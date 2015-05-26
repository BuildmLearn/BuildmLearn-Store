package org.buildmlearn.appstore.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.CategoriesCard;

import java.util.List;

/**
 * Created by Srujan Jha on 5/26/2015.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{

        public static class CategoriesViewHolder extends RecyclerView.ViewHolder {
            CardView mCardView;
            TextView mName;
            ImageView mBackground;

            CategoriesViewHolder(View itemView) {
                super(itemView);
                mCardView = (CardView)itemView.findViewById(R.id.categoriesCardView);
                mName = (TextView)itemView.findViewById(R.id.txtCardCategories);
                mBackground = (ImageView)itemView.findViewById(R.id.imgCardCategories);
            }
        }
    List<CategoriesCard> mCategories;
    public CategoriesAdapter(List<CategoriesCard> mCategories){
        this.mCategories = mCategories;
    }
    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categories_card, viewGroup, false);
        return new CategoriesViewHolder(v);
    }
    @Override
    public void onBindViewHolder(CategoriesViewHolder personViewHolder, int i) {
        personViewHolder.mName.setText(mCategories.get(i).Name);
        personViewHolder.mBackground.setImageResource(mCategories.get(i).Background);
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
