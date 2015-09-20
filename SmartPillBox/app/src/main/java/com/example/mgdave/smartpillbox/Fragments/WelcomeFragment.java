package com.example.mgdave.smartpillbox.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mgdave.smartpillbox.Activity.CurrentPillsActivity;
import com.example.mgdave.smartpillbox.Activity.InputActivity;
import com.example.mgdave.smartpillbox.R;

public class WelcomeFragment extends Fragment implements View.OnClickListener {

    public static String TAG = "WelcomeFragmentTag";

    private Button continueButton;
    private Button newButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        continueButton = (Button) view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this);
        newButton = (Button) view.findViewById(R.id.new_button);
        newButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_button: {
                Intent intent = new Intent(getActivity(), CurrentPillsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.new_button: {
                Intent intent = new Intent(getActivity(), InputActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
