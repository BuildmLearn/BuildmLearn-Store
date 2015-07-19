package org.buildmlearn.appstore.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.FullScreenImageAdapter;
import org.buildmlearn.appstore.utils.VolleySingleton;

/**
 * Created by sruja on 6/15/2015.
 */
public class FullScreenViewActivity extends Activity

    {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fullscreen_view);
            ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            Intent i = getIntent();
            int position = i.getIntExtra("position", 0);
            FullScreenImageAdapter adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, AppDetails.mScreenshots, VolleySingleton.getInstance(this).getImageLoader());
            viewPager.setAdapter(adapter);
            // displaying selected image first
            viewPager.setCurrentItem(position);
        }
}
