package org.buildmlearn.appstore.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.CardViewAdapter;


public class AppsActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_apps, frameLayout);
        getSupportActionBar().setTitle("AppStore");
        RecyclerView rv = (RecyclerView)findViewById(R.id.rvAppsCard);
        rv.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(this,3);
        rv.setLayoutManager(llm);
        CardViewAdapter adapter = new CardViewAdapter(SplashActivity.appList,this);
        rv.setAdapter(adapter);
    }
}
