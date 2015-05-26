package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 26-05-2015.
 */

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.CategoriesAdapter;
import org.buildmlearn.appstore.models.CategoriesCard;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationActivity.mActive=2;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_categories, frameLayout);
        initializeData();
        RecyclerView categoriesView = (RecyclerView)findViewById(R.id.categoriesView);
        categoriesView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        categoriesView.setLayoutManager(llm);
        CategoriesAdapter adapter = new CategoriesAdapter(mCategories);
        categoriesView.setAdapter(adapter);
    }
    private List<CategoriesCard> mCategories;

    private void initializeData() {
        mCategories = new ArrayList<>();
        mCategories.add(new CategoriesCard("Science", R.drawable.card_science));
        mCategories.add(new CategoriesCard("Mathematics", R.drawable.card_mathematics));
        mCategories.add(new CategoriesCard("Literature", R.drawable.card_literature));
        mCategories.add(new CategoriesCard("SocialStudies", R.drawable.card_socialstudies));
        mCategories.add(new CategoriesCard("History", R.drawable.card_history));
        mCategories.add(new CategoriesCard("Geography", R.drawable.card_geography));
        mCategories.add(new CategoriesCard("English", R.drawable.card_english));
        mCategories.add(new CategoriesCard("Physics", R.drawable.card_physics));
        mCategories.add(new CategoriesCard("Chemistry", R.drawable.card_chemistry));
        mCategories.add(new CategoriesCard("Biology", R.drawable.card_biology));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
