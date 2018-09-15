package com.example.android.justjava;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderItemView;
        private OrderViewHolder(View itemView) {
            super(itemView);
            orderItemView = itemView.findViewById(R.id.textView);
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
            holder.orderItemView.setText(current.getOrderSummary());
        } else {
            // Covers the case of data not being ready yet.
            holder.orderItemView.setText("No Orders");
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