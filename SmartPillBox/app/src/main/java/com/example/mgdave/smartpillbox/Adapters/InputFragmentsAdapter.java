package com.example.mgdave.smartpillbox.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class InputFragmentsAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments;

    public InputFragmentsAdapter(android.support.v4.app.FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
