package com.example.android.justjava;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Format;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderIdItemView;
        private final TextView orderNameItemView;
        private final TextView orderTotalItemView;
        private OrderViewHolder(View itemView) {
            super(itemView);
            orderIdItemView = itemView.findViewById(R.id.id_text_view);
            orderNameItemView = itemView.findViewById(R.id.name_text_view);
            orderTotalItemView = itemView.findViewById(R.id.total_text_view);
        }
    }

    private final LayoutInflater mInflater;
    private List<CoffeeOrder> mCoffeeOrder; // Cached copy of Orders

    OrderListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        if (mCoffeeOrder != null) {
            CoffeeOrder current = mCoffeeOrder.get(position);
            holder.orderIdItemView.setText(String.valueOf(current.getOrderID()));
            holder.orderNameItemView.setText(current.getCostumerName());
            holder.orderTotalItemView.setText(String.valueOf(current.getTotalPrice()));
        } else {
            // Covers the case of data not being ready yet.
            holder.orderIdItemView.setVisibility(View.GONE);
            holder.orderNameItemView.setVisibility(View.GONE);
            holder.orderTotalItemView.setVisibility(View.GONE);
        }
    }

    void setmCoffeeOrder(List<CoffeeOrder> coffeeOrder){
        mCoffeeOrder = coffeeOrder;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCoffeeOrder != null)
            return mCoffeeOrder.size();
        else return 0;
    }
}