package com.example.projectapplication;

public class Event {
    private String eventName;

    public Event(int event_id, String eventName, String eventOrganizer, String eventDate, String eventTime, byte[] eventImage) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
