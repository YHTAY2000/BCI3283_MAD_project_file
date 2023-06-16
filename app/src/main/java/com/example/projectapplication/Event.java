package com.example.projectapplication;

import  java.io.Serializable;

public class Event implements Serializable {
    private String eventName, eventOrganizer, eventDate, eventTime;
    private byte[] eventImage;
    private int id;

    public Event(int id, String eventName, String eventOrganizer, String eventDate, String eventTime, byte[] eventImage) {
        this.id = id;
        this.eventName = eventName;
        this.eventOrganizer = eventOrganizer;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventImage = eventImage;
    }

    public int getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventOrganizer() {
        return eventOrganizer;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public byte[] getEventImage() {
        return eventImage;
    }
}
