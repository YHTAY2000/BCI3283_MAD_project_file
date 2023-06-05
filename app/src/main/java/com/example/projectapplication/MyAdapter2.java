package com.example.projectapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM_1 = 1;
    private static final int VIEW_TYPE_ITEM_2 = 2;

    private Context context;
    private List<CheckInItem> itemList1;
    private MyDatabase db;
    private RecyclerView checkInRecycleView; // Reference to the RecyclerView with ID checkInRecycleView

    public MyAdapter2(Context context, List<CheckInItem> itemList1) {
        this.context = context;
        this.itemList1 = itemList1;
        db = new MyDatabase(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.view_check_in_list, parent, false);
            return new Item1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == VIEW_TYPE_ITEM_1) {
            Item1ViewHolder holder = (Item1ViewHolder) viewHolder;
            CheckInItem item = itemList1.get(position);
            holder.textView.setText(item.getCheckInName());
            holder.item = item;

            // Add the item to the checkInRecycleView
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < itemList1.size()) {
            return VIEW_TYPE_ITEM_1;
        } else {
            return VIEW_TYPE_ITEM_2;
        }
    }

    @Override
    public int getItemCount() {
        return itemList1.size();
    }

    public class Item1ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        Button view, delete;
        CheckInItem item;

        public Item1ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.checkInRecycleView);
        }
    }

    // Method to add a CheckInItem to the checkInRecycleView
    public void addItem(CheckInItem item) {
        itemList1.add(item);
        notifyItemInserted(itemList1.size());
    }
}
