package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.ViewPagerAdapter;
import org.buildmlearn.appstore.fragments.TabMyApps;
import org.buildmlearn.appstore.fragments.TabStore;
import org.buildmlearn.appstore.utils.SlidingTabLayout;

public class HomeActivity extends NavigationActivity {

    private ViewPager mPager;
    private ViewPagerAdapter mPagerAdapter;
    private SlidingTabLayout mTabs;
    private CharSequence Titles[]={"My Apps","Store"};
    private int mNumberoftabs =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean AppSection = SP.getBoolean("home_page_start", true);
        NavigationActivity.mActive=1;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs.
        mPagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,mNumberoftabs);
        // Assigning ViewPager View and setting the adapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        if(AppSection){NavigationActivity.mActiveSearchInterface=0;}
        else {mPager.setCurrentItem(1,true);NavigationActivity.mActiveSearchInterface=1;}
        // Assigning the Sliding Tab Layout View
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                NavigationActivity.mActiveSearchInterface=position;
                NavigationActivity.searchQuery="";
                TabStore.closeSearch();
                TabMyApps.closeSearch();
                getSupportActionBar().collapseActionView();
                getSupportActionBar().setTitle("Home");
                if(NavigationActivity.searchQuery.equals("")){return;}
                NavigationActivity.clearSearch();
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        mTabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs.
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
    public static void reload()
    {

    }
}
