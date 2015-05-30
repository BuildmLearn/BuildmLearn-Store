package org.buildmlearn.appstore.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.VolleySingleton;

import java.util.List;

/**
 * Created by Srujan Jha on 5/29/2015.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cvApps;
        TextView appTitle;
        TextView appSubTitle;
        NetworkImageView appLogo;
        ImageLoader imageLoader;

        CardViewHolder(View itemView) {
            super(itemView);
            imageLoader = VolleySingleton.getInstance(itemView.getContext()).getImageLoader();
            System.out.println(imageLoader);
            cvApps = (CardView) itemView.findViewById(R.id.appCard);
            appTitle = (TextView) itemView.findViewById(R.id.sCardTitle);
            appSubTitle = (TextView) itemView.findViewById(R.id.sCardSubTitle);
            appLogo = (NetworkImageView) itemView.findViewById(R.id.sCardLogo);
        }

    }

    List<Apps> apps;
    public CardViewAdapter(List<Apps> apps) {
        this.apps = apps;
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.app_card_small, viewGroup, false);
        CardViewHolder pvh = new CardViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        cardViewHolder.appTitle.setText(apps.get(i).Name);
        cardViewHolder.appSubTitle.setText(apps.get(i).Author);
        cardViewHolder.appLogo.setImageUrl(apps.get(i).AppIcon, cardViewHolder.imageLoader);
        //cardViewHolder.appLogo.setImageBitmap(apps.get(i).BAppIcon);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}