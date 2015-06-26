package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 06-06-2015.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.SpellingsModel;
import org.buildmlearn.appstore.models.WordModel;

import java.util.ArrayList;
import java.util.Locale;

public class WordInfoActivity extends AppCompatActivity {

	private Intent spellingIntent;
	private boolean isCorrect;
	private int position;
	private TextView mTv_Result, mTv_enteredWord, mTv_word, mTv_description,
			mTv_Word_num;
	private SpellingsModel mManager;
	private ArrayList<WordModel> mList;
	private Button mBtn_Next;
	private TextToSpeech textToSpeech;
	private String enteredText;
	private int activeWordCount;

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_info);
		Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar_word_info);
		mToolbar.setNavigationIcon(R.drawable.ic_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		mManager = SpellingsModel.getInstance();
		mList = mManager.getSpellingsList();
		mToolbar.setTitle(mManager.getPuzzleName());
		spellingIntent = getIntent();
		isCorrect = spellingIntent.getBooleanExtra("result", false);
		position = spellingIntent.getIntExtra("index", 0);
		enteredText = spellingIntent.getStringExtra("word");
		mTv_Result = (TextView) findViewById(R.id.tv_result);
		mTv_Word_num = (TextView) findViewById(R.id.tv_word_num);
		mTv_word = (TextView) findViewById(R.id.tv_word);
		mTv_enteredWord = (TextView) findViewById(R.id.tv_input_word);

		mTv_description = (TextView) findViewById(R.id.tv_description);
		mBtn_Next = (Button) findViewById(R.id.btn_next);
		activeWordCount=mManager.getActiveCount();
		
		if (position == mList.size() - 1) {
			
			mBtn_Next.setText("Finish");
		}
		if (isCorrect) {
			mTv_Result.setText(getString(R.string.msg_successful));
			mTv_Result.setTextColor(Color.GREEN);
			// convertTextToSpeech(getString(R.string.msg_successful));
			mTv_enteredWord.setVisibility(View.GONE);
		} else {
			mTv_Result.setText(getString(R.string.msg_failure));
			mTv_Result.setTextColor(Color.RED);
			mTv_enteredWord.setText(getString(R.string.you_entered) + " "
					+ enteredText.toLowerCase());
			// convertTextToSpeech("Wrong");
		}
		textToSpeech = new TextToSpeech(this,
				new TextToSpeech.OnInitListener() {

					@Override
					public void onInit(int arg0) {
						if (arg0 == TextToSpeech.SUCCESS) {
							textToSpeech.setLanguage(Locale.US);
							if (isCorrect)
								convertTextToSpeech(getString(R.string.msg_successful));
							else
								convertTextToSpeech(getString(R.string.msg_failure));
						}
					}
				});
		mTv_Word_num.setText("Word #" + (position + 1) + " of " + mList.size());
		mTv_word.setText(mList.get(position).getWord().toLowerCase());
		mTv_description.setText(mList.get(position).getDescription());

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		textToSpeech.shutdown();
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next:
			if (position < mList.size() - 1) {
				mManager.setActiveCount(activeWordCount+1);

				Intent spellingAgain = new Intent(this, SpellingActivity.class);
				startActivity(spellingAgain);
				finish();
			} else {

				Intent resultIntent = new Intent(this, ScoreActivity.class);
				startActivity(resultIntent);
				finish();

			}
			break;
		}
	}

	private void convertTextToSpeech(String text) {

		textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}/*
	 * 
	 * @Override public void onInit(int status) { if (status ==
	 * TextToSpeech.SUCCESS) { int result = textToSpeech.setLanguage(Locale.US);
	 * if (result == TextToSpeech.LANG_MISSING_DATA || result ==
	 * TextToSpeech.LANG_NOT_SUPPORTED) { Log.e("error",
	 * "This Language is not supported"); } } else { Log.e("error",
	 * "Initilization Failed!"); } }
	 */
}
