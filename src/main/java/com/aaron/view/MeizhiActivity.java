package com.aaron.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.aaron.looklook.R;
import com.aaron.utils.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/11.
 */
public class MeizhiActivity extends AppCompatActivity {
    @BindView(R.id.iv_meizhi)
    ImageView mIvMeizhi;
    @BindView(R.id.toolbar_meizhi)
    Toolbar mToolbarMeizhi;

    private String mDate;
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbarMeizhi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarMeizhi.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mDate = intent.getStringExtra("date");
        Glide.with(this).load(mUrl).asBitmap().into(mIvMeizhi);
        mIvMeizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_saving:
                String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/yilan";
                File dir = new File(s);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                final File file = new File(dir, mDate + ".jpg");
                if (file.exists()) {
                    Util.T(MeizhiActivity.this, "文件已存在");
                } else {
                    Observable.create(new Observable.OnSubscribe<File>() {
                        @Override
                        public void call(Subscriber<? super File> subscriber) {
                            try {
                                File file1 = Glide.with(MeizhiActivity.this).load(mUrl).downloadOnly(Target.SIZE_ORIGINAL
                                        , Target.SIZE_ORIGINAL).get();
                                subscriber.onNext(file1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<File>() {
                                @Override
                                public void call(File fe) {
                                    try {
                                        FileInputStream fis = new FileInputStream(fe);
                                        FileOutputStream os = new FileOutputStream(file);
                                        byte[] bytes = new byte[1024];
                                        int len = 0;
                                        while ((len = fis.read(bytes)) != -1){
                                            os.write(bytes,0,len);
                                        }
                                        os.flush();
                                        os.close();
                                        fis.close();
                                        Util.T(MeizhiActivity.this, "保存成功");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Util.T(MeizhiActivity.this, "保存失败" + e.getMessage());
                                    }
                                }
                            });
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
