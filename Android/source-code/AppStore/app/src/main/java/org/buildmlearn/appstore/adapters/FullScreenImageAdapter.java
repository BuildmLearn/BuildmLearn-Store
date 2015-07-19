package org.buildmlearn.appstore.adapters;

/**
 * Created by Srujan Jha on 6/15/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.buildmlearn.appstore.R;

public class FullScreenImageAdapter extends PagerAdapter {

    private final Activity _activity;
    private final String[] _imagePaths;
    private final ImageLoader _imageLoader;

    // constructor
    public FullScreenImageAdapter(Activity activity,String[] imagePaths,ImageLoader imageLoader) {
        this._activity = activity;
        this._imagePaths = imagePaths;
        this._imageLoader=imageLoader;
    }

    @Override
    public int getCount() {
        return this._imagePaths.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        NetworkImageView imgDisplay;
        Button btnClose;

        LayoutInflater inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container, false);

        imgDisplay = (NetworkImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

        imgDisplay.setImageUrl(_imagePaths[0], _imageLoader);

        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }
}