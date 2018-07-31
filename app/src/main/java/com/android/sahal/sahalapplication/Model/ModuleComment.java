package com.android.sahal.sahalapplication.Model;

public class ModuleComment {

    private String senderId;
    private String senderName;
    private String message;

    public ModuleComment(String senderId, String senderName, String message) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.message = message;
    }

    public ModuleComment() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
