package com.aaron.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aaron.bean.Meizhi;
import com.aaron.looklook.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Administrator on 2016/11/11.
 */
public class MeizhiAdapter extends RecyclerArrayAdapter<Meizhi.Results> {
    private Context mContext;
    public MeizhiAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_meizhi,null);
        return new MeizhiVH(view);
    }
    class MeizhiVH extends BaseViewHolder<Meizhi.Results>{
        private ImageView mIv;
        public MeizhiVH(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.iv_meizhi_item);
        }

        @Override
        public void setData(Meizhi.Results data) {
            super.setData(data);
            Glide.with(mContext).load(data.url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIv);
        }
    }
}
