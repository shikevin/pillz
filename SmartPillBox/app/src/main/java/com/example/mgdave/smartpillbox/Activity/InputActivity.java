package com.example.mgdave.smartpillbox.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.mgdave.smartpillbox.Adapters.InputFragmentsAdapter;
import com.example.mgdave.smartpillbox.Constants;
import com.example.mgdave.smartpillbox.Database.DBProcessor;
import com.example.mgdave.smartpillbox.Fragments.InputFragment;
import com.example.mgdave.smartpillbox.Models.Message;
import com.example.mgdave.smartpillbox.Models.Pill;
import com.example.mgdave.smartpillbox.R;
import com.example.mgdave.smartpillbox.SmartPillBox;
import com.example.mgdave.smartpillbox.Views.NonSwipeableViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputActivity extends FragmentActivity implements InputFragment.PillInputListener{

    private ArrayList<Fragment> fragments;
    private ArrayList<Pill> pills;
    private NonSwipeableViewPager viewPager;
    private InputFragmentsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pills = new ArrayList<>();
        setContentView(R.layout.activity_input_layout);
        viewPager = (NonSwipeableViewPager) findViewById(R.id.pager);
        setUpFragments();
        adapter = new InputFragmentsAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    private void setUpFragments() {
        fragments = new ArrayList<>();
        fragments.add(new InputFragment());
        fragments.add(new InputFragment());
        fragments.add(new InputFragment());
    }

    @Override
    public void nextPill(Pill pill) {
        if (viewPager.getCurrentItem() != adapter.getCount() - 1) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            pills.add(pill);
        }
    }

    @Override
    public void doneAdding(Pill pill) {
        pills.add(pill);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Constants.PILLS_POST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray array = jsonObject.getJSONArray("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Gson gson = new Gson();
                String pillParameters = gson.toJson(pills);
                Map<String, String> params = new HashMap<String, String>();
                params.put("pills", pillParameters);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.PILLS_POST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s){
                Gson gson = new Gson();
                Message message = gson.fromJson(s, Message.class);
                DBProcessor.getInstance().insertPills(pills);
                Intent intent = new Intent(InputActivity.this, TakePictureActivity.class);
                startActivity(intent);
                InputActivity.this.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Intent intent = new Intent(InputActivity.this, TakePictureActivity.class);
                startActivity(intent);
                InputActivity.this.finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Gson gson = new Gson();
                String pillParameters = gson.toJson(pills);
                Map<String, String> params = new HashMap<String, String>();
                params.put("pills", pillParameters);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        SmartPillBox.getInstance().addToRequestQueue(stringRequest);


    }
}
