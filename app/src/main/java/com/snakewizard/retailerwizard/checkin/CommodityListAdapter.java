package com.snakewizard.retailerwizard.checkin;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.snakewizard.retailerwizard.database.Commodity;

public class CommodityListAdapter extends ListAdapter<Commodity, CommodityViewHolder> {

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
