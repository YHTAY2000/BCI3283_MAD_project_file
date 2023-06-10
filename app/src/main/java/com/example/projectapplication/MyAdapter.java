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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM_1 = 1;
    private static final int VIEW_TYPE_ITEM_2 = 2;

    private Context context;
    private List<item> itemList1;
    private MyDatabase db;

    public MyAdapter(Context context, List<item> itemList1) {
        this.context = context;
        this.itemList1 = itemList1;
        db = new MyDatabase(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.view_guest_list, parent, false);
            return new Item1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == VIEW_TYPE_ITEM_1) {
            Item1ViewHolder holder = (Item1ViewHolder) viewHolder;
            item item = itemList1.get(position);
            holder.textView.setText(item.getName());
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

        TextView textView;
        Button view, delete;
        item item;

        public Item1ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.guestName);
            view = itemView.findViewById(R.id.viewBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo", "You clicked item 1 at position: " + getAdapterPosition());
                }
            });

            itemView.findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor cursor = db.getID(item.getName());
                    if (cursor.getCount() == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Status");
                        builder.setMessage("Not Data Found");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                    } else {
                        while (cursor.moveToNext()) {
                            int position = getAdapterPosition();
                            String id = cursor.getString(0);
                            boolean status = db.delete(id);
                            if (status) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Status");
                                builder.setMessage("Successful Deleted");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                                itemList1.remove(position);
                                notifyItemRemoved(position);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Status");
                                builder.setMessage("Something Wrong");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();

                            }
                        }
                    }
                }
            });

            itemView.findViewById(R.id.viewBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor cursor = db.getID(item.getName());
                    if (cursor.getCount() == 0) {

                    } else {
                        while (cursor.moveToNext()) {
                            Intent intent = new Intent(view.getContext(), Guest_Details.class);
                            String id2 = cursor.getString(0);
                            intent.putExtra("id",  id2);
                            intent.putExtra("name", cursor.getString(1));
                            intent.putExtra("gender", cursor.getString(2));
                            intent.putExtra("age", cursor.getString(3));
                            intent.putExtra("address", cursor.getString(5));
                            intent.putExtra("phoNum", cursor.getString(4));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            view.getContext().startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    // Method to add a CheckInItem to the checkInRecycleView
    public void deleteItem() {
        notifyItemInserted(itemList1.size());
    }
}