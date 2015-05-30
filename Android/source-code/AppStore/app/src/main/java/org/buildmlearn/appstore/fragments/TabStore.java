package org.buildmlearn.appstore.fragments;

/**
 * Created by Srujan Jha on 25-05-2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.SplashActivity;
import org.buildmlearn.appstore.adapters.CardViewAdapter;

public class TabStore extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_store, container, false);
        RecyclerView rv = (RecyclerView)v.findViewById(R.id.rvAppCard);
        rv.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(v.getContext(),3);
        rv.setLayoutManager(llm);
        CardViewAdapter adapter = new CardViewAdapter(SplashActivity.appList);
        rv.setAdapter(adapter);
        return v;
    }
}