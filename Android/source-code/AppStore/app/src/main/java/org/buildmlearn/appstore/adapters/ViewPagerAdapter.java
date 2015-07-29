package org.buildmlearn.appstore.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.buildmlearn.appstore.fragments.TabMyApps;
import org.buildmlearn.appstore.fragments.TabStore;

/**
 * This is an adapter class which populates the fragments(Store and My-Apps) in the Home Page.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final CharSequence[] Titles; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    private final int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    /**
     * Build a Constructor and assign the passed Values to appropriate values in the class
     * @param mFragmentManager Fragment Manager object
     * @param mTitles Array of titles for the tabs
     * @param mNumbOfTabs Number of tabs
     */
    public ViewPagerAdapter(FragmentManager mFragmentManager,CharSequence mTitles[], int mNumbOfTabs) {
        super(mFragmentManager);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabs;
    }

    /**
     * This method return the fragment for the every position in the View Pager
     * @param position Position of the current view in the viewpager
     * @return According to the position of the current view, respective fragment is returned
     */
    @Override
    public Fragment getItem(int position) {
        if(position == 1) // if the position is 0 we are returning the First tab
        {
            return new TabMyApps();
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            return new TabStore();
        }
    }

    /**
     * This method return the titles for the Tabs in the Tab Strip
     * @param position Position of the current view in the viewpager
     * @return The title of the current view
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    /**
     * This method return the Number of tabs for the tabs Strip
     */
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}