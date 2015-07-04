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

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Srujan Jha on 6/19/2015.
 */
public class CardCategoriesAdapter extends RecyclerView.Adapter<CardCategoriesAdapter.CardViewHolder> {

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        //CardView cvApps;
        TextView appTitle;
        ImageView appImage;

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


    ArrayList<Integer> rndList = new ArrayList<>();
    private static Context mContext;

    public CardCategoriesAdapter(Context context, int x) {
        this.mContext = context;
        Random rnd = new Random();
        while (rndList.size() != x) {
            int c = rnd.nextInt(10);
            if (!rndList.contains(c)) rndList.add(c);
        }
    }

    @Override
    public int getItemCount() {
        return rndList.size();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categories_card_store, viewGroup, false);
        CardViewHolder pvh = new CardViewHolder(v);
        return pvh;
    }

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
                    Intent i = new Intent(mContext, CategoriesView.class);
                    i.putExtra("Category", CategoriesCard.CategoryName[rndList.get(pos)]);
                    mContext.startActivity(i);
                    NavigationActivity.clearSearch();
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}