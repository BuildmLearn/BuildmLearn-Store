package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.ViewPagerAdapter;
import org.buildmlearn.appstore.utils.SlidingTabLayout;

public class HomeActivity extends NavigationActivity {

    private ViewPager mPager;
    private ViewPagerAdapter mPagerAdapter;
    private SlidingTabLayout mTabs;
    private CharSequence Titles[]={"My Apps","Store"};
    private int mNumberoftabs =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationActivity.mActive=1;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mPagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,mNumberoftabs);
        // Assigning ViewPager View and setting the adapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);

        // Assigning the Sliding Tab Layout View
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        // Setting the ViewPager For the SlidingTabsLayout
        mTabs.setViewPager(mPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
