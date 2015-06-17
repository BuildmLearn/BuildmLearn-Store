package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
    private static Button mBtnShare;
    private static boolean TxtShowMore=false;
    private static ProgressBar mProgressReviews;
    private WebView webDisqus;
    public static String mScreenshots[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_app_details,frameLayout);
        Intent i=getIntent();
        mApp=i.getParcelableExtra("App");
        if(mApp==null)finish();
        getSupportActionBar().setTitle(mApp.Name);
        mAppIcon=(NetworkImageView)findViewById(R.id.details_AppIcon);
        mAppTitle=(TextView)findViewById(R.id.details_AppTitle);
        mAppSubTitle=(TextView)findViewById(R.id.details_AppSubTitle);
        mAppDescription=(TextView)findViewById(R.id.details_AppDescription);
        mAppAdditionalInfo=(TextView)findViewById(R.id.details_AdditionalInfo);
        mAppAdditionalDetails=(TextView)findViewById(R.id.details_AdditionalDetails);
        mProgressReviews=(ProgressBar)findViewById(R.id.progressReviews);
        mAppTxtMore=(TextView)findViewById(R.id.details_TxtMore);
        mAppScreenshot1=(NetworkImageView)findViewById(R.id.details_Screenshot1);
        mAppScreenshot2=(NetworkImageView)findViewById(R.id.details_Screenshot2);
        mScreenshots=mApp.Screenshots;
        ImageLoader imageLoader= VolleySingleton.getInstance(this).getImageLoader();
        mAppIcon.setImageUrl(mApp.AppIcon, imageLoader);
        mAppScreenshot1.setImageUrl(mApp.Screenshots[0], imageLoader);
        mAppScreenshot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppDetails.this, FullScreenViewActivity.class);
                i.putExtra("position", 0);
                startActivity(i);
            }
        });
        mAppScreenshot2.setImageUrl(mApp.Screenshots[1], imageLoader);
        mAppScreenshot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppDetails.this, FullScreenViewActivity.class);
                i.putExtra("position", 1);
                startActivity(i);
            }
        });
        mAppTitle.setText(mApp.Name);
        mAppSubTitle.setText(mApp.Author);
        if (mApp.Description.length() > 40) {
            TxtShowMore = false;
            mAppDescription.setText(mApp.Description.substring(0, 40));
            mAppTxtMore.setText("MORE");
        }
        else mAppTxtMore.setVisibility(View.GONE);
        mAppAdditionalInfo.setText("Author: \nAuthor Email: \nCategory: \nType: ");
        mAppAdditionalDetails.setText(mApp.Author + "\n" + mApp.AuthorEmail + "\n" + mApp.Category + "\n" + mApp.Type);
        webDisqus = (WebView) findViewById(R.id.disqus);       // set up disqus
        WebSettings webSettings2 = webDisqus.getSettings();
        webSettings2.setJavaScriptEnabled(true);
        webSettings2.setBuiltInZoomControls(true);
        webDisqus.requestFocusFromTouch();
        webDisqus.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mProgressReviews.setVisibility(View.GONE);
                if (url.startsWith("http://disqus.com/") || url.startsWith("https://disqus.com/")) {
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
        mBtnShare=(Button)findViewById(R.id.btnShare);
        mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
// Add data to the intent, the receiving app will decide what to do with it.
                intent.putExtra(Intent.EXTRA_SUBJECT, "Try BuildmLearn AppStore !!!");
                intent.putExtra(Intent.EXTRA_TEXT, "BuildmLearn is a group of volunteers who collaborate to promote m-Learning with the specific aim of creating open source tools and enablers for teachers and students. The group is involved in developing easy to use m-Learning solutions, tool-kits and utilities for teachers (or parents) and students that help facilitate learning. The group comprises several like minded members of a wider community who collaborate to participate in a community building process.\n\nI want you to try this.\n\nhttp://www.buildmlearn.org\n\nThankYou.");
                intent.putExtra(Intent.EXTRA_TITLE,"BuildmLearn AppStore");
                startActivity(Intent.createChooser(intent, "How do you want to share?"));
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
}
