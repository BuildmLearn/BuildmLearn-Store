package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import me.drakeet.materialdialog.MaterialDialog;

public class SplashActivity extends Activity {
    private final String XML="<applications>\n" +
            "<app>\n" +
            "\t<title>Festivals</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/r34jqda9j/logo_h.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Social Studies</category>\n" +
            "\t<type>Info</type>\n" +
            "\t<author_name>Author</author_name>\n" +
            "\t<author_email>Author@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Biology</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/63ofyv8l3/logo_c.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Biology</category>\n" +
            "\t<type>Quiz</type>\n" +
            "\t<author_name>Author</author_name>\n" +
            "\t<author_email>Author@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Spell</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/9i6mf03zr/logo_b.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Science</category>\n" +
            "\t<type>Spellings</type>\n" +
            "\t<author_name>Author</author_name>\n" +
            "\t<author_email>Author@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Animal</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/lu9ishruf/logo_l.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Biology</category>\n" +
            "\t<type>Flashcards</type>\n" +
            "\t<author_name>Author</author_name>\n" +
            "\t<author_email>Author@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Biology Quiz</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/9i6mf03zr/logo_b.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Biology</category>\n" +
            "\t<type>Quiz</type>\n" +
            "\t<author_name>Author</author_name>\n" +
            "\t<author_email>Author@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Chemistry Spellings</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/63ofyv8l3/logo_c.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Chemistry</category>\n" +
            "\t<type>Spellings</type>\n" +
            "\t<author_name>Author1</author_name>\n" +
            "\t<author_email>Author1@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>History Info</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/r34jqda9j/logo_h.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>History</category>\n" +
            "\t<type>Info</type>\n" +
            "\t<author_name>Author2</author_name>\n" +
            "\t<author_email>Author2@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Vocabulary</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/lu9ishruf/logo_l.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>English</category>\n" +
            "\t<type>Spellings</type>\n" +
            "\t<author_name>Author3</author_name>\n" +
            "\t<author_email>Author3@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Maths Puzzle</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/qhfku9f7b/logo_m.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Mathematics</category>\n" +
            "\t<type>Quiz</type>\n" +
            "\t<author_name>Author4</author_name>\n" +
            "\t<author_email>Author4@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Physics Info</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/n815nsr3r/logo_p.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Physics</category>\n" +
            "\t<type>Info</type>\n" +
            "\t<author_name>Author5</author_name>\n" +
            "\t<author_email>Author5@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Biology Info</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/9i6mf03zr/logo_b.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Biology</category>\n" +
            "\t<type>Info</type>\n" +
            "\t<author_name>Author</author_name>\n" +
            "\t<author_email>Author@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Chemistry Quiz</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/63ofyv8l3/logo_c.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Chemistry</category>\n" +
            "\t<type>Quiz</type>\n" +
            "\t<author_name>Author1</author_name>\n" +
            "\t<author_email>Author1@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>History Spellings</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/r34jqda9j/logo_h.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>History</category>\n" +
            "\t<type>Spellings</type>\n" +
            "\t<author_name>Author2</author_name>\n" +
            "\t<author_email>Author2@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>English Words</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/lu9ishruf/logo_l.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>English</category>\n" +
            "\t<type>Flashcards</type>\n" +
            "\t<author_name>Author3</author_name>\n" +
            "\t<author_email>Author3@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Maths Info</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/qhfku9f7b/logo_m.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Mathematics</category>\n" +
            "\t<type>Info</type>\n" +
            "\t<author_name>Author4</author_name>\n" +
            "\t<author_email>Author4@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "<app>\n" +
            "\t<title>Physics Flashcards</title>\n" +
            "\t<description>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut libero interdum, scelerisque massa sit amet, finibus velit. In ac dui scelerisque, pretium arcu non, molestie nisi.</description>\n" +
            "\t<appIcon>http://s13.postimg.org/n815nsr3r/logo_p.png</appIcon>\n" +
            "\t<screenshots>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t\t<url>http://s21.postimg.org/jszl533d3/screen.png</url>\n" +
            "\t</screenshots>\n" +
            "\t<category>Physics</category>\n" +
            "\t<type>Flashcards</type>\n" +
            "\t<author_name>Author5</author_name>\n" +
            "\t<author_email>Author5@gmail.com</author_email>\n" +
            "\t<bundleURL>placeholder</bundleURL>\n" +
            "</app>\n" +
            "</applications>";
    public static final List<Apps> appList= new ArrayList<>();
    private static final String KEY_ITEM = "app"; // parent node
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String URL1 = "url";
    private static final String APP_ICON = "appIcon";
    private static final String SCREENSHOTS = "screenshots";
    private static final String CATEGORY = "category";
    private static final String TYPE = "type";
    private static final String AUTHOR_NAME = "author_name";
    private static final String AUTHOR_EMAIL = "author_email";
    private MaterialDialog mAlertDialog;
    private Context mContext;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        mContext=this;
        appList.clear();
        checkInternet();
    }
    private void checkInternet()
    {
        try{
        if(!isNetworkAvailable())
        {
            mAlertDialog=new MaterialDialog(this)
                    .setTitle("Network Connectivity")
                    .setMessage("No internet connection available!")
                    .setPositiveButton("RETRY", new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {mAlertDialog.dismiss();checkInternet();}
                    })
                    .setNegativeButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {mAlertDialog.dismiss();finish();}
                    });
            mAlertDialog.show();
        }
        else {
            Thread background = new Thread() {
                public void run() {
                    try {
                        XMLParser parser = new XMLParser();
                        // String xml = parser.getXmlFromUrl(URL); // getting XML
                        Document doc = parser.getDomElement(XML); // getting DOM element
                        NodeList nodeList = doc.getElementsByTagName(KEY_ITEM);
                        // looping through all item nodes <app>
                        for (int i = 0; i < nodeList.getLength(); i++) {
                            Element elementApp = (Element) nodeList.item(i);
                            Apps ob = new Apps();
                            ob.Name = parser.getValue(elementApp, TITLE);
                            ob.Description = parser.getValue(elementApp, DESCRIPTION);
                            ob.AppIcon = parser.getValue(elementApp, APP_ICON);
                            //ob.BAppIcon=getBitmapFromURL(ob.AppIcon);
                            NodeList nodeScreenshots = doc.getElementsByTagName(SCREENSHOTS);
                            ob.Screenshots = new String[nodeScreenshots.getLength()];
                            for (int j = 0; j < nodeScreenshots.getLength(); j++)
                                ob.Screenshots[j] = parser.getValue((Element) nodeScreenshots.item(j), URL1);
                            ob.Category = parser.getValue(elementApp, CATEGORY);
                            ob.Type = parser.getValue(elementApp, TYPE);
                            ob.Author = parser.getValue(elementApp, AUTHOR_NAME);
                            ob.AuthorEmail = parser.getValue(elementApp, AUTHOR_EMAIL);
                            appList.add(ob);
                        }
                        Thread.sleep(2000);
                        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
                        int x= Integer.parseInt(SP.getString("number_featured_categories", "4"));
                        int y= Integer.parseInt(SP.getString("number_featured_apps","6"));
                        SharedPreferences.Editor editor1 = SP.edit();
                        if(x>appList.size()){
                            editor1.putString("number_featured_apps", appList.size() + "");
                            editor1.apply();}
                        if(x<6){
                            editor1.putString("number_featured_apps", "6");
                            editor1.apply();}
                        if(y>10){
                            editor1.putString("number_featured_categories","10");
                            editor1.apply();}
                        if(y<3){
                            editor1.putString("number_featured_categories","3");
                            editor1.apply();}

                        Intent i = new Intent(getBaseContext(), HomeActivity.class);
                        startActivity(i);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            // start thread
            background.start();
        }
        }
        catch(Exception e){e.printStackTrace();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }
}
