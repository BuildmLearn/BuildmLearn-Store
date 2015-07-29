package org.buildmlearn.appstore.adapters;


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

/**
 * An adapter class which populates a full screen image for the screenshots to be viewed better.
 */
public class FullScreenImageAdapter extends PagerAdapter {

    private final Activity _activity;
    private final String[] _imagePaths;
    private final ImageLoader _imageLoader;

    /**
     * Constructor of the adapter class
     * @param activity Activity object of the current Activity.
     * @param imagePaths Array of the path of the images to be populated
     * @param imageLoader ImageLoader object of the Volley Class
     */
    public FullScreenImageAdapter(Activity activity,String[] imagePaths,ImageLoader imageLoader) {
        this._activity = activity;
        this._imagePaths = imagePaths;
        this._imageLoader=imageLoader;
    }

    /**
     * Gets the count of the total images which is to be loaded.
     * @return Count of the number of images
     */
    @Override
    public int getCount() {
        return this._imagePaths.length;
    }

    /**
     * Checks whether the view is the object to be populated
     * @param view View object
     * @param object To be tested
     * @return True if View is the current object, false otherwise
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * Initialises the current item to the container.
     * @param container Viewgroup object
     * @param position Current position of the container
     * @return Object
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        NetworkImageView imgDisplay;
        Button btnClose;
        LayoutInflater inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    /**
     * Removes the current view form the container.
     * @param container Container object to be removed
     * @param position Position of the object to be removed
     * @param object Relative layout object to be removed
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }
}