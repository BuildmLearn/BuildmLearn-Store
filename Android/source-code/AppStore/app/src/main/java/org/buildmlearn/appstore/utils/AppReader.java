package org.buildmlearn.appstore.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.buildmlearn.appstore.activities.HomeActivity;
import org.buildmlearn.appstore.models.AppInfo;
import org.buildmlearn.appstore.models.Apps;
import org.buildmlearn.appstore.models.Card;
import org.buildmlearn.appstore.models.FlashModel;
import org.buildmlearn.appstore.models.InfoModel;
import org.buildmlearn.appstore.models.Question;
import org.buildmlearn.appstore.models.QuizModel;
import org.buildmlearn.appstore.models.SpellingsModel;
import org.buildmlearn.appstore.models.WordModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Srujan Jha on 6/6/2015.
 */

public class AppReader {
    private static BufferedReader br;

    public static ArrayList<AppInfo> listApps(Context context) {

        ArrayList<AppInfo> mFileList = new ArrayList<AppInfo>();
        Resources res = context.getResources();
        AssetManager am = res.getAssets();
        String appList[],iconList[];
        try {
            appList = am.list("Apps");
            iconList = am.list("Icons");
            if (appList != null) {
                for (int i = 0; i < appList.length; i++) {
                    Log.d("", appList[i]);
                    AppInfo app=new AppInfo();
                    app.Name=(appList[i].substring(0,appList[i].indexOf(".buildmlearn")));
                    app.AppIcon=BitmapFactory.decodeStream(am.open("Icons/" + iconList[i]));
                    BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("Apps/"+appList[i])));
                    String type=br.readLine();
                    if(type.contains("InfoTemplate"))app.Type=0;
                    else if(type.contains("QuizTemplate"))app.Type=2;
                    else if(type.contains("FlashCardsTemplate"))app.Type=1;
                    else if(type.contains("SpellingTemplate"))app.Type=3;

                    mFileList.add(app);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mFileList;
    }

    public static String ReadFile(Context myContext, String file) {

        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(myContext.getAssets()
                    .open(file))); // throwing a FileNotFoundException?
            String text = "";
            String tmp = "";
            while ((tmp = br.readLine()) != null) {
                text = text + tmp;
            }
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }

    public static void readInfoFile(Context myContext, String fileName) {
        InfoModel model = InfoModel.getInstance();
        ArrayList<String> stringList = new ArrayList<String>();
        try {
            HashMap<String, String> mInfoMap = new HashMap<String, String>();
            XMLParser parser = new XMLParser();
            br = new BufferedReader(new InputStreamReader(myContext.getAssets().open(fileName))); // throwing a FileNotFoundException
            String xml = "", temp = "";
            while ((temp = br.readLine()) != null) {xml+=temp;} //getting xml
            Document doc = parser.getDomElement(xml); // getting DOM element
            Element elementAuthor= (Element) doc.getElementsByTagName("author").item(0);
            model.setInfoAuthor(parser.getValue(elementAuthor, "name"));
            model.setInfoName(fileName.substring(5,fileName.length()-12));
            NodeList nodeList = doc.getElementsByTagName("item");
            System.out.println("InfoName"+model.getInfoName()+model.getInfoAuthor());
                // looping through all item nodes <app>
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elementInfo = (Element) nodeList.item(i);
                String infoKey = parser.getValue(elementInfo, "item_title");
                String infoValue = parser.getValue(elementInfo, "item_description");
                mInfoMap.put(infoKey, infoValue);
                stringList.add(infoKey);
            }
            model.setInfoMap(mInfoMap);
            model.setListTitleList(stringList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
                try {
                    br.close(); // stop reading
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
    }

    public static void readQuizFile(Context myContext, String fileName) {
        QuizModel model = QuizModel.getInstance();
        ArrayList<Question> mQuestionList = new ArrayList<Question>();

        try {
            XMLParser parser = new XMLParser();

            br = new BufferedReader(new InputStreamReader(myContext.getAssets().open(fileName))); // throwing a FileNotFoundException
            String xml = "", temp = "";
            while ((temp = br.readLine()) != null) {
                xml += temp;
            } //getting xml
            Document doc = parser.getDomElement(xml); // getting DOM element
            Element elementAuthor = (Element) doc.getElementsByTagName("author").item(0);
            model.setQuizAuthor(parser.getValue(elementAuthor, "name"));
            model.setQuizName(fileName.substring(5, fileName.length() - 12));
            NodeList nodeList = doc.getElementsByTagName("item");
            NodeList optionList=doc.getElementsByTagName("option");
            int index=0;
            // looping through all item nodes <app>
            for (int i = 0; i < nodeList.getLength(); i++) {

                Element elementInfo = (Element) nodeList.item(i);
                ArrayList<String> mOptionList = new ArrayList<String>();
                Question ques = new Question();
                ques.setQuestion(parser.getValue(elementInfo, "question"));

                for (int j = 0; j < 4; j++) {
                    mOptionList.add(parser.getElementValue(optionList.item(index++)));
                }
                ques.setAnswerOption(mOptionList);
                ques.setOptionNumber(Integer.parseInt(parser.getValue(elementInfo, "answer")));
                mQuestionList.add(ques);
            }
            model.setQueAnsList(mQuestionList);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void readSpellingsContent(Context myContext, String fileName) {

        SpellingsModel model = SpellingsModel.getInstance();
        ArrayList<WordModel> wordList = new ArrayList<WordModel>();
        try {
            XMLParser parser = new XMLParser();
            br = new BufferedReader(new InputStreamReader(myContext.getAssets().open(fileName))); // throwing a FileNotFoundException
            String xml = "", temp = "";
            while ((temp = br.readLine()) != null) {
                xml += temp;
            } //getting xml
            Document doc = parser.getDomElement(xml); // getting DOM element
            Element elementAuthor = (Element) doc.getElementsByTagName("author").item(0);
            model.setPuzzleAuthor(parser.getValue(elementAuthor, "name"));
            model.setPuzzleName(fileName.substring(5, fileName.length() - 12));
            NodeList nodeList = doc.getElementsByTagName("item");
            // looping through all item nodes <app>
            for (int i = 0; i < nodeList.getLength(); i++) {

                Element elementInfo = (Element) nodeList.item(i);
                WordModel word = new WordModel(
                        parser.getValue(elementInfo, "word"),
                        parser.getValue(elementInfo, "meaning"));
                wordList.add(word);
            }
            model.setSpellingsList(wordList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void readFlashContent(Context myContext, String fileName) {

        FlashModel model = FlashModel.getInstance();
        ArrayList<Card> cardList = new ArrayList<Card>();
        try {
            XMLParser parser = new XMLParser();
            br = new BufferedReader(new InputStreamReader(myContext.getAssets().open(fileName))); // throwing a FileNotFoundException
            String xml = "", temp = "";
            while ((temp = br.readLine()) != null) {
                xml += temp;
            } //getting xml
            Document doc = parser.getDomElement(xml); // getting DOM element
            Element elementAuthor = (Element) doc.getElementsByTagName("author").item(0);
            model.setAuthor(parser.getValue(elementAuthor, "name"));
            model.setName(fileName.substring(5, fileName.length() - 12));
            NodeList nodeList = doc.getElementsByTagName("item");
            // looping through all item nodes <app>
            for (int i = 0; i < nodeList.getLength(); i++) {

                Element elementInfo = (Element) nodeList.item(i);
                Card card = new Card(
                        parser.getValue(elementInfo, "question"),
                        parser.getValue(elementInfo, "answer"),
                        parser.getValue(elementInfo, "hint"),
                        parser.getValue(elementInfo, "image"));
                cardList.add(card);
            }
            model.setList(cardList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}