package com.wangyi.ui09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {


    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new FeedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder holder, int position) {
        holder.mIvAvatar.setImageResource(getAvaId(position));
        holder.mIvContent.setImageResource(getContentId(position));
        holder.mTvNickname.setText("NetEase"+position);
    }

    private int getAvaId(int position) {
        switch (position%4) {
            case 0:return R.mipmap.icon_mypage_like;
            case 1:return R.mipmap.icon_mypage_money;
            case 2:return R.mipmap.icon_mypage_partner;
            case 3:return R.mipmap.icon_mypage_sales;
        }
        return 0;
    }

    private int getContentId(int position) {
        switch (position%4) {
            case 0:return R.mipmap.a;
            case 1:return R.mipmap.b;
            case 2:return R.mipmap.c;
            case 3:return R.mipmap.d;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class FeedHolder extends RecyclerView.ViewHolder {
        private ImageView mIvAvatar;
        private TextView mTvNickname;
        private View mTopDivider;
        private ImageView mIvContent;


        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            mIvAvatar = itemView.findViewById(R.id.iv_avatar);
            mTvNickname = itemView.findViewById(R.id.tv_nickname);
            mTopDivider = itemView.findViewById(R.id.top_divider);
            mIvContent = itemView.findViewById(R.id.iv_content);
        }
    }
}
