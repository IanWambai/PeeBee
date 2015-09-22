package com.makeshift.kuhustle.constructors;

public class MessageListItem {
    private String username;
    private String lastMessage;
    private String timestamp;
    private int icon;
    private int count;


    public MessageListItem(String username, String lastMessage, String timestamp, int icon, int count) {
        this.username = username;
        this.lastMessage = lastMessage;
        this.timestamp= timestamp;
        this.icon = icon;
        this.count = count;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}