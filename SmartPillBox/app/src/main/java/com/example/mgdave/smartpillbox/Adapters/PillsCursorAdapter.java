package com.example.mgdave.smartpillbox.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.mgdave.smartpillbox.Models.Pill;
import com.example.mgdave.smartpillbox.R;

public class PillsCursorAdapter extends CursorAdapter{

    public PillsCursorAdapter (Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_pills_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView pillNameText = (TextView) view.findViewById(R.id.pill_name);
        TextView pillDescriptionText = (TextView) view.findViewById(R.id.pill_description);
        TextView pillAmountText = (TextView) view.findViewById(R.id.pill_amount);
        Pill pill = Pill.pillFromCursor(cursor);
        pillNameText.setText(pill.getName());
        pillDescriptionText.setText(pill.getDescription());
        pillAmountText.setText("" + pill.getCurrentAmount());
    }
}
