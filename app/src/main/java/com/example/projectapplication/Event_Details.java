package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Event_Details extends AppCompatActivity {

    private Button backBtn, guestListBtn;
    private TextView eventName, organizerName, date, time, location, schedule, activity;
    private ImageView eventImage;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        backBtn = findViewById(R.id.backBTN2);
        guestListBtn = findViewById(R.id.guestListButton);
        eventImage = findViewById(R.id.eventimage);
        eventName = findViewById(R.id.eventname);
        organizerName = findViewById(R.id.organizername);
        date = findViewById(R.id.eventdate);
        time = findViewById(R.id.eventtime);
        location = findViewById(R.id.locationText2);
        schedule = findViewById(R.id.scheduleText);
        activity = findViewById(R.id.activityText2);

        Intent intent = getIntent();
        if (intent != null) {
            Event event = (Event) intent.getSerializableExtra("event");
            if (event != null) {
                // Set the event details in the TextViews and ImageView
                eventImage.setImageBitmap(BitmapUtils.getBitmapFromByteArray(event.getEventImage()));
                eventName.setText(event.getEventName());
                organizerName.setText(event.getEventOrganizer());
                date.setText(event.getEventDate());
                time.setText(event.getEventTime());
                location.setText(event.getEventLocation());
                activity.setText(event.getEventActivity());
                this.event = event;
                String sentence = "At " +  event.getEventTime() + ", you may enter the event. After 15 minutes the event will start.";
                schedule.setText(sentence);
            }
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Event_Details.this, Event_Home.class);
                startActivity(intent);
            }
        });

        guestListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Event_Details.this, Guest_List.class);
                startActivity(intent);
            }
        });
    }

}