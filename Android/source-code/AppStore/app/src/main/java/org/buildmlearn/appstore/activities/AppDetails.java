package org.buildmlearn.appstore.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.AppReader;
import org.buildmlearn.appstore.utils.VolleySingleton;

/**
 * This class deals with the App-Details page, which is shown when the user taps on any app cards displayed on various pages.
 */
public class AppDetails extends AppCompatActivity {

    private Context mContext;
    private WebView webDisqus;
    private LinearLayout mLinearLayout;
    private boolean mActive=false; //false:App is not installed. true:Launch the app
    private static Apps mApp=new Apps();
    private static TextView mAppDescription;
    private static TextView mAppTxtMore;
    private static Button mBtnInstall;
    private static boolean TxtShowMore=false;
    private static ProgressBar mProgressReviews;
    public static String mScreenshots[];
    private final int DEFAULT_NUMBER_OF_CHARACTERS_IN_DESCRIPTION=40;

    /**
     * The method is executed first when the activity is created.
     * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);
        Intent intent=getIntent();
        mApp=intent.getParcelableExtra("App");
        mLinearLayout=(LinearLayout)findViewById(R.id.ll_details);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar_app_details);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDetails.this.onBackPressed();
            }
        });
        mContext=this;
        mToolbar.setTitle(mApp.Name);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
        mActive=SP.getBoolean(mApp.Name,false);
        if(mApp==null)finish();
        NetworkImageView mAppIcon = (NetworkImageView) findViewById(R.id.details_AppIcon);
        TextView mAppTitle = (TextView) findViewById(R.id.details_AppTitle);
        TextView mAppSubTitle = (TextView) findViewById(R.id.details_AppSubTitle);
        mAppDescription=(TextView)findViewById(R.id.details_AppDescription);
        TextView mAppAdditionalInfo = (TextView) findViewById(R.id.details_AdditionalInfo);
        TextView mAppAdditionalDetails = (TextView) findViewById(R.id.details_AdditionalDetails);
        mProgressReviews=(ProgressBar)findViewById(R.id.progressReviews);
        mAppTxtMore=(TextView)findViewById(R.id.details_TxtMore);
        ImageLoader imageLoader= VolleySingleton.getInstance(this).getImageLoader();
        mAppIcon.setImageUrl(mApp.AppIcon, imageLoader);

        //TODO This is a temporary fix, which displays only two screenshots. It will be fixed with the real-time API.
        NetworkImageView mAppScreenshot1 = (NetworkImageView) findViewById(R.id.details_Screenshot1);
        NetworkImageView mAppScreenshot2 = (NetworkImageView) findViewById(R.id.details_Screenshot2);
        mScreenshots=mApp.Screenshots;
        mAppScreenshot1.setImageUrl(mApp.Screenshots[0], imageLoader);
        mAppScreenshot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppDetails.this, FullScreenViewActivity.class);
                i.putExtra("position", 0);
                AppDetails.this.startActivity(i);
            }
        });
        mAppScreenshot2.setImageUrl(mApp.Screenshots[1], imageLoader);
        mAppScreenshot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppDetails.this, FullScreenViewActivity.class);
                i.putExtra("position", 1);
                AppDetails.this.startActivity(i);
            }
        });

        mAppTitle.setText(mApp.Name);
        mAppSubTitle.setText(mApp.Author);
        if (mApp.Description.length() > DEFAULT_NUMBER_OF_CHARACTERS_IN_DESCRIPTION) {
            TxtShowMore = false;
            mAppDescription.setText(mApp.Description.substring(0, DEFAULT_NUMBER_OF_CHARACTERS_IN_DESCRIPTION));
            mAppTxtMore.setText("MORE");
        }
        else mAppTxtMore.setVisibility(View.GONE);
        mAppAdditionalInfo.setText("Author: \nAuthor Email: \nCategory: \nType: ");
        mAppAdditionalDetails.setText(mApp.Author + "\n" + mApp.AuthorEmail + "\n" + mApp.Category + "\n" + mApp.Type);

        //DISQUS Implementation for Reviews section.
        webDisqus = (WebView) findViewById(R.id.disqus);
        WebSettings webSettings2 = webDisqus.getSettings();
        webSettings2.setBuiltInZoomControls(true);
        webDisqus.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mProgressReviews.setVisibility(View.GONE);
                //The url will only start with the following, if the user has either performed a Sign-In or Sign-Out operation in the Reviews section.
                // In such a case, the web-view will automatically be navigated back to original reviews.
                if (url.startsWith("http://disqus.com/") || url.startsWith("https://disqus.com/")) {
                    webDisqus.loadData(getHtmlComment(), "text/html", null);
                    webDisqus.reload();
                }
            }
        });
        webDisqus.setWebChromeClient(new WebChromeClient());
        webDisqus.loadData(getHtmlComment(), "text/html", null);
        //Button to reload reviews section.
        ImageButton webReload=(ImageButton)findViewById(R.id.web_reload);
        webReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webDisqus.loadData(AppDetails.this.getHtmlComment(), "text/html", null);
                webDisqus.reload();
            }
        });
        Button mBtnShare = (Button) findViewById(R.id.btnShare);
        mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                intent1.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent1.putExtra(Intent.EXTRA_SUBJECT, AppDetails.this.getResources().getString(R.string.email_subject));
                intent1.putExtra(Intent.EXTRA_TEXT, AppDetails.this.getResources().getString(R.string.email_body));
                intent1.putExtra(Intent.EXTRA_TITLE, AppDetails.this.getResources().getString(R.string.email_title));
                AppDetails.this.startActivity(Intent.createChooser(intent1, getResources().getString(R.string.share_title)));
            }
        });
        mBtnInstall=(Button)findViewById(R.id.btnInstall);
        if(mActive)mBtnInstall.setText(getResources().getString(R.string.launch));
        else mBtnInstall.setText(getResources().getString(R.string.install));
        mBtnInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mActive) {
                    //TODO This is a temporary fix for installing an app. In real case, this method will download the .buildmlearn file from the server.
                    SharedPreferences SP1 = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor editor1 = SP1.edit();
                    editor1.putBoolean(mApp.Name, true);
                    editor1.apply();
                    if (AppReader.listApps(mContext).size() == 1) {
                        Intent i = new Intent(mContext, HomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        Activity activity = (Activity) mContext;
                        activity.finish();
                    }
                    Snackbar.make(mLinearLayout, AppDetails.this.getResources().getString(R.string.install_success) + mApp.Name, Snackbar.LENGTH_LONG).show();
                    mActive = true;
                    mBtnInstall.setText(AppDetails.this.getResources().getString(R.string.launch));
                } else {
                    Intent i = new Intent(mContext, StartActivity.class);
                    i.putExtra("filename", mApp.Name);
                    mContext.startActivity(i);
                }
            }
        });
    }

    /**
     * This method build the HTML content which needs to be loaded to the reviews section.
     * @return A string with html content, to be loaded into the web view.
     */
    private String getHtmlComment() {
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

    /**
     * This method is executed when a button in the App-Description to show more text on the App-Details page is tapped. It toggles between showing more and less text in the App description section of App-Details page.
     * @param v The view contains the Button.
     */
    public void showMore(View v) {
        if(TxtShowMore)
        {
            TxtShowMore=false;
            mAppDescription.setText(mApp.Description.substring(0,DEFAULT_NUMBER_OF_CHARACTERS_IN_DESCRIPTION));
            mAppTxtMore.setText(getResources().getString(R.string.more));
        }
        else{
            TxtShowMore=true;
            mAppDescription.setText(mApp.Description);
            mAppTxtMore.setText(getResources().getString(R.string.less));
        }
    }

    /**
     * This method is called automatically when the back button is pressed. It ensures the search results cleared.
     */
    @Override
    public void onBackPressed()
    {
        NavigationActivity.clearSearch();
        super.onBackPressed();
    }
}
