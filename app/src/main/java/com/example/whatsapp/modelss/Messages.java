package com.example.whatsapp.modelss;

public class Messages {
    String iId, message, messageID;
    Long timeStamp;

    public Messages(String iId, String message) {
        this.iId = iId;
        this.message = message;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public Messages(String iId, String message, Long timeStamp) {
        this.iId = iId;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public Messages(){}

    public String getiId() {
        return iId;
    }

    public void setiId(String iId) {
        this.iId = iId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
