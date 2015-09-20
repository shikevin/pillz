package com.example.mgdave.smartpillbox.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {

    @SerializedName("message")
    private List<Pill> message;

    public Message(List<Pill> message) {
        this.message = message;
    }

    public List<Pill> getMessage() {
        return message;
    }

    public void setMessage(List<Pill> message) {
        this.message = message;
    }
}
