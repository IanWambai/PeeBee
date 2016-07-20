package com.toe.peebee.constructors;

public class ChatListItem {

    private String message;
    private String from;
    private String to;
    private String timestamp;

    public ChatListItem(String message, String from, String to, String timestamp) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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