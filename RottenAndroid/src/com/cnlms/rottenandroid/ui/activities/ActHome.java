package com.cnlms.rottenandroid.ui.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.cnlms.rottenandroid.R;
import com.cnlms.rottenandroid.ui.adapters.HomePager;

/**
 * Author: Can Elmas <can.elmas@pozitron.com>
 * Date: 12/19/12 12:23 PM
 */
public final class ActHome extends FragmentActivity implements ViewPager.OnPageChangeListener, ActionBar.TabListener {

    /**
     *  Not Backward Compatible Native Action Bar
     *
     *  Consider ActionBarSherlock for your other projects
     *  http://actionbarsherlock.com/
     */
    private ActionBar actionBar;

    /**
     *  Main View Pager
     */
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /**
         *  Main Activity Content
         */
        setContentView(R.layout.act_home);


        /**
         *  initialize View Pager
         */
        initializeViewPager();


        /**
         *  initialize Action Bar
         */
        initializeActionBar();


    }

    private void initializeActionBar() {

        /**
         *  Get action bar
         */
        actionBar = getActionBar();

        /**
         *  Set Navigation mode to TAB
         */
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        /**
         *  Add two tabs
         */
        actionBar.addTab(actionBar.newTab().setText("In Theaters").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Movie Search").setTabListener(this));

    }

    private void initializeViewPager() {

        viewPager = (ViewPager) findViewById(R.id.homePager);

        /**
         *  View Pager adapter that will provide the fragments
         */
        viewPager.setAdapter(new HomePager(getSupportFragmentManager()));

        /**
         *  Listen for page changes
         */
        viewPager.setOnPageChangeListener(this);

    }

    /**
     *  View Pager Callback Methods
     */

    @Override
    public void onPageSelected(int position) {

        /**
         *  Select proper Action Bar tab when a page is selected via ViewPager
         */
        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
        //  ignore for this project
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //  ignore for this project
    }


    /**
     *  Action Bar Callback Methods
     */

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        /**
         *  Select proper ViewPager item when an ActionBar tab is selected
         */
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //  ignore for this project
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //  ignore for this project
    }
}
