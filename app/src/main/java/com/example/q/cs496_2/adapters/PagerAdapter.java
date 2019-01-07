package com.example.q.cs496_2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.q.cs496_2.fragments.InformationFragment;
import com.example.q.cs496_2.fragments.MatchFragment;
import com.example.q.cs496_2.fragments.OtherFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InformationFragment tab1 = new InformationFragment();
                return tab1;
            case 1:
                OtherFragment tab2 = new OtherFragment();
                return tab2;
            case 2:
                MatchFragment tab3 = new MatchFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}