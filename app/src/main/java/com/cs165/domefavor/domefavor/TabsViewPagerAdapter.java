package com.cs165.domefavor.domefavor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 *
 * Created by Jilai Zhou on 5/19/2016.
 */
public class TabsViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    private static final String UI_TAB_NEWTASK = "Post New Task";
    private static final String UI_TAB_TASKLIST = "Task List";
    private static final String UI_TAB_PROFILE = "Profile";

    //connect adapter to fragments
    public TabsViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments){
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


    //This method may be called by the ViewPager to obtain a title string to describe the specified page.
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return UI_TAB_NEWTASK ;
            case 1:
                return UI_TAB_TASKLIST;
            case 2:
                return UI_TAB_PROFILE;
            default:
                break;
        }
        return null;
    }
}
