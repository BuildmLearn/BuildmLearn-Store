package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.ViewPagerAdapter;
import org.buildmlearn.appstore.fragments.TabMyApps;
import org.buildmlearn.appstore.fragments.TabStore;
import org.buildmlearn.appstore.utils.AppReader;
import org.buildmlearn.appstore.utils.SlidingTabLayout;

import static org.buildmlearn.appstore.utils.AppReader.listApps;

public class HomeActivity extends NavigationActivity {

    private static ViewPager mPager;
    private ViewPagerAdapter mPagerAdapter;
    private SlidingTabLayout mTabs;
    private CharSequence Titles[]={"Store","My Apps"};
    private int mNumberoftabs =2;
    private boolean mAppSection=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        mAppSection = SP.getBoolean("home_page_start", true);
        NavigationActivity.mActive=1;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);
        listApps(this);
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        if(AppReader.AppList.size()>0) {mNumberoftabs=2;mTabs.setVisibility(View.VISIBLE);}
            else {mNumberoftabs=1;mTabs.setVisibility(View.GONE);}
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs.
        mPagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,mNumberoftabs);
        // Assigning ViewPager View and setting the adapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        if(mAppSection){NavigationActivity.mActiveSearchInterface=0;}
        else {mPager.setCurrentItem(1,true);NavigationActivity.mActiveSearchInterface=1;}
        // Assigning the Sliding Tab Layout View
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                NavigationActivity.mActiveSearchInterface=position;
                NavigationActivity.searchQuery="";
                TabStore.closeSearch();
                TabMyApps.closeSearch();
                getSupportActionBar().setTitle("Home");
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
    public static void MyAppsView()
    {mPager.setCurrentItem(1,true);

    }
}
