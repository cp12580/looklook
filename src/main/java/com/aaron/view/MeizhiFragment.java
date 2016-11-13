package com.aaron.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.aaron.adapter.MeizhiAdapter;
import com.aaron.bean.Meizhi;
import com.aaron.domain.MeizhiApi;
import com.aaron.domain.MeizhiRequest;
import com.aaron.looklook.R;
import com.aaron.utils.Util;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/11.
 */
public class MeizhiFragment extends BaseFragment {
    @BindView(R.id.rv_meizhi)
    EasyRecyclerView mRvMeizhi;

    private MeizhiAdapter mAdapter;
    private MeizhiApi meizhiServic;
    private int mPage = 1;


    @Override
    protected void initData() {
        super.initData();
        meizhiServic = MeizhiRequest.getMeizhiService();
        mAdapter = new MeizhiAdapter(mActivity);
    }

    @Override
    protected View initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_meizhi, null);
        ButterKnife.bind(this, view);
        mRvMeizhi.setAdapter(mAdapter);
        mRvMeizhi.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        SpaceDecoration itemDecoration = new SpaceDecoration(10);
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRvMeizhi.addItemDecoration(itemDecoration);
        getMeizhiData(1);
        mRvMeizhi.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                mPage = 1;
                getMeizhiData(mPage);
            }
        });
        mAdapter.setMore(R.layout.foot_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                getMeizhiData(mPage);
            }

            @Override
            public void onMoreClick() {
                Util.L("more");
            }
        });
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Meizhi.Results results = mAdapter.getAllData().get(position);
                String url = results.url;
                String date = results.desc;
                Intent intent = new Intent(mActivity,MeizhiActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
        mAdapter.setNoMore(R.layout.foot_nomore);
        return view;
    }

    @OnClick(R.id.fab_pengfu)
    public void onClick() {
        mRvMeizhi.scrollToPosition(0);
    }

    private void getMeizhiData(int page){
        meizhiServic.getMeizhiList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Meizhi>() {
                    @Override
                    public void call(Meizhi meizhi) {
                        if (meizhi.error){
                            Snackbar.make(getView(),"网络连接失败",Snackbar.LENGTH_SHORT).show();
                            return;
                        }else {
                            mAdapter.addAll(meizhi.results);
                            mPage++;
                        }
                    }
                });
    }
}
