package com.example.mgdave.smartpillbox;

import android.app.Application;
import android.app.DownloadManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SmartPillBox extends Application{

    public static final String TAG = SmartPillBox.class.getSimpleName();

    private static SmartPillBox instance;

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        instance = this;
    }

    public static SmartPillBox getInstance () {
        if (instance != null) {
            return instance;
        }
        throw new RuntimeException ("SmartPillBox instance is null");
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
