package org.buildmlearn.appstore.adapters;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.activities.NavigationActivity;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;   // Declaring Variable to Understand which View is being worked on
    private static final int TYPE_ITEM1 = 1;     // if the view under inflation and population is header or Item
    private static final int TYPE_ITEM2 = 2;     // if the view under inflation and population is header or Item
    private static final int TYPE_DIVIDER = 3;     // if the view under inflation and population is header or Item

    private static final String TITLES1[] = {"Home","Categories","Settings"};
    private static final String TITLES2[] = {"Feedback","Rate My App","About"};
    private static final int ICONS[] = {R.drawable.ic_home_g,R.drawable.ic_categories_g,R.drawable.ic_settings_g};//,R.drawable.ic_feedback_g};
    private static final int ICONS_ACTIVE[] = {R.drawable.ic_home,R.drawable.ic_categories,R.drawable.ic_settings};//,R.drawable.ic_feedback};
    private static final int mProfile = R.drawable.aka;

    private String mName;        //String Resource for header View Name
    private String mEmail;       //String Resource for header view email
    private int mActive=1;

    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        View rowView;
        LinearLayout rowLayout;
        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView Name;
        TextView email;
        public ViewHolder(View itemView,int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);           // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created
            if(ViewType == TYPE_ITEM1) {
                textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            }
            else if(ViewType == TYPE_ITEM2) {
                textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
                imageView.setVisibility(View.GONE);
                Holderid = 2;                                               // setting holder id as 2 as the object being populated are of type item row
            }
            else if(ViewType==TYPE_DIVIDER)
            {
                textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
                textView.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                Holderid=3;
            }
            else{
                Name = (TextView) itemView.findViewById(R.id.header_name);         // Creating Text View object from header.xml for name
                email = (TextView) itemView.findViewById(R.id.header_email);       // Creating Text View object from header.xml for email
                profile = (ImageView) itemView.findViewById(R.id.header_circleView);// Creating Image view object from header.xml for profile pic
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }
            rowLayout=(LinearLayout)itemView.findViewById(R.id.rowLayout);
            rowView=itemView;
        }
    }

    public NavigationAdapter(String Name,String Email,int Active) {
        mName = Name;
        mEmail = Email;
        mActive=Active;
    }

    // In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
    // if the viewType is TYPE_HEADER and pass it to the view holder
    @Override
    public NavigationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false); //Inflating the layout
            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view
            return vhItem; // Returning the created object inflate your layout and pass it to view holder
        }else if (viewType == TYPE_ITEM2) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false); //Inflating the layout
            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view
            return vhItem; // Returning the created object inflate your layout and pass it to view holder
        }else if (viewType == TYPE_DIVIDER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false); //Inflating the layout
            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view
            return vhItem; // Returning the created object inflate your layout and pass it to view holder
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false); //Inflating the layout
            ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view
            return vhHeader; //returning the object created
        }
        return null;
    }
    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(NavigationAdapter.ViewHolder holder, int position) {
        if(holder.Holderid ==1 && position <= TITLES1.length) {// as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.textView.setText(TITLES1[position - 1]); // Setting the Text with the array of our Titles
            if(mActive==position)
            {
                holder.imageView.setImageResource(ICONS_ACTIVE[position - 1]);
                holder.textView.setTextColor(holder.rowView.getResources().getColor(R.color.activeText));
                holder.rowLayout.setBackgroundColor(holder.rowView.getResources().getColor(R.color.selectedItem));
            }
            else holder.imageView.setImageResource(ICONS[position-1]);
        }
        else if(holder.Holderid ==2 && position <= TITLES1.length+TITLES2.length+1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.textView.setText(TITLES2[position - TITLES1.length-2]); // Setting the Text with the array of our Titles
            if(mActive==position)
            {
                //holder.imageView.setImageResource(ICONS_ACTIVE[position - 1]);
                holder.textView.setTextColor(holder.rowView.getResources().getColor(R.color.activeText));
                holder.rowLayout.setBackgroundColor(holder.rowView.getResources().getColor(R.color.selectedItem));
            }
            //else holder.imageView.setImageResource(ICONS[position-1]);
        }
        else if(holder.Holderid ==3) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.rowView.setMinimumHeight(1);
            holder.rowView.setBackgroundColor(NavigationActivity.color_divider);
        }
        else if(holder.Holderid==0){
            holder.profile.setImageResource(mProfile);           // Similarly we set the resources for header view
            holder.Name.setText(mName);
            holder.email.setText(mEmail);
        }
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return TITLES1.length+TITLES2.length+2; // the number of items in the list will be +1 the titles including the header view.
    }

    // With the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return TYPE_HEADER;
        else if(position<=TITLES1.length)
        return TYPE_ITEM1;
        else if(position==TITLES1.length+1)
        return TYPE_DIVIDER;
        else return TYPE_ITEM2;
    }
}