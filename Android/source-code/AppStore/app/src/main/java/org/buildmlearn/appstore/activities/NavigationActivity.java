package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
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
import org.buildmlearn.appstore.adapters.SearchListAdapter;
import org.buildmlearn.appstore.fragments.TabMyApps;
import org.buildmlearn.appstore.fragments.TabStore;
import org.buildmlearn.appstore.models.Apps;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class NavigationActivity extends AppCompatActivity {
    String NAME = "BuildmLearn AppStore";
    String EMAIL = "Promoting mLearning";
    private MaterialDialog mAlertDialog=new MaterialDialog(NavigationActivity.this);
    private static Toolbar mToolbar;                                     // Declaring the Toolbar Object
    private RecyclerView mRecyclerView;                           // Declaring RecyclerView
    private RecyclerView.Adapter mNavigationAdapter;              // Declaring Adapter For Recycler View
    private RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    private static DrawerLayout mDrawer;                                 // Declaring DrawerLayout
    private ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle
    protected FrameLayout frameLayout;
    private String[] columns = new String[] { "_id", "search","image" };
    public static List<Apps> appList=new ArrayList<Apps>();
    private MatrixCursor cursor = new MatrixCursor(columns);
    public static int mActive=1;
    private static SearchView searchView=null;
    private static MenuItem searchItem;
    private static Context mContext;
    public static String searchQuery="";
    public static int mActiveSearchInterface=0;//0-MyApps, 1-Store, 2-Categories, 3-InnerCategories, 4-AppsActivity
    public static int color_divider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mContext=this;
        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.primary));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setTitle("Home");
        setSupportActionBar(mToolbar);
        color_divider=getResources().getColor(R.color.divider);
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
                    if(mActive==recyclerView.getChildPosition(child))return false;
                    switch(recyclerView.getChildPosition(child))
                    {
                        case 0:
                        {
                            break;
                        }
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
                        case 5:
                        {
                            mAlertDialog.setTitle("Feedback")
                                    .setMessage("We just love feedback !")
                                    .setPositiveButton("SEND SMILE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            sendEmail("I love BuildmLearn AppStore. I like the following features:\n");
                                            mAlertDialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("SEND FROWN", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            sendEmail("I didn't like BuildmLearn AppStore. I don't like the following features:\n");
                                            mAlertDialog.dismiss();}
                                    })
                                    .setCanceledOnTouchOutside(true);
                            mAlertDialog.show();
                             break;
                        }
                        case 6:
                        {
                            Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
                            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                startActivity(goToMarket);
                            } catch (ActivityNotFoundException e) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + mContext.getPackageName())));
                            }
                            break;
                        }
                        case 7:
                        {
                            Toast.makeText(mContext,"About page is yet to be created",Toast.LENGTH_SHORT).show();
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

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
    }
    private void sendEmail(String body){
    Intent emailIntent = new Intent(Intent.ACTION_SEND);
    emailIntent.setData(Uri.parse("mailto:"));
    emailIntent.setType("message/rfc822");
    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"srujanjha.jha@gmail.com"});
    emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"srujanjha@outlook.com"});
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "BuildmLearn AppStore Feedback");
    emailIntent.putExtra(Intent.EXTRA_TEXT, body);
    try {
        startActivity(Intent.createChooser(emailIntent, "Give feedback"));
    }
    catch (android.content.ActivityNotFoundException ex) {
        Toast.makeText(NavigationActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
    }
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home_activity, menu);
        getCustomCursor();
        searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) NavigationActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(NavigationActivity.this.getComponentName()));
            if(mActiveSearchInterface==1)searchView.setSuggestionsAdapter(new SearchListAdapter(this, cursor, true));
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    if(mActiveSearchInterface==1)
                        TabMyApps.closeSearch();
                    else if(mActiveSearchInterface==0)
                        TabStore.closeSearch();
                    else if(mActiveSearchInterface==2)
                        CategoriesActivity.closeSearch();
                    else if(mActiveSearchInterface==3)
                        CategoriesView.closeSearch();
                    else if(mActiveSearchInterface==4)
                        AppsActivity.closeSearch();
                    return false;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchQuery=query;
                    refineSearch(query);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    searchQuery=newText;
                    refineSearch(newText);
                    if(searchQuery.equals(""))return false;
                    refreshCursor(newText);
                    return true;
                }
            });
            searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionSelect(int position) {
                    Intent i = new Intent(mContext, AppDetails.class);
                    i.putExtra("App", appList.get(position));
                    mContext.startActivity(i);
                    return false;
                }
                @Override
                public boolean onSuggestionClick(int position) {
                    Intent i = new Intent(mContext, AppDetails.class);
                    i.putExtra("App", appList.get(position));
                    mContext.startActivity(i);
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }
    private void getCustomCursor()    {
        appList.clear();cursor= new MatrixCursor(columns);
        Object[] temp = new Object[] { 0, "search","image" };
        for(int i = 0; i < SplashActivity.appList.size(); i++) {
            appList.add(SplashActivity.appList.get(i));
            temp[0] = i;
            temp[1] = appList.get(i).Name;
            temp[2] = appList.get(i).AppIcon;
            cursor.addRow(temp);
        }
    }
    private void refreshCursor(String query)    {
        appList.clear();cursor= new MatrixCursor(columns);
        int k=0;
        Object[] temp = new Object[] { 0, "search","image" };
        for(int i = 0; i < SplashActivity.appList.size(); i++) {
            if(!SplashActivity.appList.get(i).Name.toLowerCase().contains(query.toLowerCase())){continue;}
            appList.add(SplashActivity.appList.get(i));
            temp[0] = k;
            temp[1] = appList.get(k).Name;
            temp[2] = appList.get(k++).AppIcon;
            cursor.addRow(temp);
        }
        if(mActiveSearchInterface==1)searchView.setSuggestionsAdapter(new SearchListAdapter(this,cursor,true));
    }
    public static void clearSearch()    {
        searchView.clearFocus();
        searchView.setQuery("",false);
        searchItem.collapseActionView();
    }
    private void refineSearch(String query)    {
        if(mActiveSearchInterface==1)
            TabMyApps.refineSearch(query);
        else if(mActiveSearchInterface==0)
            TabStore.refineSearch(query);
        else if(mActiveSearchInterface==2)
            CategoriesActivity.refineSearch(query);
        else if(mActiveSearchInterface==3)
            CategoriesView.refineSearch(query);
        else if(mActiveSearchInterface==4)
            AppsActivity.refineSearch(query);

    }

}
