package org.buildmlearn.appstore.adapters;

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
 * Created by Srujan Jha on 5/29/2015.
 */
public class MyAppsViewAdapter extends RecyclerView.Adapter<MyAppsViewAdapter.MyAppsViewHolder> {

    public static class MyAppsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView appTitle;
        TextView appAuthor;
        ImageView appLogo;
        ImageButton btnShowMore;
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
private MaterialDialog mAlertDialog;
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
                                mAlertDialog=new MaterialDialog(mContext)
                                        .setTitle("Uninstall")
                                        .setMessage("Do you want to uninstall "+apps.get(pos).Name+" ?")
                                        .setPositiveButton("YES", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mAlertDialog.dismiss();
                                                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
                                                SharedPreferences.Editor editor1 = SP.edit();
                                                editor1.putBoolean(apps.get(pos).Name, false);
                                                editor1.commit();
                                                if(AppReader.listApps(mContext).size()==0) {
                                                    Intent i = new Intent(mContext, HomeActivity.class);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    mContext.startActivity(i);
                                                    NavigationActivity.clearSearch();
                                                }else TabMyApps.refreshList();
                                                NavigationActivity.showSnackBar("The app is unistalled.");
                                                }
                                        })
                                        .setNegativeButton("NO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {mAlertDialog.dismiss();}
                                        });
                                mAlertDialog.show(); return true;
                            }
                        });
                        popup.show();//showing popup menu
                        // View v at position pos is long-clicked.
                } else {
                    String appName=apps.get(pos).Name;
                    for(Apps app:SplashActivity.appList) {
                        if (app.Name.equals(appName)) {
                            Intent i = new Intent(mContext, AppDetails.class);
                            i.putExtra("App", app);
                            mContext.startActivity(i);
                            NavigationActivity.clearSearch();
                            return;
                        }
                    }
                    Intent i = new Intent(mContext, StartActivity.class);
                    i.putExtra("filename", apps.get(pos).Name);
                    mContext.startActivity(i);
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
                            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}