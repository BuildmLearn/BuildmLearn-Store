package org.buildmlearn.appstore.adapters;

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
import android.widget.ImageView;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.AppDetails;
import org.buildmlearn.appstore.activities.HomeActivity;
import org.buildmlearn.appstore.activities.NavigationActivity;
import org.buildmlearn.appstore.activities.SplashActivity;
import org.buildmlearn.appstore.activities.StartActivity;
import org.buildmlearn.appstore.fragments.TabMyApps;
import org.buildmlearn.appstore.models.AppInfo;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.AppReader;

import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * This adapter class populates cards related to apps in the My-Apps section of the Home Page.
 */
public class MyAppsViewAdapter extends RecyclerView.Adapter<MyAppsViewAdapter.MyAppsViewHolder> {

    /**
     * Holder class to contain all the views required during populating content into it.
     */
    public static class MyAppsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        final TextView appTitle;
        final TextView appAuthor;
        final ImageView appLogo;
        final ImageButton btnShowMore;
        MyAppsViewHolder(View itemView) {
            super(itemView);
            appTitle = (TextView) itemView.findViewById(R.id.sCardTitle);
            appLogo = (ImageView) itemView.findViewById(R.id.sCardLogo);
            appAuthor=(TextView) itemView.findViewById(R.id.sCardSubTitle);
            btnShowMore = (ImageButton) itemView.findViewById(R.id.btn_More_Cards);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        /* Interface for handling clicks - both normal and long ones. */
        public interface ClickListener {

            /**
             * Called when the view is clicked.
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

    private final List<AppInfo> apps;
    private static Context mContext;

    /**
     * Constructor of the Adapter class
     * @param apps List of apps which is to be populated
     * @param context Context of the current Activity
     */
    public MyAppsViewAdapter(List<AppInfo> apps, Context context) {
        this.apps = apps;
        mContext=context;
    }

    /**
     * Gets the total number of app-cards which is populated
     * @return Count of total number of apps installed
     */
    @Override
    public int getItemCount() {
        return apps.size();
    }

    /**
     * Inflates the layout
     * @param viewGroup ViewGroup object which is to be populated
     * @param i Index of the current item
     * @return MyAppsViewHolder object
     */
    @Override
    public MyAppsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myapp_card_small, viewGroup, false);
        return new MyAppsViewHolder(v);
    }

    private MaterialDialog mAlertDialog;

    /**
     * Binds the content to the view
     * @param cardViewHolder CardViewHolder Object which contains all the object of the views to be populated
     * @param i Index of the item
     */
    @Override
    public void onBindViewHolder(final MyAppsViewHolder cardViewHolder, int i) {
        if(apps.get(i).Name.length()<11)
            cardViewHolder.appTitle.setText(apps.get(i).Name);
        else
            cardViewHolder.appTitle.setText(apps.get(i).Name.substring(0,9)+"...");
        cardViewHolder.appLogo.setImageBitmap(apps.get(i).AppIcon);
        cardViewHolder.appAuthor.setText(apps.get(i).Author);

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
                            mAlertDialog = new MaterialDialog(mContext)
                                    .setTitle("Uninstall")
                                    .setMessage("Do you want to uninstall " + apps.get(pos).Name + " ?")
                                    .setPositiveButton("YES", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mAlertDialog.dismiss();
                                            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
                                            SharedPreferences.Editor editor1 = SP.edit();
                                            editor1.putBoolean(apps.get(pos).Name, false);
                                            editor1.apply();
                                            if (AppReader.listApps(mContext).size() == 0) {
                                                Intent i1 = new Intent(mContext, HomeActivity.class);
                                                i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                mContext.startActivity(i1);
                                                NavigationActivity.clearSearch();
                                            } else TabMyApps.refreshList();
                                            Snackbar.make(v, mContext.getResources().getString(R.string.uninstall_success), Snackbar.LENGTH_LONG);
                                        }
                                    })
                                    .setNegativeButton("NO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mAlertDialog.dismiss();
                                        }
                                    });
                            mAlertDialog.show();
                            return true;
                        }
                    });
                    popup.show();//showing popup menu
                    // View v at position pos is long-clicked.
                } else {
                    String appName = apps.get(pos).Name;
                    for (Apps app : SplashActivity.appList) {
                        if (app.Name.equals(appName)) {
                            Intent i1 = new Intent(mContext, AppDetails.class);
                            i1.putExtra("App", app);
                            mContext.startActivity(i1);
                            NavigationActivity.clearSearch();
                            return;
                        }
                    }
                    Intent i1 = new Intent(mContext, StartActivity.class);
                    i1.putExtra("filename", apps.get(pos).Name);
                    mContext.startActivity(i1);
                    NavigationActivity.clearSearch();
                }
            }
        });
        cardViewHolder.btnShowMore.setTag(apps.get(i).Name);
        cardViewHolder.btnShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(mContext, cardViewHolder.btnShowMore);
                popup.getMenuInflater().inflate(R.menu.menu_popup_apps_launch, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_launch) {
                            Intent intent = new Intent(mContext, StartActivity.class);
                            String appName = v.getTag().toString();
                            intent.putExtra("filename", appName);
                            mContext.startActivity(intent);
                            NavigationActivity.clearSearch();
                        } else if (item.getItemId() == R.id.menu_share_2) {
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
        });
    }

}