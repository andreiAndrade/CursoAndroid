package com.totvs.classificados.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.totvs.classificados.R;
import com.totvs.classificados.activity.DetailActivity;
import com.totvs.classificados.model.AdItem;

import java.util.List;

/**
 * Created by Totvs on 19/12/2016.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    private List<AdItem> mItems;
    private Context mContext;

    public ItemAdapter(List<AdItem> items, Context context) {
        mItems = items;
        mContext = context;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvDetail;
        public ImageView ivImage;

        public ItemHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        AdItem item = mItems.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDetail.setText(item.getDetail());
        holder.ivImage.setImageResource(R.mipmap.ic_launcher);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("AD_KEY", mItems.get(holder.getAdapterPosition()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
