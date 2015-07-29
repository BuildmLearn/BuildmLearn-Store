package org.buildmlearn.appstore.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.AppDetails;
import org.buildmlearn.appstore.activities.HomeActivity;
import org.buildmlearn.appstore.activities.NavigationActivity;
import org.buildmlearn.appstore.activities.StartActivity;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.AppReader;
import org.buildmlearn.appstore.utils.VolleySingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An Adapter class, which populates cards related to apps in various activities like HomeActivity, AppsActivity etc.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {

    /**
     * Holder class to contain all the views required during populating content into it.
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        //CardView cvApps;
        final TextView appTitle;
        final TextView appSubTitle;
        final NetworkImageView appLogo;
        final ImageLoader imageLoader;
        final ImageButton btnShowMore;

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

    private List<Apps> apps= new ArrayList<>();
    private final ArrayList<Integer> rndList = new ArrayList<>();
    private static Context mContext;
    /**
     * Constructor to the Adapter class
     * @param apps: List of apps which needs to be populated.
     * @param context: Context of the activity currently in view/active.
     * @param x:Number of categories to be populated
     */
    public CardViewAdapter(List<Apps> apps,Context context,int x) {
        this.apps.clear();
        this.apps = apps;
        Random rnd = new Random();
        while (rndList.size() != x) {
            int c = rnd.nextInt(apps.size());
            if (!rndList.contains(c)) rndList.add(c);
        }
        mContext=context;
    }

    /**
     * Constructor of the Adapter class
     * @param apps List of apps which needs to be populated
     * @param context Context of the current Activity
     */
    public CardViewAdapter(List<Apps> apps,Context context) {
        this.apps.clear();
        this.apps = apps;
        for(int i=0;i<apps.size();i++)rndList.add(i);
        mContext=context;
    }
    /**
     * Gets the total number of app-cards which is populated
     * @return Size of the App-List which is randomly generated
     */
    @Override
    public int getItemCount() {
        return rndList.size();
    }

    /**
     * Inflates the layout
     * @param viewGroup: contains the viewgroup object which is being populated
     * @param i Current index
     * @return CardViewHolder Object of the current view
     */
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.app_card_small, viewGroup, false);
        return new CardViewHolder(v);
    }

    /**
     * Binds content to the cardviewholder object
     * @param cardViewHolder Contains the cardviewholder object of the view.
     * @param i Index of the populated view.
     */
    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, final int i) {
        if(apps.get(rndList.get(i)).Name.length()<11)
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
                    Intent i1 = new Intent(mContext, AppDetails.class);
                    i1.putExtra("App", apps.get(rndList.get(pos)));
                /*String appName=apps.get(pos).Name;
                for(AppInfo app: AppReader.AppList) {
                    if (app.Name.equals(appName)) {
                        i.putExtra("mActive", true);
                        i.putExtra("option", app.Type);
                        i.putExtra("filename", "Apps/" + apps.get(pos).Name + ".buildmlearn");
                        mContext.startActivity(i);
                        return;
                    }
                }*/
                    i1.putExtra("mActive", false);
                    mContext.startActivity(i1);
                }
            }
        });
        cardViewHolder.btnShowMore.setTag(apps.get(rndList.get(i)).Name);
        cardViewHolder.btnShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
                boolean mActive = SP.getBoolean(apps.get(rndList.get(i)).Name, false);
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(mContext, cardViewHolder.btnShowMore);
                //Inflating the Popup using xml file
                if (mActive)
                    popup.getMenuInflater().inflate(R.menu.menu_popup_apps_launch, popup.getMenu());
                else popup.getMenuInflater().inflate(R.menu.menu_popup_apps, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_install) {
                            SharedPreferences SP1 = PreferenceManager.getDefaultSharedPreferences(mContext);
                            SharedPreferences.Editor editor1 = SP1.edit();
                            editor1.putBoolean(v.getTag().toString(), true);
                            editor1.apply();
                            NavigationActivity.clearSearch();
                            Snackbar.make(NavigationActivity.frameLayout, "Thank you for installing " + v.getTag().toString(), Snackbar.LENGTH_LONG).show();
                            if (AppReader.listApps(mContext).size() == 1) {
                                Intent i1 = new Intent(mContext, HomeActivity.class);
                                i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                i1.putExtra("View", 1);
                                mContext.startActivity(i1);
                                Activity activity = (Activity) mContext;
                                activity.finish();
                            }
                        } else if (item.getItemId() == R.id.menu_launch) {
                            Intent intent = new Intent(mContext, StartActivity.class);
                            String appName = apps.get(rndList.get(i)).Name;
                            intent.putExtra("filename", appName);
                            mContext.startActivity(intent);
                            NavigationActivity.clearSearch();
                        }
                        if (item.getItemId() == R.id.menu_share_2) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// Add data to the intent, the receiving app will decide what to do with it.
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Try BuildmLearn AppStore !!!");
                            intent.putExtra(Intent.EXTRA_TEXT, "BuildmLearn is a group of volunteers who collaborate to promote m-Learning with the specific aim of creating open source tools and enablers for teachers and students. The group is involved in developing easy to use m-Learning solutions, tool-kits and utilities for teachers (or parents) and students that help facilitate learning. The group comprises several like minded members of a wider community who collaborate to participate in a community building process.\n\nI want you to try this.\n\nhttp://www.buildmlearn.org\n\nThankYou.");
                            intent.putExtra(Intent.EXTRA_TITLE, "BuildmLearn AppStore");
                            mContext.startActivity(Intent.createChooser(intent, "How do you want to share?"));
                            NavigationActivity.clearSearch();
                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method
    }
}