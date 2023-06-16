package com.example.projectapplication;

public class getEventNameOnly {
    private String eventName;
    int id;

    public getEventNameOnly(int event_id, String eventName) {
        this.eventName = eventName;
        this.id = event_id;
    }

    public String getEventName() {
        return eventName;
    }
    public int  getID() {
        return id;
    }
}
