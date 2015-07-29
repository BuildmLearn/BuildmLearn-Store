package org.buildmlearn.appstore.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.CategoriesAdapter;
import org.buildmlearn.appstore.models.CategoriesCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity shows all the categories. The user is navigated to this page, when "Categories" is selected in the navigation drawer
 * or "more" button is pressed in the Featured Categories section on the Home Page.
 */
public class CategoriesActivity extends NavigationActivity {

    private static List<CategoriesCard> mCategories;
    private static RecyclerView mRecyclerView;
    private static Context mContext;
    private static TextView txtCategories;

    /**
     * The method is executed first when the activity is created.
     * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
     */
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

    /**
     * This method is called from the Navigation Activity, which controls all the search view.
     * @param query A string with search query.
     */
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
            txtCategories.setText(mContext.getResources().getString(R.string.search_categories_error));
            mRecyclerView.setVisibility(View.GONE);
            txtCategories.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method is called from the Navigation Activity. It closes the search view for this activity.
     */
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

    /**
     * This activity is automatically called when back button is pressed. It closes the navigation drawer, if it is already in open state. It navigates to Home Activity, after clearing all the Back Stacks.
     */
    @Override
    public void onBackPressed() {
        closeSearch();
        if(isDrawerOpened)
        {mDrawer.closeDrawers();mDrawer.closeDrawer(GravityCompat.START);return;}
        Intent i = new Intent(mContext, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
        Activity activity = (Activity) mContext;
        activity.finish();
    }
}
