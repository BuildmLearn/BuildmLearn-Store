package org.buildmlearn.appstore.fragments;

/**
 * Created by Srujan Jha on 25-05-2015.
 */
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.AppsActivity;
import org.buildmlearn.appstore.activities.CategoriesActivity;
import org.buildmlearn.appstore.activities.SplashActivity;
import org.buildmlearn.appstore.adapters.CardViewAdapter;
import org.buildmlearn.appstore.models.CategoriesCard;

import java.util.ArrayList;
import java.util.Random;

public class TabStore extends Fragment {

    private static View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_store, container, false);
        mView = v;
        ImageView imgCard1 = (ImageView) v.findViewById(R.id.appImgCard1);
        ImageView imgCard2 = (ImageView) v.findViewById(R.id.appImgCard2);
        ImageView imgCard3 = (ImageView) v.findViewById(R.id.appImgCard3);
        ImageView imgCard4 = (ImageView) v.findViewById(R.id.appImgCard4);
        TextView txtCard1 = (TextView) v.findViewById(R.id.appTxtCard1);
        TextView txtCard2 = (TextView) v.findViewById(R.id.appTxtCard2);
        TextView txtCard3 = (TextView) v.findViewById(R.id.appTxtCard3);
        TextView txtCard4 = (TextView) v.findViewById(R.id.appTxtCard4);
        Random rnd = new Random();
        ArrayList<Integer> rndList = new ArrayList<>();
        while (rndList.size() != 4) {
            int c = rnd.nextInt(10);
            if (!rndList.contains(c)) rndList.add(c);
        }
        imgCard1.setImageResource(CategoriesCard.CategoryImage[rndList.get(0)]);
        imgCard1.setTag(rndList.get(0));
        txtCard1.setText(CategoriesCard.CategoryName[rndList.get(0)]);
        imgCard2.setImageResource(CategoriesCard.CategoryImage[rndList.get(1)]);
        imgCard2.setTag(rndList.get(1));
        txtCard2.setText(CategoriesCard.CategoryName[rndList.get(1)]);
        imgCard3.setImageResource(CategoriesCard.CategoryImage[rndList.get(2)]);
        imgCard3.setTag(rndList.get(2));
        txtCard3.setText(CategoriesCard.CategoryName[rndList.get(2)]);
        imgCard4.setImageResource(CategoriesCard.CategoryImage[rndList.get(3)]);
        imgCard4.setTag(rndList.get(3));
        txtCard4.setText(CategoriesCard.CategoryName[rndList.get(3)]);

        TextView txtMoreApps = (TextView) v.findViewById(R.id.txtMoreApps);
        TextView txtMoreCategories = (TextView) v.findViewById(R.id.txtMoreCategories);
        txtMoreApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mView.getContext(), AppsActivity.class);
                startActivity(i);
            }
        });
        txtMoreCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mView.getContext(), CategoriesActivity.class);
                startActivity(i);
            }
        });

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.rvAppCard);
        rv.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(v.getContext(), 3);
        rv.setLayoutManager(llm);
        CardViewAdapter adapter = new CardViewAdapter(SplashActivity.appList, v.getContext());
        rv.setAdapter(adapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing);
        rv.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        return v;
    }
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if(parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }
}