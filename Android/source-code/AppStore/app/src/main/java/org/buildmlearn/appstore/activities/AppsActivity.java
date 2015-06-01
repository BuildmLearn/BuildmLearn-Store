package org.buildmlearn.appstore.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.NavigationActivity;
import org.buildmlearn.appstore.activities.SplashActivity;
import org.buildmlearn.appstore.adapters.CardViewAdapter;


public class AppsActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_apps, frameLayout);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rvAppsCard);
        rv.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(this,3);
        rv.setLayoutManager(llm);
        CardViewAdapter adapter = new CardViewAdapter(SplashActivity.appList,this);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apps, menu);
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
