package org.buildmlearn.appstore.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.CategoriesView;
import org.buildmlearn.appstore.activities.NavigationActivity;
import org.buildmlearn.appstore.models.CategoriesCard;

import java.util.List;

/**
 * This adapter class populates cards related to categories in the Categories Activity.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{

    /**
     * Holder class to contain all the views required during populating content into it.
     */
    public static class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
            final TextView mName;
            final ImageView mBackground;
        CategoriesViewHolder(View itemView) {
            super(itemView);
            mName = (TextView)itemView.findViewById(R.id.txtCardCategories);
            mBackground = (ImageView)itemView.findViewById(R.id.imgCardCategories);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        /* Interface for handling clicks - both normal and long ones. */
        public interface ClickListener {
            /**
             * Called when the view is clicked.
             *
             * @param v view that is clicked
             * @param position of the clicked item
             * @param isLongClick true if long click, false otherwise
             */
            void onClick(View v, int position, boolean isLongClick);
        }
        private ClickListener clickListener;
        /* Setter for listener. */
        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }
        @SuppressWarnings("deprecation")
        @Override
        public void onClick(View v) {
            // If not long clicked, pass last variable as false.
            clickListener.onClick(v, getPosition(), false);
        }
        @SuppressWarnings("deprecation")
        @Override
        public boolean onLongClick(View v) {
            // If long clicked, passed last variable as true.
            clickListener.onClick(v, getPosition(), true);
            return true;
        }
    }
    private final List<CategoriesCard> mCategories;
    private static Context mContext;
    /**
     * Constructor to the Adapter
     * @param mCategories List of Categories
     * @param context Context of the activity currently in view/active.
     */
    public CategoriesAdapter(List<CategoriesCard> mCategories,Context context){
        this.mCategories = mCategories;
        mContext=context;
    }

    /**
     * Inflates the layout
     * @param viewGroup Viewgroup object which is to be populated
     * @param i Index of the current object
     * @return CategoriesViewHolder Object
     */
    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categories_card, viewGroup, false);
        return new CategoriesViewHolder(v);
    }

    /**
     * Binds the content to the view
     * @param categoriesViewHolder Holder object which contains objects of the all the views which will be populated
     * @param i Index of the current viewgroup
     */
    @Override
    public void onBindViewHolder(CategoriesViewHolder categoriesViewHolder, int i) {
        categoriesViewHolder.mName.setText(mCategories.get(i).Name);
        categoriesViewHolder.mBackground.setImageResource(mCategories.get(i).Background);
        categoriesViewHolder.setClickListener(new CategoriesViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int pos, boolean isLongClick) {
                if (isLongClick) {
                    // View v at position pos is long-clicked.
                } else {
                    Intent i1 = new Intent(mContext, CategoriesView.class);
                    i1.putExtra("Category", mCategories.get(pos).Name);
                    mContext.startActivity(i1);
                    NavigationActivity.clearSearch();
                }
            }
        });
    }

    /**
     * Gets the total number of cards which is populated
     * @return Count of the Categories-List
     */
    @Override
    public int getItemCount() {
        return mCategories.size();
    }
}
