package org.buildmlearn.appstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.CardViewAdapter;
import org.buildmlearn.appstore.models.Apps;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends NavigationActivity {
private List<Apps> appList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_search_results, frameLayout);
        getSupportActionBar().setTitle("\"" + NavigationActivity.searchQuery + "\"");
        Intent intent=getIntent();
        String query=intent.getStringExtra("Search");
        appList=new ArrayList<>();
        for(int i=0;i<SplashActivity.appList.size();i++)
        {
            if(!SplashActivity.appList.get(i).Name.toLowerCase().contains(query.toLowerCase())){continue;}
            appList.add(SplashActivity.appList.get(i));
        }
        RecyclerView rv = (RecyclerView)findViewById(R.id.rvSearchCard);
        rv.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(this,3);
        rv.setLayoutManager(llm);
        final CardViewAdapter adapter = new CardViewAdapter(appList,this);
        rv.setAdapter(adapter);
    }
}
