package com.example.mgdave.smartpillbox.Database;

import android.content.ContentValues;
import android.text.TextUtils;

import com.example.mgdave.smartpillbox.Models.Pill;

public class ContentValueHelper {

    public static ContentValues getPillContentValues(Pill pill) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PillsTable.ID, pill.getId());
        contentValues.put(PillsTable.NAME, pill.getName());
        if (!TextUtils.isEmpty(pill.getDescription())) {
            contentValues.put(PillsTable.DESCRIPTION, pill.getDescription());
        }
        contentValues.put(PillsTable.CURRENT_AMOUNT, pill.getCurrentAmount());
        contentValues.put(PillsTable.FREQUENCY, pill.getFrequency());
        if (pill.getLastTakenTime() != null) {
            contentValues.put(PillsTable.LAST_TAKEN_TIME, pill.getLastTakenTime().getTime());
        }
        if (pill.getExpectedLastDate() != null) {
            contentValues.put(PillsTable.EXPECTED_LAST_DATE, pill.getExpectedLastDate().getTime());
        }
        return contentValues;
    }
}
