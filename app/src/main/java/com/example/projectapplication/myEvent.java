package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class myEvent extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyEventAdapter eventAdapter;
    private List<displayMyEvent> eventList;
    private Button back;
    MyDatabase db;
    ImageView menu;
    DrawerLayout drawerLayout;


    public TextView mytext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event);

        drawerLayout = findViewById(R.id.drawerLayout);
        recyclerView = findViewById(R.id.myeventRecyclerView);
        db = new MyDatabase(this);
        eventList = new ArrayList<>();
        eventAdapter = new MyEventAdapter(this, eventList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventAdapter);
        db = new MyDatabase(this);
        back = (Button) findViewById(R.id.backButton3);
        menu = (ImageView) findViewById(R.id.menu);

        mytext = (TextView) findViewById(R.id.emptyText);
        loadDataFromDatabase();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myEvent.this, HomePage.class);
                startActivity(intent);
            }
        });
    }

    private void loadDataFromDatabase() {
        eventList.clear();
        Cursor eventname = db.searchMyName("TAY");
        if (eventname != null && eventname.moveToFirst()) {

            String eventName = eventname.getString(3);
            List<displayMyEvent> events = db.searchMyEvent(eventName);

            if (events.isEmpty()) {
                recyclerView.setVisibility(View.GONE); // Show placeholder view

            } else {
                eventList.addAll(events);
                eventAdapter.notifyDataSetChanged();

                recyclerView.setVisibility(View.VISIBLE); // Show placeholder view
                mytext.setVisibility(View.GONE);

            }
        }else {
            recyclerView.setVisibility(View.GONE); // Hide RecyclerView
            mytext.setVisibility(View.VISIBLE); // Show empty text view
        }

    }
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

}
