package com.example.mgdave.smartpillbox.Database;

import android.content.ContentResolver;
import android.content.ContentValues;

import com.example.mgdave.smartpillbox.Models.Pill;
import com.example.mgdave.smartpillbox.SmartPillBox;

import java.util.ArrayList;
import java.util.List;

public class DBProcessor {

    private static final String TAG = "DBProcessor";
    private static volatile DBProcessor instance = null;
    private ContentResolver contentResolver;

    public static DBProcessor getInstance() {
        if (instance == null) {
            synchronized (DBProcessor.class) {
                if (instance == null) {
                    instance = new DBProcessor();
                }
            }
        }
        return instance;
    }

    private DBProcessor () {
        contentResolver = SmartPillBox.getInstance().getContentResolver();
    }

    public int insertPills (List<Pill> pills) {
        ArrayList<ContentValues> values = new ArrayList<>();
        for (Pill pill : pills) {
            ContentValues cvs = ContentValueHelper.getPillContentValues(pill);
            values.add(cvs);
        }
        return contentResolver.bulkInsert(DataProvider.PILLS_CONTENT_URI, values.toArray(new ContentValues[values.size()]));
    }
}
