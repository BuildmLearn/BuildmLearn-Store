package org.buildmlearn.appstore.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.CategoriesView;
import org.buildmlearn.appstore.models.CategoriesCard;

import java.util.List;

/**
 * Created by Srujan Jha on 5/26/2015.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{

        public static class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
            CardView mCardView;
            TextView mName;
            ImageView mBackground;

            CategoriesViewHolder(View itemView) {
                super(itemView);
                mCardView = (CardView)itemView.findViewById(R.id.categoriesCardView);
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
                public void onClick(View v, int position, boolean isLongClick);

            }
            private ClickListener clickListener;
            /* Setter for listener. */
            public void setClickListener(ClickListener clickListener) {
                this.clickListener = clickListener;
            }
            @Override
            public void onClick(View v) {

                // If not long clicked, pass last variable as false.
                clickListener.onClick(v, getPosition(), false);
            }

            @Override
            public boolean onLongClick(View v) {
                // If long clicked, passed last variable as true.
                clickListener.onClick(v, getPosition(), true);
                return true;
            }
        }
    List<CategoriesCard> mCategories;
    private static Context mContext;
    public CategoriesAdapter(List<CategoriesCard> mCategories,Context context){
        this.mCategories = mCategories;
        this.mContext=context;
    }
    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categories_card, viewGroup, false);
        return new CategoriesViewHolder(v);
    }
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
                    Intent i = new Intent(mContext, CategoriesView.class);
                    i.putExtra("Category", mCategories.get(pos).Name);
                    mContext.startActivity(i);
                }
            }
        });
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemCount() {
        return mCategories.size();
    }
}
