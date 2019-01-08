package com.example.q.cs496_2.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.q.cs496_2.R;
import com.example.q.cs496_2.adapters.PagerAdapter;

public class FragmentActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private TabLayout TabBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();

        setContentView(R.layout.fragment_main);

        TabBar = (TabLayout) findViewById(R.id.tabLayout);
        TabBar.setupWithViewPager(mViewPager);
        TabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(TabBar));
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), 3);
        mViewPager.setAdapter(mPagerAdapter);
    }
}

