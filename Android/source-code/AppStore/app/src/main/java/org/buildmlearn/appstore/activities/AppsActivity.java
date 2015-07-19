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


public class AppsActivity extends NavigationActivity {
    private static RecyclerView mRecyclerView;
    private static Context mContext;
    private static TextView txtAppsActivity;
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
            txtAppsActivity.setText("Sorry, No app matches your search!");
            mRecyclerView.setVisibility(View.GONE);
            txtAppsActivity.setVisibility(View.VISIBLE);
        }
    }
    public static void closeSearch()    {
        mRecyclerView.setVisibility(View.VISIBLE);
        txtAppsActivity.setVisibility(View.GONE);
        CardViewAdapter adapter = new CardViewAdapter(SplashActivity.appList, mContext);
        mRecyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed()
    {
        if(isDrawerOpened)
        {mDrawer.closeDrawers();mDrawer.closeDrawer(GravityCompat.START);return;}
        NavigationActivity.mActiveSearchInterface=0;
        super.onBackPressed();
    }
}
