package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.SearchListAdapter;
import org.buildmlearn.appstore.fragments.TabMyApps;
import org.buildmlearn.appstore.fragments.TabStore;
import org.buildmlearn.appstore.models.Apps;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class NavigationActivity extends AppCompatActivity {
    private MaterialDialog mAlertDialog=new MaterialDialog(NavigationActivity.this);
    private static Toolbar mToolbar;
    private static DrawerLayout mDrawer;                                 // Declaring DrawerLayout
    private ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle
    public static FrameLayout frameLayout;
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
    protected NavigationView navigationView;

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
        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        mDrawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawer.setStatusBarBackgroundColor(getResources().getColor(R.color.primary_dark));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.start,R.string.close){
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(drawerView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        };
        mDrawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawer.closeDrawers();
                mDrawer.closeDrawer(Gravity.LEFT);
                if(mActive==menuItem.getItemId())return false;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_1: {
                        Intent i = new Intent(NavigationActivity.this, HomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                        menuItem.setChecked(true);
                        break;
                    }
                    case R.id.navigation_item_2: {
                        Intent i = new Intent(NavigationActivity.this, CategoriesActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                        menuItem.setChecked(true);
                        break;
                    }
                    case R.id.navigation_item_3: {
                        Intent i = new Intent(NavigationActivity.this, SettingsActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.navigation_item_4: {
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
                                        mAlertDialog.dismiss();
                                    }
                                })
                                .setCanceledOnTouchOutside(true);
                        mAlertDialog.show();
                        break;
                    }
                    case R.id.navigation_item_5: {
                        Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + mContext.getPackageName())));
                        }
                        break;
                    }
                    case R.id.navigation_item_6: {
                        showSnackBar("About page is yet to be created");
                        break;
                    }
                }
                return true;
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
        showSnackBar("There is no email client installed.");
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
        searchQuery="";
        searchView.onActionViewCollapsed();
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
    public static void showSnackBar(String s)
    {
        Snackbar
                .make(frameLayout, s, Snackbar.LENGTH_LONG)
                .show();
    }
}
