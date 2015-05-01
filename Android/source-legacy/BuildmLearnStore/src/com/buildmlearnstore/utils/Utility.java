package com.buildmlearnstore.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;

import com.buildmlearnstore.model.Card;
import com.buildmlearnstore.model.FlashModel;
import com.buildmlearnstore.model.InfoModel;
import com.buildmlearnstore.model.Question;
import com.buildmlearnstore.model.QuizModel;
import com.buildmlearnstore.model.SpellingsModel;
import com.buildmlearnstore.model.WordModel;

public class Utility {
	private static BufferedReader br;
	private static HashMap<String, String> mInfoMap = new HashMap<String, String>();

	public static ArrayList<String> listFiles(Context context, String dirFrom) {

		ArrayList<String> mFileList = new ArrayList<String>();
		Resources res = context.getResources(); // if you are in an activity
		AssetManager am = res.getAssets();
		String fileList[];
		try {
			fileList = am.list(dirFrom);
			if (fileList != null) {
				for (int i = 0; i < fileList.length; i++) {
					Log.d("", fileList[i]);
					mFileList.add(fileList[i]);
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
			br = new BufferedReader(new InputStreamReader(myContext.getAssets()
					.open(fileName))); // throwing a FileNotFoundException?

			model.setInfoName(br.readLine());
			model.setInfoAuthor(br.readLine());
			String text;
			while ((text = br.readLine()) != null) {
				String[] tempArr = text.split("==");
				mInfoMap.put(tempArr[0], tempArr[1]);
				stringList.add(tempArr[0]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		model.setInfoMap(mInfoMap);
		model.setListTitleList(stringList);

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
		ArrayList<com.buildmlearnstore.model.WordModel> mWordList = new ArrayList<WordModel>();
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
					.open(folderPath + File.separator +filePath))); // throwing a FileNotFoundException?
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
