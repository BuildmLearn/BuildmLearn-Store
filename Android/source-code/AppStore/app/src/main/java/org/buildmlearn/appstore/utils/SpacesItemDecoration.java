package org.buildmlearn.appstore.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * It is a helper class which is used to provide offsets between the cards in CardView.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    /**
     * Constructor of the helper class.
     * @param space Integer value of the space to be provided as offsets.
     */
    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    /**
     * Gets the offsets.
     * @param outRect Rectangle Object
     * @param view View object where the offsets will be provided.
     * @param parent RecyclerView object of the CardView.
     * @param state RecyclerView.State Object
     */
    @SuppressWarnings("deprecation")
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if(parent.getChildPosition(view) == 0)
            outRect.top = space;
    }
}
