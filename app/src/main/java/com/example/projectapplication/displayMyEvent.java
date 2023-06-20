package com.example.projectapplication;

import android.graphics.Bitmap;

public class displayMyEvent {
    private String eventName, time, date, organizer, location, description;
    int id;
    Bitmap image;

    public displayMyEvent(int event_id, String eventName, String time, String date, Bitmap image, String organizer, String location, String description) {
        this.eventName = eventName;
        this.time = time;
        this.date = date;
        this.organizer = organizer;
        this.location = location;
        this.description = description;
        this.id = event_id;
        this.image = image;
    }

    public String getEventName() {
        return eventName;
    }
    public String getTime() {
        return time;
    }
    public String getDate() {
        return date;
    }
    public String getzorganizer() {
        return organizer;
    }
    public String getLocation2() {
        return location;
    }
    public String getDescription() {
        return description;
    }
    public Bitmap getImage() {
        return image;
    }
    public int  getID() {
        return id;
    }
}
