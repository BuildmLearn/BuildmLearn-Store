package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.NavigationAdapter;
import org.buildmlearn.appstore.adapters.ViewPagerAdapter;
import org.buildmlearn.appstore.utils.SlidingTabLayout;

public class NavigationActivity extends AppCompatActivity {
    String NAME = "Srujan Jha";
    String EMAIL = "srujanjha.jha@gmail.com";

    private Toolbar mToolbar;                                     // Declaring the Toolbar Object
    private RecyclerView mRecyclerView;                           // Declaring RecyclerView
    private RecyclerView.Adapter mNavigationAdapter;              // Declaring Adapter For Recycler View
    private RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    private DrawerLayout mDrawer;                                 // Declaring DrawerLayout
    private ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle
    protected FrameLayout frameLayout;

    public static int mActive=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        mNavigationAdapter = new NavigationAdapter(NAME,EMAIL,mActive);       // Creating the Adapter of NavigationAdapter class(which we are going to see in a bit)
        mRecyclerView.setAdapter(mNavigationAdapter);                              // Setting the adapter to RecyclerView
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager
        mDrawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawer.setStatusBarBackgroundColor(getResources().getColor(R.color.primary_dark));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.start,R.string.close);
        mDrawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        final GestureDetector mGestureDetector = new GestureDetector(NavigationActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    mDrawer.closeDrawers();
                    switch(recyclerView.getChildPosition(child))
                    {
                        case 1:
                        {
                            startActivity(new Intent(NavigationActivity.this,HomeActivity.class));break;
                        }
                        case 2:
                        {
                            startActivity(new Intent(NavigationActivity.this,CategoriesActivity.class));break;
                        }
                        case 3:
                        {
                            startActivity(new Intent(NavigationActivity.this,SettingsActivity.class));break;
                        }
                        case 4:
                        {
                            Toast.makeText(getApplicationContext(), "Feedback is not enabled right now.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_home_activity, menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home_activity, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) NavigationActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(NavigationActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
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
