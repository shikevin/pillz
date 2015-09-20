package com.example.mgdave.smartpillbox.Models;

import android.database.Cursor;

import com.example.mgdave.smartpillbox.Database.PillsTable;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Pill {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("current_amount")
    private int currentAmount;

    @SerializedName("frequency")
    private int frequency;

    @SerializedName("last_taken_time")
    private Date lastTakenTime;

    @SerializedName("expected_last_date")
    private Date expectedLastDate;

    @SerializedName("color")
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Date getLastTakenTime() {
        return lastTakenTime;
    }

    public void setLastTakenTime(Date lastTakenTime) {
        this.lastTakenTime = lastTakenTime;
    }

    public Date getExpectedLastDate() {
        return expectedLastDate;
    }

    public void setExpectedLastDate(Date expectedLastDate) {
        this.expectedLastDate = expectedLastDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static Pill pillFromCursor(Cursor cursor) {
        Pill pill = new Pill();
        pill.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PillsTable.ID)));
        pill.setName(cursor.getString(cursor.getColumnIndexOrThrow(PillsTable.NAME)));
        pill.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(PillsTable.DESCRIPTION)));
        pill.setCurrentAmount(cursor.getInt(cursor.getColumnIndexOrThrow(PillsTable.CURRENT_AMOUNT)));
        return pill;
    }
}
