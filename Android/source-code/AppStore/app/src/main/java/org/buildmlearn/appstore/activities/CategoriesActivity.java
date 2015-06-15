package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 26-05-2015.
 */

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.CategoriesAdapter;
import org.buildmlearn.appstore.models.CategoriesCard;

import java.util.List;

public class CategoriesActivity extends NavigationActivity {

    private List<CategoriesCard> mCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationActivity.mActive = 2;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_categories, frameLayout);
        mCategories = CategoriesCard.getCategoriesList();
        RecyclerView categoriesView = (RecyclerView) findViewById(R.id.categoriesView);
        categoriesView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        categoriesView.setLayoutManager(llm);
        CategoriesAdapter adapter = new CategoriesAdapter(mCategories, this);
        categoriesView.setAdapter(adapter);
    }
}
