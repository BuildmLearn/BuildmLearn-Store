package org.buildmlearn.appstore.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

/**
 * This activity shows all the apps which belong to a particular category.
 * The user navigates to this page, when a particular category is selected in the Categories Page or from a list of Featured Categories in Home Page.
 */
public class CategoriesView extends NavigationActivity {

    private static String mCategory="";
    private static final List<Apps> appList=new ArrayList<>();
    private static RecyclerView mRecyclerView;
    private static Context mContext;
    private static TextView txtCategories;
    private boolean onBack=false;

    /**
     * The method is executed first when the activity is created.
     * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationActivity.mActive=R.id.navigation_item_2;
        NavigationActivity.mActiveSearchInterface=3;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_categories_view, frameLayout);
        navigationView.getMenu().findItem(R.id.navigation_item_2).setChecked(true);
        mContext=this;
        Intent i=getIntent();
        mCategory=i.getStringExtra("Category");
        onBack=i.getBooleanExtra("Home",false);
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

    /**
     * It initialises the app list for the category selected.
     */
    private static void initializeData()    {
        appList.clear();
        for(int i=0;i<SplashActivity.appList.size();i++)
        {
            Apps ob=SplashActivity.appList.get(i);
            if(ob.Category.equals(mCategory))appList.add(ob);
        }
    }

    /**
     * This method is called from the Navigation Activity, which controls all the search view.
     * @param query A string with search query.
     */
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
            txtCategories.setText(mContext.getResources().getString(R.string.search_category_error));
            mRecyclerView.setVisibility(View.GONE);
            txtCategories.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method is called from the Navigation Activity. It closes the search view for this activity.
     */
    public static void closeSearch()    {
        CardViewAdapter adapter = new CardViewAdapter(appList, mContext);
        mRecyclerView.setAdapter(adapter);
        if(appList.size()==0){
            txtCategories.setText(mContext.getResources().getString(R.string.search_category_error));
            txtCategories.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);}
        else {
            txtCategories.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method is automatically called when back button is pressed. The purpose of this method is to correctly assign the active menu in Navigation drawer to either Categories or Home.
     */
    @Override
    public void onBackPressed(){
        if(onBack){navigationView.getMenu().findItem(R.id.navigation_item_1).setChecked(true);NavigationActivity.mActive=R.id.navigation_item_1;}
        super.onBackPressed();
    }
}
