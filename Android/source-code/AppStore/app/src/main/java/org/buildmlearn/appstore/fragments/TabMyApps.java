package org.buildmlearn.appstore.fragments;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.HomeActivity;
import org.buildmlearn.appstore.adapters.MyAppsViewAdapter;
import org.buildmlearn.appstore.models.AppInfo;
import org.buildmlearn.appstore.utils.AppReader;
import org.buildmlearn.appstore.utils.SpacesItemDecoration;

import java.util.ArrayList;


public class TabMyApps extends Fragment {
    private static ArrayList<AppInfo> appList;
    private static RecyclerView mRecyclerView;
    private static Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_my_apps, container, false);
        mContext = v.getContext();
        appList = AppReader.AppList;
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rvMyAppCard);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(v.getContext(), 3);
        mRecyclerView.setLayoutManager(llm);

        MyAppsViewAdapter adapter = new MyAppsViewAdapter(appList, v.getContext());
        mRecyclerView.setAdapter(adapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        return v;
    }
    public static void refineSearch(String query) {
        ArrayList<AppInfo> tempList = new ArrayList<>();
        if (query.equals("")) {closeSearch();return;}
        else for (int i = 0; i < appList.size(); i++) {
            if (appList.get(i).Name.toLowerCase().contains(query.toLowerCase()))
                tempList.add(appList.get(i));
        }

        if (tempList.size() != 0) {
            MyAppsViewAdapter adapter = new MyAppsViewAdapter(tempList, mContext);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
        }
    }
    public static void closeSearch()    {
        MyAppsViewAdapter adapter = new MyAppsViewAdapter(appList, mContext);
        mRecyclerView.setAdapter(adapter);
        if (appList.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);}
        else {
            mRecyclerView.setVisibility(View.GONE);}
    }
    public static void refreshList()
    {
        appList=AppReader.AppList;
        MyAppsViewAdapter adapter = new MyAppsViewAdapter(appList, mContext);
        mRecyclerView.setAdapter(adapter);
        HomeActivity.MyAppsView();
    }
}