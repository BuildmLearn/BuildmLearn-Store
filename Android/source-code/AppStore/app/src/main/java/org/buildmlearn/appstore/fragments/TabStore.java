package org.buildmlearn.appstore.fragments;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.AppsActivity;
import org.buildmlearn.appstore.activities.CategoriesActivity;
import org.buildmlearn.appstore.activities.SplashActivity;
import org.buildmlearn.appstore.adapters.CardCategoriesAdapter;
import org.buildmlearn.appstore.adapters.CardViewAdapter;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TabStore extends Fragment {

    private static RecyclerView mRecyclerView1,mRecyclerView2;
    private static TextView txtAppsStore;
    private static Context mContext;
    private static TextView txtStore;
    private static int x=4,y=6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_store, container, false);
        mContext = v.getContext();
        txtStore=(TextView)v.findViewById(R.id.txtStore);
        txtAppsStore=(TextView)v.findViewById(R.id.txtAppsStore);
        TextView txtMoreApps = (TextView) v.findViewById(R.id.txtMoreApps);
        TextView txtMoreCategories = (TextView) v.findViewById(R.id.txtMoreCategories);
        txtMoreApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, AppsActivity.class);
                startActivity(i);
            }
        });
        txtMoreCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, CategoriesActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                ((Activity)mContext).finish();
            }
        });
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
        x= Integer.parseInt(SP.getString("number_featured_categories", "4"));
        y= Integer.parseInt(SP.getString("number_featured_apps","6"));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing);
        mRecyclerView1 = (RecyclerView) v.findViewById(R.id.rvCategoriesCard);
        mRecyclerView1.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView1.setLayoutManager(llm);
        CardCategoriesAdapter adapter1 = new CardCategoriesAdapter(mContext,x);
        mRecyclerView1.setAdapter(adapter1);
        mRecyclerView1.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mRecyclerView2 = (RecyclerView) v.findViewById(R.id.rvAppCard);
        mRecyclerView2.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(v.getContext(), 3);
        mRecyclerView2.setLayoutManager(glm);
        CardViewAdapter adapter2 = new CardViewAdapter(SplashActivity.appList, v.getContext(),y);
        mRecyclerView2.setAdapter(adapter2);
        mRecyclerView2.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        return v;
    }
    public static void refineSearch(String query)    {
        txtAppsStore.setText("Search Results");
        List<Apps> tempList=new ArrayList<>();
        if(query.equals("")){closeSearch();return;}
        else for(int i=0;i<SplashActivity.appList.size();i++)
        {
            if(SplashActivity.appList.get(i).Name.toLowerCase().contains(query.toLowerCase()))tempList.add(SplashActivity.appList.get(i));
        }
        if(tempList.size()!=0){
            CardViewAdapter adapter = new CardViewAdapter(tempList, mContext);
            mRecyclerView2.setAdapter(adapter);
            txtStore.setVisibility(View.GONE);
            mRecyclerView2.setVisibility(View.VISIBLE);
        }
        else{
            txtStore.setText("Sorry, No app matches your search!");
            mRecyclerView2.setVisibility(View.GONE);
            txtStore.setVisibility(View.VISIBLE);
        }
    }
    public static void closeSearch()    {
        txtAppsStore.setText("Featured Apps");
        mRecyclerView2.setVisibility(View.VISIBLE);
        txtStore.setVisibility(View.GONE);
        CardViewAdapter adapter = new CardViewAdapter(SplashActivity.appList, mContext,y);
        mRecyclerView2.setAdapter(adapter);
    }
}