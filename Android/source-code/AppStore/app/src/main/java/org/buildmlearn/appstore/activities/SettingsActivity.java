package org.buildmlearn.appstore.activities;

import android.os.Bundle;

import org.buildmlearn.appstore.R;

public class SettingsActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationActivity.mActive = 3;
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);
    }

}
