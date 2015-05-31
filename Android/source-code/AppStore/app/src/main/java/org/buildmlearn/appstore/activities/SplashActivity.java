package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 25-05-2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.utils.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.buildmlearn.appstore.R;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class SplashActivity extends Activity {
    private String XML="<applications>\n" +
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
    private static final String URL = "http://api.androidhive.info/pizza/?format=xml";
    public static List<Apps> appList=new ArrayList<Apps>();
    static final String KEY_ITEM = "app"; // parent node
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String URL1 = "url";
    private static final String APP_ICON = "appIcon";
    private static final String SCREENSHOTS = "screenshots";
    private static final String CATEGORY = "category";
    private static final String TYPE = "type";
    private static final String AUTHOR_NAME = "author_name";
    private static final String AUTHOR_EMAIL = "author_email";
    private static boolean mInternet=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkInternet();
    }
    private void checkInternet()
    {
        try{
        if(!isNetworkAvailable())
        {System.out.println("Not Available");
            mInternet=false;
            MaterialDialog mAlertDialog=new MaterialDialog(this)
                    .setTitle("Network Connectivity")
                    .setMessage("No internet connection available!")
                    .setPositiveButton("RETRY", new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {checkInternet();}
                    })
                    .setNegativeButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {finish();}
                    });
            mAlertDialog.show();
        }
        else {
            Thread background = new Thread() {
                public void run() {
                    try {
                        XMLParser parser = new XMLParser();
                        //TODO
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
                            System.out.println(ob.Name + ob.AppIcon);
                            appList.add(ob);
                        }
                        Thread.sleep(1000);
                        Intent i = new Intent(getBaseContext(), HomeActivity.class);
                        startActivity(i);
                        //Remove activity
                        finish();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            };
            // start thread
            background.start();
        }
        }
        catch(Exception e){System.out.println(e.getMessage());
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        System.out.println(activeNetworkInfo != null && activeNetworkInfo.isConnected());
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
