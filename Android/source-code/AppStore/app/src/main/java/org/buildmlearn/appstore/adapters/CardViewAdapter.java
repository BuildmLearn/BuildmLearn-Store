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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.AppDetails;
import org.buildmlearn.appstore.activities.HomeActivity;
import org.buildmlearn.appstore.activities.NavigationActivity;
import org.buildmlearn.appstore.models.AppInfo;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.AppReader;
import org.buildmlearn.appstore.utils.VolleySingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Srujan Jha on 5/29/2015.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        //CardView cvApps;
        TextView appTitle;
        TextView appSubTitle;
        NetworkImageView appLogo;
        ImageLoader imageLoader;
        ImageButton btnShowMore;

        CardViewHolder(View itemView) {
            super(itemView);
            imageLoader = VolleySingleton.getInstance(itemView.getContext()).getImageLoader();
            //cvApps = (CardView) itemView.findViewById(R.id.appCard);
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

    List<Apps> apps=new ArrayList<Apps>();
    ArrayList<Integer> rndList = new ArrayList<>();
    private static Context mContext;
    public CardViewAdapter(List<Apps> apps,Context context,int x) {
        this.apps.clear();
        this.apps = apps;
        Random rnd = new Random();
        while (rndList.size() != x) {
            int c = rnd.nextInt(apps.size());
            if (!rndList.contains(c)) rndList.add(c);
        }
        this.mContext=context;
    }
    public CardViewAdapter(List<Apps> apps,Context context) {
        this.apps.clear();
        this.apps = apps;
        for(int i=0;i<apps.size();i++)rndList.add(i);
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return rndList.size();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.app_card_small, viewGroup, false);
        CardViewHolder pvh = new CardViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, int i) {
        if(apps.get(rndList.get(i)).Name.length()<12)
            cardViewHolder.appTitle.setText(apps.get(rndList.get(i)).Name);
        else
            cardViewHolder.appTitle.setText(apps.get(rndList.get(i)).Name.substring(0,9)+"...");
        cardViewHolder.appSubTitle.setText(apps.get(rndList.get(i)).Author);
        cardViewHolder.appLogo.setImageUrl(apps.get(rndList.get(i)).AppIcon, cardViewHolder.imageLoader);
        //cardViewHolder.appLogo.setImageBitmap(apps.get(i).BAppIcon);
        cardViewHolder.setClickListener(new CardViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int pos, boolean isLongClick) {
                if (isLongClick) {
                    // View v at position pos is long-clicked.
                } else {
                    Intent i = new Intent(mContext, AppDetails.class);
                    i.putExtra("App", apps.get(rndList.get(pos)));
                    String appName=apps.get(pos).Name;
                    for(AppInfo app: AppReader.AppList) {
                        if (app.Name.equals(appName)) {
                            i.putExtra("mActive", true);
                            i.putExtra("option", app.Type);
                            i.putExtra("filename", "Apps/" + apps.get(pos).Name + ".buildmlearn");
                            mContext.startActivity(i);
                            return;
                        }
                    }
                    i.putExtra("mActive",false);
                    mContext.startActivity(i);
                }
            }
        });
        cardViewHolder.btnShowMore.setTag(apps.get(rndList.get(i)).Name);
        cardViewHolder.btnShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(mContext, cardViewHolder.btnShowMore);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_popup_apps, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_install) {
                            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
                            if(SP.getBoolean(v.getTag().toString(),false)) {
                                Toast.makeText(mContext, "The app is already installed", Toast.LENGTH_LONG).show();
                                return false;
                            }
                            SharedPreferences.Editor editor1 = SP.edit();
                            editor1.putBoolean(v.getTag().toString(),true);
                            editor1.commit();
                            /*Intent i = new Intent(mContext, HomeActivity.class);
                            mContext.startActivity(i);*/
                            HomeActivity.MyAppsView();
                            Toast.makeText(mContext, "Thank you for installing "+v.getTag().toString(), Toast.LENGTH_LONG).show();
                            if(NavigationActivity.mActive>1){Activity activity = (Activity) mContext;
                            activity.finish();}
                        }
                        if (item.getItemId() == R.id.menu_share_2) {
                            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
// Add data to the intent, the receiving app will decide what to do with it.
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Try BuildmLearn AppStore !!!");
                            intent.putExtra(Intent.EXTRA_TEXT, "BuildmLearn is a group of volunteers who collaborate to promote m-Learning with the specific aim of creating open source tools and enablers for teachers and students. The group is involved in developing easy to use m-Learning solutions, tool-kits and utilities for teachers (or parents) and students that help facilitate learning. The group comprises several like minded members of a wider community who collaborate to participate in a community building process.\n\nI want you to try this.\n\nhttp://www.buildmlearn.org\n\nThankYou.");
                            intent.putExtra(Intent.EXTRA_TITLE, "BuildmLearn AppStore");
                            mContext.startActivity(Intent.createChooser(intent, "How do you want to share?"));
                        }
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