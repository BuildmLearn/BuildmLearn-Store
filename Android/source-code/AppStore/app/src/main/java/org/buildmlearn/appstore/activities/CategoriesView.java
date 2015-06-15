package org.buildmlearn.appstore.activities;
/**
 * Created by Srujan Jha on 31-05-2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.CardViewAdapter;
import org.buildmlearn.appstore.models.Apps;

import java.util.ArrayList;
import java.util.List;

public class CategoriesView extends NavigationActivity {

    private static String mCategory="";
    private static List<Apps> appList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationActivity.mActive=2;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_categories_view, frameLayout);
        Intent i=getIntent();
        mCategory=i.getStringExtra("Category");
        android.support.v7.app.ActionBar tool_bar=getSupportActionBar();
        tool_bar.setTitle(mCategory);
        initializeData();
        TextView txtCategoriesView=(TextView)findViewById(R.id.txtCategoriesView);
        if(appList.size()==0)txtCategoriesView.setVisibility(View.VISIBLE);
        else txtCategoriesView.setVisibility(View.INVISIBLE);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rvAppCategories);
        rv.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(this,3);
        rv.setLayoutManager(llm);
        CardViewAdapter adapter = new CardViewAdapter(appList,this);
        rv.setAdapter(adapter);
    }
    private static void initializeData()
    {
        appList.clear();
        for(int i=0;i<SplashActivity.appList.size();i++)
        {
            Apps ob=SplashActivity.appList.get(i);
            if(ob.Category.equals(mCategory))appList.add(ob);
        }
    }
}
