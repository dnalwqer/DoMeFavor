package com.cs165.domefavor.domefavor;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cs165.domefavor.domefavor.view.SlidingTabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragments = new ArrayList<Fragment>();
        fragments.add(new FragmentNewTask());
        fragments.add(new FragmentTaskList());
        fragments.add(new FragmentProfile());
        //use adapter to bind the slidingTabLayout and ViewPager;
        TabsViewPagerAdapter myViewPageAdapter = new TabsViewPagerAdapter(this.getFragmentManager(), fragments);
        viewPager.setAdapter(myViewPageAdapter);

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }
}
