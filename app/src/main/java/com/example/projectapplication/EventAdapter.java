package com.example.projectapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> eventList;
    private Context context;

    public EventAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Event event = eventList.get(position);
        holder.eventNameTextView.setText(event.getEventName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Event_Details.class);
                intent.putExtra("event", event); // Pass the entire Event object
                context.startActivity(intent);
            }
        });



        // Set click listener for the edit icon
        holder.editImageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Event_Edit.class);
            intent.putExtra("event", event); // Pass the entire Event object
            context.startActivity(intent);
        });

        // Set click listener for the delete icon
        holder.deleteImageView.setOnClickListener(v -> {
            // Perform delete operation here
            deleteEvent(event, position);
        });
    }

    private void deleteEvent(Event event, int position) {
        int eventId = event.getId(); // Get the event ID
        // Perform the delete operation using the eventId
        MyDatabase db = new MyDatabase(context);
        int rowsAffected = db.deleteEvent(eventId);
        if (rowsAffected > 0) {
            // Event deleted successfully
            Toast.makeText(context, "Event deleted", Toast.LENGTH_SHORT).show();
            // Remove the event from the eventList and update the RecyclerView
            eventList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, eventList.size());
        } else {
            // Failed to delete event
            Toast.makeText(context, "Failed to delete event", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eventNameTextView;
        public ImageView editImageView;
        public ImageView deleteImageView;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView);
            editImageView = itemView.findViewById(R.id.editImageView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
