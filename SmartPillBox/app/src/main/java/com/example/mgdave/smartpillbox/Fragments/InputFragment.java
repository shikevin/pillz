package com.example.mgdave.smartpillbox.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mgdave.smartpillbox.Constants;
import com.example.mgdave.smartpillbox.Models.Pill;
import com.example.mgdave.smartpillbox.R;

import java.util.ArrayList;

public class InputFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String TAG = "InputFragmentTag";

    private int [] frequencyTimes = {5 * Constants.ONE_MINUTE, 10 * Constants.ONE_MINUTE,
                                     Constants.ONE_HOUR, 3*Constants.ONE_HOUR, Constants.ONE_DAY};

    private Pill pill;
    private PillInputListener listener;
    private EditText nameEditText, descriptionEditText;

    @Override
    public void onAttach(Activity activity) {
        try {
            listener = (PillInputListener) activity;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Activity calling must implement listener");
        }
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_pills, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.frequency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.frequency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Button nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);
        Button doneButton = (Button) view.findViewById(R.id.done_button);
        doneButton.setOnClickListener(this);
        pill = new Pill();
        nameEditText = (EditText) view.findViewById(R.id.edit_text_name);
        descriptionEditText = (EditText) view.findViewById(R.id.edit_text_description);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pill.setFrequency(frequencyTimes[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button: {
                listener.nextPill(pill);
                break;
            }
            case R.id.done_button: {
                listener.doneAdding(pill);
                break;
            }
        }
    }

    public interface PillInputListener {
        void nextPill(Pill pill);
        void doneAdding(Pill pill);
    }
}
