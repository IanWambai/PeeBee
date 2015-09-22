package com.makeshift.kuhustle.constructors;

public class NotificationListItem {
    private String title;
    private String message;
    private String timestamp;


    public NotificationListItem(String title, String message, String timestamp) {
        this.title = title;
        this.message = message;
        this.timestamp= timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}