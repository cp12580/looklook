package com.aaron.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.aaron.looklook.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Administrator on 2016/11/10.
 */
public class PengfuActivity extends AppCompatActivity {
    private ImageView mIv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengfu);
        mIv = (ImageView) findViewById(R.id.iv_pengfu);
        Intent intent = getIntent();
        String url = intent.getStringExtra("imgUrl");

        if (!TextUtils.isEmpty(url)){
            if (url.endsWith("gif")){
                Glide.with(this).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIv);
            }else {
                Glide.with(this).load(url).into(mIv);
            }

        }
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PengfuActivity.this.finish();
            }
        });
    }
}
