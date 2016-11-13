package com.aaron.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.aaron.adapter.PengfuAdapter;
import com.aaron.bean.PengfuBean;
import com.aaron.domain.PengfuApi;
import com.aaron.domain.PengfuRequest;
import com.aaron.looklook.R;
import com.aaron.utils.PengfuJsoup;
import com.aaron.utils.Util;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/10.
 */
public class PengfuFragment extends BaseFragment {
    @BindView(R.id.rv_pengfu)
    EasyRecyclerView mRvPengfu;


    private PengfuAdapter mAdapter;
    private int mPage = 1;
    private PengfuApi pengfuService;
    private PengfuJsoup mJsoup;
    private boolean hasMore = true;
    private Handler mHandler = new Handler();


    @Override
    protected void initData() {
        pengfuService = PengfuRequest.getPengfuService();
        mAdapter = new PengfuAdapter(mActivity);
        mJsoup = PengfuJsoup.getInstance();
    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_pengfu, null);
        ButterKnife.bind(this, view);
        mRvPengfu.setAdapter(mAdapter);
        mRvPengfu.setLayoutManager(new LinearLayoutManager(mActivity));
        //添加边框
        SpaceDecoration itemDecoration = new SpaceDecoration(10);
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRvPengfu.addItemDecoration(itemDecoration);
        getPengfuData(1);
        mRvPengfu.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.clear();
                        mPage = 1;
                        getPengfuData(mPage);
                    }
                },1000);
            }
        });
        mAdapter.setMore(R.layout.foot_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (hasMore){
                            getPengfuData(mPage);
                        }
                    }
                },1000);
            }
        });
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                PengfuBean pengfuBean = mAdapter.getAllData().get(position);
                String content = pengfuBean.bean.content;
                String imgUrl = null;
                if (TextUtils.isEmpty(content)){
                    String gifsrcImg = pengfuBean.bean.gifsrcImg;
                    if (TextUtils.isEmpty(gifsrcImg)){
                        imgUrl = pengfuBean.bean.showImg;
                    }else {
                        imgUrl = pengfuBean.bean.gifsrcImg;
                    }
                }else {
                    return;
                }
                Intent intent = new Intent(mActivity,PengfuActivity.class);
                intent.putExtra("imgUrl",imgUrl);
                startActivity(intent);
            }
        });
        return view;
    }

    @OnClick(R.id.fab_pengfu)
    public void onClick() {
        mRvPengfu.scrollToPosition(0);
    }

    private void getPengfuData(int page) {
        pengfuService.getPengfuList(page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.T(mActivity,e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                        String s = null;
                        try {
                            s = response.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Document document = Jsoup.parse(s);
                        if (document != null) {
                            List<PengfuBean> pengfuList = mJsoup.getPengfuList(document);
                            mAdapter.addAll(pengfuList);
                            mPage ++;
                        }else {
                            hasMore = false;
                            mAdapter.setNoMore(R.layout.foot_nomore);
                        }
                    }
                });
    }

}
