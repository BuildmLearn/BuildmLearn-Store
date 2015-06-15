package org.buildmlearn.appstore.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.CardViewAdapter;

public class SearchResultsActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_search_results, frameLayout);
        getSupportActionBar().setTitle(NavigationActivity.searchQuery);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rvSearchCard);
        rv.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(this,3);
        rv.setLayoutManager(llm);
        CardViewAdapter adapter = new CardViewAdapter(NavigationActivity.appList,this);
        System.out.println("SearchResults"+adapter.getItemCount()+" "+NavigationActivity.appList);
        rv.setAdapter(adapter);
    }
}
