package com.sunrise.srs.adapter.workoutsetting;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChuHui on 2017/9/7.
 */

public class VrViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments=new ArrayList<>();

    public VrViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public VrViewPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void recycle() {
        mFragments.clear();
        mFragments = null;
    }
}
