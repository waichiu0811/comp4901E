package com.example.waichiuyung.diov;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by waichiuyung on 18/2/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    HomeFragment tab1;
    SleepingFragment tab2;
    PressureFragment tab3;
    FocusFragment tab4;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return getHomeFragment();
            case 1:
                return getSleepingFragment();
            case 2:
                return getPressureFragment();
            case 3:
                return getFocusFragment();
            default:
                return null;
        }
    }

    public HomeFragment getHomeFragment() {
        if (tab1 == null) {
            tab1 = new HomeFragment();
        }
        return tab1;
    }

    public SleepingFragment getSleepingFragment() {
        if (tab2 == null) {
            tab2 = new SleepingFragment();
        }
        return tab2;
    }

    public PressureFragment getPressureFragment() {
        if (tab3 == null) {
            tab3 = new PressureFragment();
        }
        return tab3;
    }

    public FocusFragment getFocusFragment() {
        if (tab4 == null) {
            tab4 = new FocusFragment();
        }
        return tab4;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}