package com.example.mgdave.smartpillbox.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class DataProvider extends ContentProvider {

    // database
    private DatabaseHelper database;

    // table names
    private static final String PILLS_TABLE = PillsTable.TABLE_NAME;

    // Used for the URI Matcher
    private static final int PILLS_COLLECTION = 1;

    private static final String AUTHORITY = "com.smartpillbox.data.provider";

    public static final Uri PILLS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PILLS_TABLE);

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, PILLS_TABLE, PILLS_COLLECTION);
    }

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(this.getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase readableDatabase = database.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case PILLS_COLLECTION: {
                qb.setTables(PILLS_TABLE);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }

        Cursor cursor = qb.query(readableDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id;
        SQLiteDatabase writableDatabase = database.getWritableDatabase();
        if (writableDatabase == null) {
            return null;
        }
        switch (uriMatcher.match(uri)) {
            case PILLS_COLLECTION: {
                id = writableDatabase.insertWithOnConflict(PILLS_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
        if (id > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] valuesCollection) {
        if (valuesCollection == null || valuesCollection.length == 0) {
            return 0;
        }
        SQLiteDatabase writableDatabase = database.getWritableDatabase();
        int insertsCount = 0;
        switch (uriMatcher.match(uri)) {
            case PILLS_COLLECTION: {
                writableDatabase.beginTransaction();
                for (ContentValues values : valuesCollection) {
                    if (writableDatabase.insertWithOnConflict(PILLS_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE) > 0) {
                        insertsCount++;
                    }
                }
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                    break;
                }
                default: {
                    throw new IllegalArgumentException("URI Unknown: " + uri);
                }
            }
        if (insertsCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return insertsCount;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteCount;
        SQLiteDatabase writableDatabase = database.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case PILLS_COLLECTION: {
                deleteCount = writableDatabase.delete(PILLS_TABLE, selection, selectionArgs);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
        if (deleteCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase writableDatabase = database.getWritableDatabase();
        int updateCount;
        switch (uriMatcher.match(uri)) {
            case PILLS_COLLECTION: {
                updateCount = writableDatabase.update(PILLS_TABLE, values, selection, selectionArgs);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }

    public void clearTable() {
        SQLiteDatabase writableDatabase = database.getWritableDatabase();
        writableDatabase.delete(PILLS_TABLE, null, null);
    }
}
