package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Event_Home extends AppCompatActivity {

    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private FloatingActionButton addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);

        // Initialize RecyclerView
        eventRecyclerView = findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize event list and adapter
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList, this);

        // Set the adapter to the RecyclerView
        eventRecyclerView.setAdapter(eventAdapter);

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Event_Home.this, Event_Add.class);
                startActivity(intent);
            }
        });

        // Load and display events
        loadEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload events when the activity is resumed
        loadEvents();
    }

    private void loadEvents() {
        // Clear the existing event list
        eventList.clear();

        // Retrieve events from the database
        MyDatabase db = new MyDatabase(this);
        List<Event> events = db.getAllEvents();

        // Add the retrieved events to the eventList
        eventList.addAll(events);

        // Notify the adapter about the data changes
        eventAdapter.notifyDataSetChanged();
    }
}