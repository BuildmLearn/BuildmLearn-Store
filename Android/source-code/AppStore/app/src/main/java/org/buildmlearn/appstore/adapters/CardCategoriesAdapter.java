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
import org.buildmlearn.appstore.activities.CategoriesActivity;
import org.buildmlearn.appstore.activities.CategoriesView;
import org.buildmlearn.appstore.activities.NavigationActivity;
import org.buildmlearn.appstore.models.CategoriesCard;

import java.util.ArrayList;
import java.util.Random;

/**
 * This adapter class populates cards related to categories in the Featured Categories section of the Home page.
 */
public class CardCategoriesAdapter extends RecyclerView.Adapter<CardCategoriesAdapter.CardViewHolder> {

    /**
     * Holder class to contain all the views required during populating content into it.
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        //CardView cvApps;
        final TextView appTitle;
        final ImageView appImage;

        CardViewHolder(View itemView) {
            super(itemView);
            appTitle = (TextView) itemView.findViewById(R.id.appTxtCard);
            appImage = (ImageView) itemView.findViewById(R.id.appImgCard);
            itemView.setOnClickListener(this);
        }

        /* Interface for handling clicks - both normal and long ones. */
        public interface ClickListener {
            /**
             * Called when the view is clicked.
             *
             * @param v           view that is clicked
             * @param position    of the clicked item
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


    private final ArrayList<Integer> rndList = new ArrayList<>();
    private static Context mContext;

    /**
     * Constructor to the Adapter
     * @param context: Context of the activity currently in view/active.
     * @param x:Number of categories to be populated
     */
    public CardCategoriesAdapter(Context context, int x) {
        mContext = context;
        Random rnd = new Random();
        while (rndList.size() != x) {
            int c = rnd.nextInt(10);
            if (!rndList.contains(c)) rndList.add(c);
        }
    }

    /**
     * Gets the total number of cards which is populated
     * @return Size of the Categories-List which is randomly generated
     */
    @Override
    public int getItemCount() {
        return rndList.size();
    }

    /**
     * Inflates the layout
     * @param viewGroup:View group object, which is inflated with the current layout.
     * @param i: Index of the object
     * @return CardViewHolder object of the current view.
     */
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categories_card_store, viewGroup, false);
        return new CardViewHolder(v);
    }

    /**
     * It binds content to the views.
     * @param cardViewHolder:Holder object which contains all the views which needs to be populated
     * @param i:Index of the card, which is being populated.
     */
    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, int i) {

        cardViewHolder.appTitle.setText(CategoriesCard.CategoryName[rndList.get(i)]);
        cardViewHolder.appImage.setImageResource(CategoriesCard.CategoryImage[rndList.get(i)]);
        cardViewHolder.setClickListener(new CardViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int pos, boolean isLongClick) {
                if (isLongClick) {
                    // View v at position pos is long-clicked.
                } else {
                    Intent i1 = new Intent(mContext, CategoriesView.class);
                    i1.putExtra("Category", CategoriesCard.CategoryName[rndList.get(pos)]);
                    i1.putExtra("Home", true);
                    mContext.startActivity(i1);
                    CategoriesActivity.closeSearch();
                    NavigationActivity.clearSearch();
                }
            }
        });
    }

}