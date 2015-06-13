package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
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
    private static ImageButton btnShowMore;
    private WebView webDisqus;
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
        mAppAdditionalInfo.setText("Author: \nAuthor Email: \nCategory: \nType: ");
        mAppAdditionalDetails.setText(mApp.Author+"\n"+mApp.AuthorEmail+"\n"+mApp.Category+"\n"+mApp.Type);
        btnShowMore = (ImageButton) findViewById(R.id.btn_More_Details);
        btnShowMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(AppDetails.this, btnShowMore);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_popup_details, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        webDisqus = (WebView) findViewById(R.id.disqus);       // set up disqus
        WebSettings webSettings2 = webDisqus.getSettings();
        webSettings2.setJavaScriptEnabled(true);
        webSettings2.setBuiltInZoomControls(true);
        webDisqus.requestFocusFromTouch();
        webDisqus.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                System.out.println("WebListener:"+url);
                if(url.startsWith("http://disqus.com/"))
                {
                    webDisqus.loadData(getHtmlComment(), "text/html", null);
                    webDisqus.reload();
                }
            }
        });
        webDisqus.setWebChromeClient(new WebChromeClient());
        webDisqus.loadData(getHtmlComment(), "text/html", null);
        ImageButton webReload=(ImageButton)findViewById(R.id.web_reload);
        webReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webDisqus.loadData(getHtmlComment(), "text/html", null);
                webDisqus.reload();
            }
        });
        webDisqus.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                System.out.println("WebListener\n"+url+" \n"+userAgent+" \n"+contentDisposition+" \n"+mimetype+" \n"+contentLength);
            }
        });
    }

    public String getHtmlComment() {

        return "<div id='disqus_thread'></div>"
                + "<script type='text/javascript'>"
                + "var disqus_identifier = '"+ mApp.Name+"_"+mApp.Author + "';"
                + "var disqus_title = '"+mApp.Name+"';"
                + "var disqus_url='http://www.buildmlearn.org/appstore/"+ mApp.Name+"_"+mApp.Author + "';"
                + "var disqus_shortname = 'buildmlearn';"
                + " (function() { var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;"
                + "dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';"
                + "(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq); })();"
                + "</script>";
    }

    public void showMore(View v) {
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
