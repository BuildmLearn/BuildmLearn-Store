package org.buildmlearn.appstore.activities;

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

/**
 * This class is the Home Page, which has a viewpager to display tabs for Store section and My-Apps section.
 */
public class HomeActivity extends NavigationActivity {

    private final CharSequence[] TITLES={"Store","My Apps"};
    private static ViewPager mPager;
    private MaterialDialog mAlertDialog;

    /**
     * The method is executed first when the activity is created.
     * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean mAppSection = SP.getBoolean("home_page_start", true);
        // To make sure, when the user selects the Home button on Navigation drawer, it should not be executed, as the user is already in the Home Page.
        NavigationActivity.mActive = R.id.navigation_item_1;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);
        navigationView.getMenu().findItem(R.id.navigation_item_1).setChecked(true);
        listApps(this);
        SlidingTabLayout mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        int mNumberoftabs;
        if(AppReader.AppList.size()>0) {
            mNumberoftabs =2;
            mTabs.setVisibility(View.VISIBLE);}
            else {
            mNumberoftabs =1;
            mTabs.setVisibility(View.GONE);}
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs.
        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), TITLES, mNumberoftabs);
        // Assigning ViewPager View and setting the adapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        // This ensures the searching capabilities of the Search tool.
        if(mAppSection){NavigationActivity.mActiveSearchInterface=0;}
        else {mPager.setCurrentItem(1,true);NavigationActivity.mActiveSearchInterface=1;}
        // Assigning the Sliding Tab Layout View
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            /**
             * When the page selection is changed, search view should reset. The app list on the My_Apps section is also refreshed, just in case the user has installed any app form the Store section.
             * @param position 0:Store Section; 1: My-Apps Section
             */
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
        // This makes the tabs Space Evenly in Available width
        mTabs.setDistributeEvenly();
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return HomeActivity.this.getResources().getColor(R.color.tabsScrollColor);
            }
        });
        // Setting the ViewPager For the SlidingTabsLayout
        mTabs.setViewPager(mPager);
    }

    /**
     * Set the current view to My-Apps section. This method is helpful when the user selects to open the app from My-Apps section in the Settings Page.
     */
    public static void MyAppsView()
    {
        mPager.setCurrentItem(1, true);
    }

    /**
     * This method is automatically called when the user presses the back button on his mobile. It closes the Navigation Drawer if its open. Otherwise, it displays a popup to close the app.
     */
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
                        HomeActivity.this.finish();
                        System.exit(0);
                    }
                });
        mAlertDialog.show();
    }
}
