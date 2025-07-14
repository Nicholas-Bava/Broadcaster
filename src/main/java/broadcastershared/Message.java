package broadcastershared;

import java.util.Random;

public class Message {
    private String senderID;
    private String recipientID;
    private type messageType;
    private int waitTime = 0;
    private String payload;

    public Message(String senderID, String recipientID, type messageType, String payload) {
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.messageType = messageType;
        this.payload = payload;
    }

    public enum type {
        TEXT,
        COMMAND
    }

    public type getMessageType() {
        return messageType;
    }

    public void setMessageType(type messageType) {
        this.messageType = messageType;
    }

    public String getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(String recipientID) {
        this.recipientID = recipientID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }




}
