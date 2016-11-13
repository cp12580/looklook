package com.aaron.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.aaron.bean.ZhihuStart;
import com.aaron.domain.ZhihuRequest;
import com.aaron.looklook.MainActivity;
import com.aaron.looklook.R;
import com.aaron.utils.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/11.
 */
public class SplashActivity extends AppCompatActivity
{
    private ImageView mIvSplash;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mIvSplash = (ImageView) findViewById(R.id.iv_splah);
        inieView();
    }

    private void inieView() {
        if (Util.isNetConn(this)){
            ZhihuRequest.getZhihuService().getZhihuStart()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<ZhihuStart>() {
                        @Override
                        public void call(final ZhihuStart start) {
                            Glide.with(SplashActivity.this).load(start.img).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(mIvSplash);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                                    finish();
                                }
                            },3000);
                            //缩放动画
//                            final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
//                                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                                    0.5f);
//                            scaleAnim.setFillAfter(true);
//                            scaleAnim.setDuration(3000);
//                            mIvSplash.startAnimation(scaleAnim);
//                            scaleAnim.setAnimationListener(new Animation.AnimationListener() {
//                                @Override
//                                public void onAnimationStart(Animation animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationEnd(Animation animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationRepeat(Animation animation) {
//
//                                }
//                            });
                        }
                    });

        }else {
            mIvSplash.setImageResource(R.drawable.background_cards);
            new AlertDialog.Builder(this)
                    .setMessage("无网络连接")
                    .setCancelable(false)
                    .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            SplashActivity.this.finish();
                        }
                    }).show();
        }
    }
}
