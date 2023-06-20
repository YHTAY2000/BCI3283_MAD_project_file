package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class Event_Home extends AppCompatActivity {

    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private FloatingActionButton addBtn;
    private Button backbutton;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        backbutton = findViewById(R.id.backBTN);
        backbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Event_Home.this, HomePage.class);
                startActivity(intent);
            }
        });

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

        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyroscopeListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                // Handle gyroscope sensor data here
                float x = event.values[0];  // Rotation around x-axis
                float y = event.values[1];  // Rotation around y-axis
                float z = event.values[2];  // Rotation around z-axis

                // Add your custom logic based on gyroscope data
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Handle gyroscope accuracy changes if needed
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload events when the activity is resumed
        loadEvents();
        sensorManager.registerListener(gyroscopeListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeListener);
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