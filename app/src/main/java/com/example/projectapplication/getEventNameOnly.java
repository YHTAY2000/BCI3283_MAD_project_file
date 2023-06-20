package com.example.projectapplication;

import android.graphics.Bitmap;

import java.sql.Blob;

public class getEventNameOnly {
    private String eventName, time, date;
    int id;
    Bitmap image;

    public getEventNameOnly(int event_id, String eventName, String time, String date, Bitmap image) {
        this.eventName = eventName;
        this.time = time;
        this.date = date;
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
    public Bitmap getImage() {
        return image;
    }
    public int  getID() {
        return id;
    }
}
