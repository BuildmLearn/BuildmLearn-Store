package org.buildmlearn.appstore.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.NavigationActivity;
import org.buildmlearn.appstore.utils.VolleySingleton;

/**
 * Created by Srujan Jha on 6/15/2015.
 */
public class SearchListAdapter extends CursorAdapter {
        private TextView text;
        private NetworkImageView image;


    public SearchListAdapter(Context context, Cursor c) {
        super(context, c,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.search_list_layout, parent, false);
        text = (TextView) view.findViewById(R.id.SrowText);
        image=(NetworkImageView)view.findViewById(R.id.SrowIcon);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        text.setText(NavigationActivity.appList.get(cursor.getPosition()).Name);
        image.setImageUrl(NavigationActivity.appList.get(cursor.getPosition()).AppIcon, VolleySingleton.getInstance(context).getImageLoader());
    }
}
