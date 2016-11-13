package com.aaron.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.bean.PengfuBean;
import com.aaron.looklook.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/10.
 */
public class PengfuAdapter extends RecyclerArrayAdapter<PengfuBean> {
    private Context mContext;

    public PengfuAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pengfu, null);
        return new PengfuVH(view);
    }

    class PengfuVH extends BaseViewHolder<PengfuBean> {
        @BindView(R.id.iv_user_head)
        ImageView mIvUserHead;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.iv_content_pic)
        ImageView mIvContentPic;
        @BindView(R.id.tv_content_text)
        TextView mTvContentText;
        @BindView(R.id.tv_tag1)
        TextView mTvTag1;
        @BindView(R.id.tv_tag2)
        TextView mTvTag2;
        @BindView(R.id.tv_tag3)
        TextView mTvTag3;
        @BindView(R.id.tv_tag4)
        TextView mTvTag4;
        public PengfuVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(PengfuBean data) {
            super.setData(data);
            Glide.with(mContext).load(data.userAvatar).into(mIvUserHead);
            mTvTitle.setText(data.title);
            mTvTime.setText(data.lastTime);
            setTag(data.tags);
            if (TextUtils.isEmpty(data.bean.content)){
                mIvContentPic.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(data.bean.gifsrcImg)){
                    mTvContentText.setVisibility(View.GONE);
                    Glide.with(mContext).load(data.bean.showImg).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvContentPic);
                }else {
                    mTvContentText.setVisibility(View.VISIBLE);
                    mTvContentText.setText("GIF,点开后查看");
                    Glide.with(mContext).load(data.bean.showImg).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvContentPic);
                }
            }else {
                mTvContentText.setVisibility(View.VISIBLE);
                mIvContentPic.setVisibility(View.GONE);
                mTvContentText.setText(data.bean.content);
            }
        }

        private void setTag(List<String> tags){
            switch (tags.size()){
                case 0:
                    mTvTag1.setVisibility(View.GONE);
                    mTvTag2.setVisibility(View.GONE);
                    mTvTag3.setVisibility(View.GONE);
                    mTvTag4.setVisibility(View.GONE);
                    break;
                case 1:
                    mTvTag1.setText(tags.get(0));
                    mTvTag1.setVisibility(View.VISIBLE);
                    mTvTag2.setVisibility(View.GONE);
                    mTvTag3.setVisibility(View.GONE);
                    mTvTag4.setVisibility(View.GONE);
                    break;
                case 2:
                    mTvTag1.setText(tags.get(0));
                    mTvTag2.setText(tags.get(1));
                    mTvTag1.setVisibility(View.VISIBLE);
                    mTvTag2.setVisibility(View.VISIBLE);
                    mTvTag3.setVisibility(View.GONE);
                    mTvTag4.setVisibility(View.GONE);
                    break;
                case 3:
                    mTvTag1.setText(tags.get(0));
                    mTvTag2.setText(tags.get(1));
                    mTvTag3.setText(tags.get(2));
                    mTvTag1.setVisibility(View.VISIBLE);
                    mTvTag2.setVisibility(View.VISIBLE);
                    mTvTag3.setVisibility(View.VISIBLE);
                    mTvTag4.setVisibility(View.GONE);
                    break;
                case 4:
                    mTvTag1.setText(tags.get(0));
                    mTvTag2.setText(tags.get(1));
                    mTvTag3.setText(tags.get(2));
                    mTvTag4.setText(tags.get(3));
                    mTvTag1.setVisibility(View.VISIBLE);
                    mTvTag2.setVisibility(View.VISIBLE);
                    mTvTag3.setVisibility(View.VISIBLE);
                    mTvTag4.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
