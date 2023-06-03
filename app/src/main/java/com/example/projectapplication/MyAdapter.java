package com.example.projectapplication;

import android.content.ClipData;
import android.content.Context;
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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<item> item;
    List<item> item2;
    static MyDatabase db;
    static Guest_List guest_list;


    public MyAdapter(Context context, List<item> item) {
        this.context = context;
        this.item = item;
        db = new MyDatabase(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.view_guest_list, parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.position = position;
        item item2 = item.get(position);
        holder.textView.setText(item2.getName());
        holder.item2 = item2;
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        Button view, delete;
        int position;
        item item2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.guestName);
            view  = itemView.findViewById(R.id.viewBtn);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo", "You click " + position + item2.name);
                }
            });

            itemView.findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor cursor = db.getID(item2.name);
                    if (cursor.getCount() == 0){

                    }else {
                        while (cursor.moveToNext()) {

                            String id = cursor.getString(0);
                            boolean type = db.delete(id);

                        }
                    }
                }
            });

            itemView.findViewById(R.id.viewBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor cursor = db.getID(item2.name);
                    // Add any necessary extras or data to the intent
                    if (cursor.getCount() == 0){

                    }else{
                        while (cursor.moveToNext()) {
                            Intent intent = new Intent(view.getContext(), Guest_Details.class);
                            intent.putExtra("name",  cursor.getString(1));
                            intent.putExtra("gender",  cursor.getString(2));
                            intent.putExtra("age",  cursor.getString(3));
                            intent.putExtra("address",  cursor.getString(5));
                            intent.putExtra("phoNum",  cursor.getString(4));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this line
                            view.getContext().startActivity(intent);
                        }
                    }
                }
            });
        }
    }
}
