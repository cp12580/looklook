package com.aaron.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.bean.TopStories;
import com.aaron.looklook.MainActivity;
import com.aaron.looklook.R;
import com.aaron.view.ZhihustoryActivity;
import com.bumptech.glide.Glide;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/11.
 */
public class BannerAdapter extends StaticPagerAdapter {
    private Context mContext;
    private ArrayList<TopStories> mTopStories;

    public BannerAdapter(Context context, ArrayList<TopStories> top) {
        mContext = context;
        mTopStories = top;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_top,null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_top);
        TextView tv = (TextView) view.findViewById(R.id.tv_top);
        Glide.with(mContext).load(mTopStories.get(position).image).into(iv);
        tv.setText(mTopStories.get(position).title);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((MainActivity)mContext, ZhihustoryActivity.class);
                intent.putExtra("id",mTopStories.get(position).id + "");
                intent.putExtra("title",mTopStories.get(position).title);
                ((MainActivity)mContext).startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return mTopStories.size();
    }
}
