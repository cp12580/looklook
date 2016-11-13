package com.aaron.view;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaron.adapter.BannerAdapter;
import com.aaron.adapter.ZhihuAdpter;
import com.aaron.bean.ZhihuNews;
import com.aaron.domain.ZhihuApi;
import com.aaron.domain.ZhihuRequest;
import com.aaron.looklook.R;
import com.aaron.utils.Util;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.rollviewpager.RollPagerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/8.
 */
public class ZhihuFragment extends BaseFragment{
    @BindView(R.id.rv_zhihu)
    EasyRecyclerView mRvZhihu;
    @BindView(R.id.fab_zhihu)
    FloatingActionButton mFab;

    private ZhihuApi zhihuService;
    private ZhihuAdpter mAdpter;
    private Long mCurrentTime;
    private Handler mHandler = new Handler();
    private boolean mFirst = true;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_zhihu,null);
        ButterKnife.bind(this,view);
        mRvZhihu.setAdapter(mAdpter);
        mRvZhihu.setLayoutManager(new LinearLayoutManager(mActivity));
        getLatestNews();
        mFirst = true;
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRvZhihu.scrollToPosition(0);
            }
        });
        mRvZhihu.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentTime = System.currentTimeMillis();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdpter.clear();
                        getLatestNews();
                    }
                },1000);
            }
        });
        mAdpter.setMore(R.layout.foot_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getBeforeNews();
                    }
                },1000);
            }
        });
        mAdpter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String id = mAdpter.getAllData().get(position).id +"";
                Intent intent = new Intent(mActivity, ZhihustoryActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("title",mAdpter.getAllData().get(position).title);
                startActivity(intent);
            }
        });
        mRvZhihu.scrollToPosition(0);
        return view;
    }

    @Override
    protected void initData() {
        mAdpter = new ZhihuAdpter(mActivity);
        zhihuService = ZhihuRequest.getZhihuService();
        mCurrentTime = System.currentTimeMillis();
    }
    private void getLatestNews(){
        zhihuService.getLastestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhihuNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(getView(),"网络连接错误" ,Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(final ZhihuNews news) {
                        mAdpter.addAll(news.stories);
                        if (mFirst){
                            mAdpter.addHeader(new RecyclerArrayAdapter.ItemView() {
                                @Override
                                public View onCreateView(ViewGroup parent) {
                                    RollPagerView header = new RollPagerView(mActivity);
                                    header.setHintPadding(0,0,0, (int) Util.dpToPx(40,mActivity));
                                    header.setPlayDelay(5000);
                                    header.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) Util.dpToPx(200,mActivity)));
                                    header.setAdapter(new BannerAdapter(mActivity,news.top_stories));
                                    return header;
                                }

                                @Override
                                public void onBindView(View headerView) {

                                }
                            });
                            mRvZhihu.scrollToPosition(0);
                            mFirst = false;
                        }

                    }
                });
    }
    private void getBeforeNews(){
        mCurrentTime = mCurrentTime - 1000 *60 * 60 *24;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(mCurrentTime);
        String s = format.format(date);
        zhihuService.getTheDaily(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhihuNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(getView(),"网络连接错误",Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ZhihuNews news) {
                        mAdpter.addAll(news.stories);
                    }
                });
    }
}
