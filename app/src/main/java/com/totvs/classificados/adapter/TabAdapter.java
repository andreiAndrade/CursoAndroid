package com.totvs.classificados.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.totvs.classificados.fragment.ListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Totvs on 26/01/2017.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private List<String> mTitles;
    private List<Fragment> mFragments;

    public TabAdapter(FragmentManager fm) {
        super(fm);
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
    }

    public List<Fragment> getFragments() {
        return mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void add(String title, Fragment fragment) {
        mTitles.add(title);
        mFragments.add(fragment);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
