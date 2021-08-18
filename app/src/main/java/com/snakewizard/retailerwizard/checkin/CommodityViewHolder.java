package com.snakewizard.retailerwizard.checkin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.snakewizard.retailerwizard.R;
import com.snakewizard.retailerwizard.database.Commodity;


class CommodityViewHolder extends RecyclerView.ViewHolder {
    private final TextView serialNumberItemView;
    private final TextView nameItemView;
    private final TextView unitItemView;
    private final TextView amountItemView;
    private final TextView priceItemView;

    private CommodityViewHolder(View itemView) {
        super(itemView);

        serialNumberItemView = itemView.findViewById(R.id.serial_number);
        nameItemView = itemView.findViewById(R.id.name);
        unitItemView = itemView.findViewById(R.id.unit);
        amountItemView = itemView.findViewById(R.id.amount);
        priceItemView = itemView.findViewById(R.id.price);

    }

    public void bind(Commodity commodity) {
        serialNumberItemView.setText(commodity.getSerialNumber());
        nameItemView.setText(commodity.getName());
        unitItemView.setText(commodity.getUnit());
        amountItemView.setText(commodity.getAmount().toString());
        priceItemView.setText(commodity.getPrice().toString());
    }

    static CommodityViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview_item, parent, false);
        return new CommodityViewHolder(view);
    }
}