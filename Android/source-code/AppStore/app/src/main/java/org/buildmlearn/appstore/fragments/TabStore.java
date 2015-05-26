package org.buildmlearn.appstore.fragments;

/**
 * Created by Srujan Jha on 25-05-2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.buildmlearn.appstore.R;

public class TabStore extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_store, container, false);
        return v;
    }
}