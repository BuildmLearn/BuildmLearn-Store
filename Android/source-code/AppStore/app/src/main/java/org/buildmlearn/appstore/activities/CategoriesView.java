package org.buildmlearn.appstore.activities;
/**
 * Created by Srujan Jha on 31-05-2015.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.CardViewAdapter;
import org.buildmlearn.appstore.fragments.TabMyApps;
import org.buildmlearn.appstore.fragments.TabStore;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CategoriesView extends NavigationActivity {

    private static String mCategory="";
    private static List<Apps> appList=new ArrayList<>();
    private static RecyclerView mRecyclerView;
    private static Context mContext;
    private static TextView txtCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationActivity.mActive=2;
        NavigationActivity.mActiveSearchInterface=3;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_categories_view, frameLayout);
        mContext=this;
        Intent i=getIntent();
        mCategory=i.getStringExtra("Category");
        getSupportActionBar().setTitle(mCategory);
        initializeData();
        txtCategories=(TextView)findViewById(R.id.txtCategoriesView);
        txtCategories.setVisibility(View.GONE);
        if(appList.size()==0)txtCategories.setVisibility(View.VISIBLE);
        else txtCategories.setVisibility(View.INVISIBLE);
        mRecyclerView = (RecyclerView)findViewById(R.id.rvAppCategories);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(llm);
        CardViewAdapter adapter = new CardViewAdapter(appList,this);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mRecyclerView.setAdapter(adapter);
    }
    private static void initializeData()    {
        appList.clear();
        for(int i=0;i<SplashActivity.appList.size();i++)
        {
            Apps ob=SplashActivity.appList.get(i);
            if(ob.Category.equals(mCategory))appList.add(ob);
        }
    }
    public static void refineSearch(String query)    {
        List<Apps> tempList=new ArrayList<>();
        if(query.equals("")){closeSearch();return;}
        else for(int i=0;i<appList.size();i++)
        {
            if(appList.get(i).Name.toLowerCase().contains(query.toLowerCase()))tempList.add(appList.get(i));
        }
        if(tempList.size()!=0){
            CardViewAdapter adapter = new CardViewAdapter(tempList, mContext);
            mRecyclerView.setAdapter(adapter);
            txtCategories.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        else{
            txtCategories.setText("Sorry, No app in this category matches your search!");
            mRecyclerView.setVisibility(View.GONE);
            txtCategories.setVisibility(View.VISIBLE);
        }

    }
    public static void closeSearch()    {
        CardViewAdapter adapter = new CardViewAdapter(appList, mContext);
        mRecyclerView.setAdapter(adapter);
        if(appList.size()==0){
            txtCategories.setText("There are no apps to be shown in this particular Category!");
            txtCategories.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);}
        else {
            txtCategories.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
