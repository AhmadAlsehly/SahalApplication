package com.android.sahal.sahalapplication.Model;

public class Comment {

    private String senderId;
    private String itemId;

    public Comment(String senderId, String itemId) {
        this.senderId = senderId;
        this.itemId = itemId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
