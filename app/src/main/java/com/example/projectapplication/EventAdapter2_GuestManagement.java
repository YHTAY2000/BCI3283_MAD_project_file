package com.example.projectapplication;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

public class EventAdapter2_GuestManagement extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM_1 = 1;
    private static final int VIEW_TYPE_ITEM_2 = 2;

    private Context context;
    private List<getEventNameOnly> itemList1;
    private MyDatabase db;


    public EventAdapter2_GuestManagement(Context context, List<getEventNameOnly> itemList1) {
        this.context = context;
        this.itemList1 = itemList1;
        db = new MyDatabase(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.guest_event_item, parent, false);
        return new Item1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == VIEW_TYPE_ITEM_1) {
            Item1ViewHolder holder = (Item1ViewHolder) viewHolder;
            getEventNameOnly item = itemList1.get(position);
            holder.textView.setText(item.getEventName());
            holder.time.setText(" TIME : " + item.getTime());
            holder.date.setText(" DATE : " + item.getDate());
            holder.image.setImageBitmap(BitmapUtils.getBitmapFromByteArray(item.getImage()));
            holder.item = item;
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

        TextView textView, time, date;
        Button view, delete;
        getEventNameOnly item;
         ImageView image;

        public Item1ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.eventNameTextView);
            time = itemView.findViewById(R.id.Texttime);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.imageEvent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Guest_List.class);
                    intent.putExtra("event_name", item.getEventName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    // Method to add a CheckInItem to the checkInRecycleView
    public void deleteItem() {
        notifyItemInserted(itemList1.size());
    }
}