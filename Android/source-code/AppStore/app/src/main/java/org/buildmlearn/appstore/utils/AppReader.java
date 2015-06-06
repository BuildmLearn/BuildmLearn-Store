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
    private static HashMap<String, String> mInfoMap = new HashMap<String, String>();

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
            br = new BufferedReader(new InputStreamReader(myContext.getAssets()
                    .open(fileName))); // throwing a
            // FileNotFoundException?

            model.setQuizName(br.readLine());
            model.setQuizAuthor(br.readLine());
            String text;
            while ((text = br.readLine()) != null) {
                ArrayList<String> mOptionList = new ArrayList<String>();
                Question ques = new Question();
                String[] temp = text.split("==");
                ques.setQuestion(temp[0]);
                for (int i = 1; i < temp.length - 1; i++) {
                    mOptionList.add(temp[i]);
                }
                ques.setAnswerOption(mOptionList);
                ques.setOptionNumber(Integer.parseInt(temp[temp.length - 1]));

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

        SpellingsModel mSpellingModel = SpellingsModel.getInstance();
        ArrayList<WordModel> mWordList = new ArrayList<WordModel>();
        try {
            br = new BufferedReader(new InputStreamReader(myContext.getAssets()
                    .open(fileName))); // throwing a

            mSpellingModel.setPuzzleName(br.readLine());
            mSpellingModel.setPuzzleAuthor(br.readLine());
            String text;
            while ((text = br.readLine()) != null) {
                if (text.contains("==")) {
                    String[] spelling = text.split("==");
                    int startIndex = spelling[0].length() + 2;
                    String des = text.substring(startIndex);
                    mWordList.add(new WordModel(spelling[0], des));
                }
            }
            mSpellingModel.setSpellingsList(mWordList);

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

    public static void readFlashContent(Context myContext, String folderPath) {

        FlashModel mModel = FlashModel.getInstance();
        String filePath = null;
        ArrayList<String> mFileList = new ArrayList<String>();
        ArrayList<Card> mCardsList = new ArrayList<Card>();
        Resources res = myContext.getResources(); // if you are in an activity
        AssetManager am = res.getAssets();
        String fileList[];
        try {
            fileList = am.list(folderPath);
            if (fileList != null) {
                for (int i = 0; i < fileList.length; i++) {
                    Log.d("", fileList[i]);
                    if (fileList[i].endsWith(".txt")) {
                        filePath = fileList[i];
                        break;
                    }
                    // mFileList.add(fileList[i]);
                }
            }

            br = new BufferedReader(new InputStreamReader(myContext.getAssets()
                    .open(folderPath + File.separator + filePath))); // throwing a FileNotFoundException?
            mModel.setName(br.readLine());
            mModel.setAuthor(br.readLine());
            String text;
            int index = 0;
            while ((text = br.readLine()) != null) {
                String que = "", ans = "", hint = "", imagepath = "";
                String[] dataLine = text.split("__");
                if (dataLine[0].equals("IMAGE")) {
                    imagepath = folderPath + File.separator + "IMAGE" + index
                            + ".png";
					/*
					 * Resources r = myContext.getResources(); int picId =
					 * r.getIdentifier( "image" + String.valueOf(index),
					 * "drawable", "com.buildmlearnstore.activities");
					 */
                }
                String[] dataArray = dataLine[1].split("==");
                // TextView answerText = (TextView)
                // findViewById(R.id.answerText);
                // answerText.setText(dataArray[1]);
                ans = dataArray[1];

                hint = dataArray[0];
                if (dataArray.length == 3) {
                    que = dataArray[2];
                }
                mCardsList.add(new Card(que, ans, hint, imagepath));
                index++;
            }
            mModel.setList(mCardsList);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}