package com.aaron.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.aaron.bean.ZhihuStory;
import com.aaron.domain.ZhihuRequest;
import com.aaron.looklook.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ZhihustoryActivity extends AppCompatActivity {
    @BindView(R.id.iv_zhihustory)
    ImageView mIvZhihustory;
    @BindView(R.id.toolbar_zhihustory)
    Toolbar mToolbarZhihustory;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.webview_zhihustory)
    WebView mWebviewZhihustory;


    private  Observable<ZhihuStory> storyObservable;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihustory);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        setSupportActionBar(mToolbarZhihustory);
        getSupportActionBar().setTitle(mTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarZhihustory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        storyObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ZhihuStory>() {
                    @Override
                    public void call(ZhihuStory story) {
//                       getSupportActionBar().setTitle(story.title);
//                        mWebviewZhihustory.loadUrl(story.share_url);
                        Glide.with(ZhihustoryActivity.this).load(story.image).centerCrop().into(mIvZhihustory);

                        WebSettings settings = mWebviewZhihustory.getSettings();
                        settings.setJavaScriptEnabled(true);
                        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                        String head = "<head>\n" +
                                "\t<link rel=\"stylesheet\" href=\""+story.css.get(0)+"\"/>\n" +
                                "</head>";
                        String img = "<div class=\"headline\">";
                        String html =head + story.body.replace(img," ");
                        mWebviewZhihustory.loadDataWithBaseURL(null,html,"text/html","utf-8",null);
                    }
                });
    }

    private void initData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        mTitle = intent.getStringExtra("title");
        storyObservable = ZhihuRequest.getZhihuService().getZhihuStory(id);
    }

}
