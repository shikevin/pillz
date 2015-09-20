package com.example.mgdave.smartpillbox.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.mgdave.smartpillbox.Fragments.TakePictureFragment;
import com.example.mgdave.smartpillbox.R;

public class TakePictureActivity extends FragmentActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_fragment_holder, new TakePictureFragment(), TakePictureFragment.TAG)
                    .commit();
        }
    }
}
