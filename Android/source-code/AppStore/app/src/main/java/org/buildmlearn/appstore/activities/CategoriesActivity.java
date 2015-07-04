package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 26-05-2015.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.CategoriesAdapter;
import org.buildmlearn.appstore.fragments.TabMyApps;
import org.buildmlearn.appstore.fragments.TabStore;
import org.buildmlearn.appstore.models.CategoriesCard;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends NavigationActivity {

    private static List<CategoriesCard> mCategories;
    private static RecyclerView mRecyclerView;
    private static Context mContext;
    private static TextView txtCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationActivity.mActive = R.id.navigation_item_2;
        NavigationActivity.mActiveSearchInterface=2;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_categories, frameLayout);
        navigationView.getMenu().findItem(R.id.navigation_item_2).setChecked(true);
        mContext=this;
        mCategories = CategoriesCard.getCategoriesList();
        getSupportActionBar().setTitle("Categories");
        txtCategories=(TextView)findViewById(R.id.txtCategoriesActivity);
        txtCategories.setVisibility(View.GONE);
        mRecyclerView = (RecyclerView) findViewById(R.id.categoriesView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        CategoriesAdapter adapter = new CategoriesAdapter(mCategories, this);
        mRecyclerView.setAdapter(adapter);
    }
    public static void refineSearch(String query)    {
        List<CategoriesCard> tempList=new ArrayList<>();
        if(query.equals("")){closeSearch();return;}
        else for(int i=0;i<mCategories.size();i++)
        {
            if(mCategories.get(i).Name.toLowerCase().contains(query.toLowerCase()))tempList.add(mCategories.get(i));
        }
        if(tempList.size()!=0){
            CategoriesAdapter adapter = new CategoriesAdapter(tempList, mContext);
            mRecyclerView.setAdapter(adapter);
            txtCategories.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        else{
            txtCategories.setText("Sorry, No category matches your search!");
            mRecyclerView.setVisibility(View.GONE);
            txtCategories.setVisibility(View.VISIBLE);
        }
    }
    public static void closeSearch()    {
        CategoriesAdapter adapter = new CategoriesAdapter(mCategories, mContext);
        mRecyclerView.setAdapter(adapter);
        System.out.println(mCategories.size());
        if(mCategories.size()==0){
            txtCategories.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);}
        else {
            txtCategories.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
