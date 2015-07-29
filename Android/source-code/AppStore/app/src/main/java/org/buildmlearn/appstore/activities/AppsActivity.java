package org.buildmlearn.appstore.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.CardViewAdapter;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity deals with the apps shown when "more" button on the Home Page is pressed. It shows a grid of all the apps in the Store.
 * This activity extends Navigation Activity and thus has a navigation drawer(with "Home" selected) and a local search tool.
 */
public class AppsActivity extends NavigationActivity {

    private static RecyclerView mRecyclerView;
    private static Context mContext;
    private static TextView txtAppsActivity;

    /**
     * The method is executed first when the activity is created.
     * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_apps, frameLayout);
        mContext=this;
        NavigationActivity.mActiveSearchInterface=4;
        getSupportActionBar().setTitle("Apps");
        txtAppsActivity=(TextView)findViewById(R.id.txtAppsActivity);
        mRecyclerView = (RecyclerView)findViewById(R.id.rvAppsCard);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(llm);
        CardViewAdapter adapter = new CardViewAdapter(SplashActivity.appList,this);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * This method is called from the Navigation Activity, which controls all the search view.
     * @param query A string with search query.
     */
    public static void refineSearch(String query)    {
        List<Apps> tempList=new ArrayList<>();
        if(query.equals("")){closeSearch();return;}
        else for(int i=0;i<SplashActivity.appList.size();i++)
        {
            if(SplashActivity.appList.get(i).Name.toLowerCase().contains(query.toLowerCase()))tempList.add(SplashActivity.appList.get(i));
        }
        if(tempList.size()!=0){
            CardViewAdapter adapter = new CardViewAdapter(tempList, mContext);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
            txtAppsActivity.setVisibility(View.GONE);
        }
        else{
            txtAppsActivity.setText(mContext.getResources().getString(R.string.search_app_error));
            mRecyclerView.setVisibility(View.GONE);
            txtAppsActivity.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method is called from the Navigation Activity. It closes the search view for this activity.
     */
    public static void closeSearch()    {
        mRecyclerView.setVisibility(View.VISIBLE);
        txtAppsActivity.setVisibility(View.GONE);
        CardViewAdapter adapter = new CardViewAdapter(SplashActivity.appList, mContext);
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * It is automatically called when the back button is pressed. It closes the drawer, if it is in open state.
     */
    @Override
    public void onBackPressed()
    {
        if(isDrawerOpened)
        {mDrawer.closeDrawers();mDrawer.closeDrawer(GravityCompat.START);return;}
        NavigationActivity.mActiveSearchInterface=0;
        super.onBackPressed();
    }
}
