package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.VolleySingleton;

public class AppDetails extends NavigationActivity {
    private static Apps mApp=new Apps();
    private static NetworkImageView mAppIcon;
    private static TextView mAppTitle;
    private static TextView mAppSubTitle;
    private static TextView mAppDescription;
    private static TextView mAppAdditionalInfo;
    private static TextView mAppAdditionalDetails;
    private static TextView mAppTxtMore;
    private static NetworkImageView mAppScreenshot1;
    private static NetworkImageView mAppScreenshot2;
    private static boolean TxtShowMore=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_app_details,frameLayout);
        Intent i=getIntent();
        mApp=i.getParcelableExtra("App");
        mAppIcon=(NetworkImageView)findViewById(R.id.details_AppIcon);
        mAppTitle=(TextView)findViewById(R.id.details_AppTitle);
        mAppSubTitle=(TextView)findViewById(R.id.details_AppSubTitle);
        mAppDescription=(TextView)findViewById(R.id.details_AppDescription);
        mAppAdditionalInfo=(TextView)findViewById(R.id.details_AdditionalInfo);
        mAppAdditionalDetails=(TextView)findViewById(R.id.details_AdditionalDetails);
        mAppTxtMore=(TextView)findViewById(R.id.details_TxtMore);
        mAppScreenshot1=(NetworkImageView)findViewById(R.id.details_Screenshot1);
        mAppScreenshot2=(NetworkImageView)findViewById(R.id.details_Screenshot2);
        ImageLoader imageLoader= VolleySingleton.getInstance(this).getImageLoader();
        mAppIcon.setImageUrl(mApp.AppIcon, imageLoader);
        mAppScreenshot1.setImageUrl(mApp.Screenshots[0], imageLoader);
        mAppScreenshot2.setImageUrl(mApp.Screenshots[1], imageLoader);
        mAppTitle.setText(mApp.Name);
        mAppSubTitle.setText(mApp.Author);
        if(mApp.Description.length()>40)
        {
            TxtShowMore=false;
            mAppDescription.setText(mApp.Description.substring(0,40));
            mAppTxtMore.setText("MORE");
        }
        else mAppTxtMore.setVisibility(View.INVISIBLE);
        mAppAdditionalInfo.setText("Author: \nAuthor Email: \n Category: \nType: ");
        mAppAdditionalDetails.setText(mApp.Author+"\n"+mApp.AuthorEmail+"\n"+mApp.Category+"\n"+mApp.Type);
    }
    public void showMore(View v)
    {
        if(TxtShowMore)
        {
            TxtShowMore=false;
            mAppDescription.setText(mApp.Description.substring(0,40));
            mAppTxtMore.setText("MORE");
        }
        else{
            TxtShowMore=true;
            mAppDescription.setText(mApp.Description);
            mAppTxtMore.setText("LESS");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
