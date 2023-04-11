package com.example.fetchscreen;

public class Message {
    private String text;
    private String timestamp;

    public Message() {}

    public Message(String text, String timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
