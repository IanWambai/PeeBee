package com.makeshift.kuhustle.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.makeshift.kuhustle.fragments.MainActivityFragment;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    protected static final String[] TITLES = new String[]{"All",
            "Open", "Won", "Closed"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putInt(MainActivityFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ViewPagerAdapter.TITLES[position % TITLES.length];
    }
}
