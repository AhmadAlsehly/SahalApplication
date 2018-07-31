package com.android.sahal.sahalapplication.Model;

public class ModuleComment {

    private String senderId;
    private String itemId;
    private  String comments;

    public ModuleComment(String senderId, String itemId, String comments) {
        this.senderId = senderId;
        this.itemId = itemId;
        this.comments = comments;
    }

    public ModuleComment() {
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
