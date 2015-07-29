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
 * An adapter class for the search list suggestions.
 */
public class SearchListAdapter extends CursorAdapter {
        private TextView text;
        private NetworkImageView image;

    /**
     * Constructor of the Adapter class
     * @param context Context of the current Activity
     * @param c Cursor object to be populated
     */
    public SearchListAdapter(Context context, Cursor c) {
        super(context, c,true);
    }

    /**
     * Inflates the view objects
     * @param context Context of the current activity
     * @param cursor Cursor position of the current view
     * @param parent Viewgroup object, which contains all the object of the view to be populated
     * @return View object which is to be populated
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.search_list_layout, parent, false);
        text = (TextView) view.findViewById(R.id.SrowText);
        image=(NetworkImageView)view.findViewById(R.id.SrowIcon);
        return view;
    }
    /**
     * Binds the content ot different views
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        text.setText(NavigationActivity.appList.get(cursor.getPosition()).Name);
        image.setImageUrl(NavigationActivity.appList.get(cursor.getPosition()).AppIcon, VolleySingleton.getInstance(context).getImageLoader());
    }
}
