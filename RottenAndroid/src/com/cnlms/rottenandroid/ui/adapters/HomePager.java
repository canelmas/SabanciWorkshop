package com.cnlms.rottenandroid.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cnlms.rottenandroid.ui.fragments.FragInTheaterMovies;
import com.cnlms.rottenandroid.ui.fragments.FragSearch;

/**
 * Author: Can Elmas <can.elmas@pozitron.com>
 * Date: 12/19/12 2:30 PM
 */
public final class HomePager extends FragmentPagerAdapter {

    private static final int NUM_OF_PAGER_ITEMS = 2;

    public HomePager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return position == 0 ? new FragInTheaterMovies() : new FragSearch();

    }

    @Override
    public int getCount() {
        return NUM_OF_PAGER_ITEMS;
    }
}
