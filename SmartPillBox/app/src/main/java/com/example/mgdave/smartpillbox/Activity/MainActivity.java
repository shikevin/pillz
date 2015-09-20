package com.example.mgdave.smartpillbox.Activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.mgdave.smartpillbox.Database.DBProcessor;
import com.example.mgdave.smartpillbox.Models.Pill;
import com.example.mgdave.smartpillbox.R;
import com.example.mgdave.smartpillbox.Fragments.WelcomeFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_fragment_holder, new WelcomeFragment(), WelcomeFragment.TAG)
                    .commit();
        }
    }
}