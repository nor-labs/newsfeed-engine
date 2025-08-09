package com.learn.newsfeed.model;

public class Event {
    private String eventId;
    private String eventData;
    private String eventTimestamp;

    public Event(String eventId, String eventData, String eventTimestamp) {
        this.eventId = eventId;
        this.eventData = eventData;
        this.eventTimestamp = eventTimestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public String getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(String eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }
}
