package org.buildmlearn.appstore.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.HomeActivity;
import org.buildmlearn.appstore.activities.StartActivity;
import org.buildmlearn.appstore.models.AppInfo;

import java.util.List;

/**
 * Created by Srujan Jha on 5/29/2015.
 */
public class MyAppsViewAdapter extends RecyclerView.Adapter<MyAppsViewAdapter.MyAppsViewHolder> {

    public static class MyAppsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView appTitle;
        ImageView appLogo;

        MyAppsViewHolder(View itemView) {
            super(itemView);
            appTitle = (TextView) itemView.findViewById(R.id.sMyCardTitle);
            appLogo = (ImageView) itemView.findViewById(R.id.sMyCardLogo);
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

    List<AppInfo> apps;
    private static Context mContext;
    public MyAppsViewAdapter(List<AppInfo> apps, Context context) {
        this.apps = apps;
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    @Override
    public MyAppsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myapp_card_small, viewGroup, false);
        MyAppsViewHolder pvh = new MyAppsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MyAppsViewHolder cardViewHolder, int i) {
        if(apps.get(i).Name.length()<12)
            cardViewHolder.appTitle.setText(apps.get(i).Name);
        else
            cardViewHolder.appTitle.setText(apps.get(i).Name.substring(0,9)+"...");
        cardViewHolder.appLogo.setImageBitmap(apps.get(i).AppIcon);

        cardViewHolder.setClickListener(new MyAppsViewHolder.ClickListener() {
            @Override
            public void onClick(View v, final int pos, boolean isLongClick) {
                if (isLongClick) {
                        //Creating the instance of PopupMenu
                        PopupMenu popup = new PopupMenu(mContext, v);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.menu_popup_myapps, popup.getMenu());
                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
                                SharedPreferences.Editor editor1 = SP.edit();
                                editor1.putBoolean(apps.get(pos).Name, false);
                                editor1.commit();
                                Intent i = new Intent(mContext, HomeActivity.class);
                                mContext.startActivity(i);
                                Activity activity = (Activity) mContext;
                                activity.finish();
                                return true;
                            }
                        });
                        popup.show();//showing popup menu
                        // View v at position pos is long-clicked.
                } else {
                        Intent i = new Intent(mContext, StartActivity.class);
                        i.putExtra("option", apps.get(pos).Type);
                        i.putExtra("filename", "Apps/" + apps.get(pos).Name + ".buildmlearn");
                        mContext.startActivity(i);
                   //Toast.makeText(mContext,"Only Info-apps(Festivals) are functional now.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}