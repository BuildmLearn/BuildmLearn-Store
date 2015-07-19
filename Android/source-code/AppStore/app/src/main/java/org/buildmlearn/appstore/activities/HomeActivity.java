package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.ViewPagerAdapter;
import org.buildmlearn.appstore.fragments.TabMyApps;
import org.buildmlearn.appstore.fragments.TabStore;
import org.buildmlearn.appstore.utils.AppReader;
import org.buildmlearn.appstore.utils.SlidingTabLayout;

import me.drakeet.materialdialog.MaterialDialog;

import static org.buildmlearn.appstore.utils.AppReader.listApps;

public class HomeActivity extends NavigationActivity {

    private static ViewPager mPager;
    private final CharSequence[] Titles={"Store","My Apps"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean mAppSection = SP.getBoolean("home_page_start", true);
        NavigationActivity.mActive = R.id.navigation_item_1;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);
        navigationView.getMenu().findItem(R.id.navigation_item_1).setChecked(true);
        listApps(this);
        SlidingTabLayout mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        int mNumberoftabs = 2;
        if(AppReader.AppList.size()>0) {
            mNumberoftabs =2;
            mTabs.setVisibility(View.VISIBLE);}
            else {
            mNumberoftabs =1;
            mTabs.setVisibility(View.GONE);}
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs.
        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, mNumberoftabs);
        // Assigning ViewPager View and setting the adapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        if(mAppSection){NavigationActivity.mActiveSearchInterface=0;}
        else {mPager.setCurrentItem(1,true);NavigationActivity.mActiveSearchInterface=1;}
        // Assigning the Sliding Tab Layout View
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    TabMyApps.refreshList();
                }
                NavigationActivity.mActiveSearchInterface = position;
                NavigationActivity.searchQuery = "";
                TabStore.closeSearch();
                TabMyApps.closeSearch();
                getSupportActionBar().setTitle("Home");
                NavigationActivity.clearSearch();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mTabs.setDistributeEvenly(); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
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
    {
        mPager.setCurrentItem(1, true);
    }
    private MaterialDialog mAlertDialog;

    @Override
    public void onBackPressed(){
        if(isDrawerOpened)
        {mDrawer.closeDrawers();mDrawer.closeDrawer(GravityCompat.START);return;}
        mAlertDialog = new MaterialDialog(this)
                .setTitle("Exit")
                .setMessage("Do you want to close BuildmLearn Store ?")
                .setPositiveButton("NO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.dismiss();
                    }
                })
                .setNegativeButton("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.dismiss();
                        finish();
                        System.exit(0);
                    }
                });
        mAlertDialog.show();
    }
}
