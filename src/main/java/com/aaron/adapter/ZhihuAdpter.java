package com.aaron.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.bean.Stories;
import com.aaron.looklook.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Administrator on 2016/11/8.
 */
public class ZhihuAdpter extends RecyclerArrayAdapter<Stories> {
    private Context mContext;
    public ZhihuAdpter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_zhihu, null);
        return new ZhihuViewHolder(view);
    }

    class ZhihuViewHolder extends BaseViewHolder<Stories>{
        public ImageView mIvZhihuItem;
        public TextView mTvZhihuItem;


        public ZhihuViewHolder(View itemView) {
            super(itemView);
            mIvZhihuItem = (ImageView) itemView.findViewById(R.id.iv_zhihu_item);
            mTvZhihuItem = (TextView) itemView.findViewById(R.id.tv_zhihu_item);
        }

        @Override
        public void setData(Stories data) {
            super.setData(data);
            mTvZhihuItem.setText(data.title);
            Glide.with(mContext).load(data.images.get(0))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(mIvZhihuItem);
        }
    }
}
