package com.toe.peebee.constructors;

public class ConversationItem {

    private String from;
    private String to;
    private String timestamp;

    public ConversationItem() {
        //Empty for Firebase
    }

    public ConversationItem(String from, String to, String timestamp) {
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}