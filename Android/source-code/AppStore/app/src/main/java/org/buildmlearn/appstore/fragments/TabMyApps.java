package org.buildmlearn.appstore.fragments;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.adapters.MyAppsViewAdapter;
import org.buildmlearn.appstore.models.AppInfo;

import java.util.ArrayList;

import static org.buildmlearn.appstore.utils.AppReader.listApps;

public class TabMyApps extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_my_apps, container, false);
        TextView txtMyApps=(TextView)v.findViewById(R.id.txtMyApps);
        ArrayList<AppInfo> appList=listApps(v.getContext());
        if(appList.size()>0)txtMyApps.setVisibility(View.GONE);
        else txtMyApps.setVisibility(View.VISIBLE);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.rvMyAppCard);
        rv.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(v.getContext(), 3);
        rv.setLayoutManager(llm);
        MyAppsViewAdapter adapter = new MyAppsViewAdapter(appList, v.getContext());
        rv.setAdapter(adapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing);
        rv.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        return v;
    }
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if(parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }
}