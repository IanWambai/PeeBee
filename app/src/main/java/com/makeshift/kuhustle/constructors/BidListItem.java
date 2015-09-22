package com.makeshift.kuhustle.constructors;

public class BidListItem {
    private String username;
    private String message;
    private String value;
    private String timestamp;
    private int rating;
    private int icon;


    public BidListItem(String username, String message, String value, String timestamp, int rating, int icon) {
        this.username = username;
        this.message = message;
        this.value = value;
        this.timestamp = timestamp;
        this.rating = rating;
        this.icon = icon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}