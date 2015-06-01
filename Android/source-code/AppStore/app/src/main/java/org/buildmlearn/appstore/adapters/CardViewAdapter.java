package org.buildmlearn.appstore.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.AppDetails;
import org.buildmlearn.appstore.activities.HomeActivity;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.VolleySingleton;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Srujan Jha on 5/29/2015.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        CardView cvApps;
        TextView appTitle;
        TextView appSubTitle;
        NetworkImageView appLogo;
        ImageLoader imageLoader;
        ImageButton btnShowMore;

        CardViewHolder(View itemView) {
            super(itemView);
            imageLoader = VolleySingleton.getInstance(itemView.getContext()).getImageLoader();
            cvApps = (CardView) itemView.findViewById(R.id.appCard);
            appTitle = (TextView) itemView.findViewById(R.id.sCardTitle);
            appSubTitle = (TextView) itemView.findViewById(R.id.sCardSubTitle);
            appLogo = (NetworkImageView) itemView.findViewById(R.id.sCardLogo);
            btnShowMore = (ImageButton) itemView.findViewById(R.id.btn_More_Cards);
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

    List<Apps> apps;
    private static Context mContext;
    public CardViewAdapter(List<Apps> apps,Context context) {
        this.apps = apps;
        this.mContext=context;
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
    public void onBindViewHolder(final CardViewHolder cardViewHolder, int i) {
        if(apps.get(i).Name.length()<12)
            cardViewHolder.appTitle.setText(apps.get(i).Name);
        else
            cardViewHolder.appTitle.setText(apps.get(i).Name.substring(0,9)+"...");

        cardViewHolder.appSubTitle.setText(apps.get(i).Author);
        cardViewHolder.appLogo.setImageUrl(apps.get(i).AppIcon, cardViewHolder.imageLoader);
        //cardViewHolder.appLogo.setImageBitmap(apps.get(i).BAppIcon);
        cardViewHolder.setClickListener(new CardViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int pos, boolean isLongClick) {
                if (isLongClick) {
                    // View v at position pos is long-clicked.
                } else {
                    Intent i = new Intent(mContext, AppDetails.class);
                    i.putExtra("App", (Parcelable) apps.get(pos));
                    mContext.startActivity(i);
                }
            }
        });
        cardViewHolder.btnShowMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(mContext, cardViewHolder.btnShowMore);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_popup_apps, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}