package com.snakewizard.retailerwizard.checkin;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.snakewizard.retailerwizard.database.Commodity;

public class CommodityListAdapter extends ListAdapter<Commodity, CommodityViewHolder> {

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }



    public CommodityListAdapter(@NonNull DiffUtil.ItemCallback<Commodity> diffCallback) {
        super(diffCallback);
    }

    @Override
    public CommodityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommodityViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(CommodityViewHolder holder, int position) {
        Commodity current = getItem(position);
        holder.bind(current);
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    static class CommidityDiff extends DiffUtil.ItemCallback<Commodity> {

        @Override
        public boolean areItemsTheSame(@NonNull Commodity oldItem, @NonNull Commodity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Commodity oldItem, @NonNull Commodity newItem) {
            return oldItem.equals(newItem);
        }
    }
}
