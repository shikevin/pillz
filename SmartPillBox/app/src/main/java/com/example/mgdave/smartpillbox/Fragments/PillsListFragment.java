package com.example.mgdave.smartpillbox.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mgdave.smartpillbox.Adapters.PillsCursorAdapter;
import com.example.mgdave.smartpillbox.Constants;
import com.example.mgdave.smartpillbox.Database.DBProcessor;
import com.example.mgdave.smartpillbox.Database.DataProvider;
import com.example.mgdave.smartpillbox.Models.Pill;
import com.example.mgdave.smartpillbox.R;
import com.example.mgdave.smartpillbox.SmartPillBox;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class PillsListFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "PillsListFragmentTag";

    private PillsCursorAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pills, container, false);
        ListView pillsListView = (ListView) view.findViewById(R.id.pills_listview);

        adapter = new PillsCursorAdapter(getActivity().getApplicationContext(), null);
        pillsListView.setAdapter(adapter);
        getLoaderManager().initLoader(1, null, this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.PILLS_POST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                ArrayList<Pill> pills = gson.fromJson(s, new TypeToken<List<Pill>>(){}.getType());
                DBProcessor.getInstance().insertPills(pills);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        return view;
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getActivity(), DataProvider.PILLS_CONTENT_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
