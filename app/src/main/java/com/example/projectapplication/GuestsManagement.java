package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GuestsManagement extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter2_GuestManagement eventAdapter;
    private List<getEventNameOnly> eventList;
    private Button back;
    MyDatabase db;

    public TextView mytext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guests_management);

        recyclerView = findViewById(R.id.eventRecyclerView);
        db = new MyDatabase(this);
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter2_GuestManagement(this, eventList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventAdapter);
        db = new MyDatabase(this);
        back = (Button) findViewById(R.id.backbutton2);

        mytext = (TextView) findViewById(R.id.emptyText);
        loadDataFromDatabase();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuestsManagement.this, HomePage.class);
                startActivity(intent);
            }
        });
    }

    private void loadDataFromDatabase() {
        eventList.clear();

        List<getEventNameOnly> events = db.getAllEvents2();

        if (events.isEmpty()){
            recyclerView.setVisibility(View.GONE); // Show placeholder view

        }else {
            eventList.addAll(events);
            eventAdapter.notifyDataSetChanged();

            recyclerView.setVisibility(View.VISIBLE); // Show placeholder view
            mytext.setVisibility(View.GONE);

        }

    }
}