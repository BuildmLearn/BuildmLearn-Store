package org.buildmlearn.appstore.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.SpellingsModel;
import org.buildmlearn.appstore.models.WordModel;

import java.util.ArrayList;
import java.util.Locale;

public class SpellingActivity extends AppCompatActivity implements
		TextToSpeech.OnInitListener {
	private TextToSpeech textToSpeech;
	private ArrayList<WordModel> mWordList;
	private int count=0;
	private AlertDialog mAlert;
	private TextView mTv_WordNumber;
	private Button mBtn_Spell, mBtn_Skip;
	private EditText mEt_Spelling;
	private SeekBar mSb_SpeechRate;
	private SpellingsModel mSpellingModel;
	private int totalCorrect,totalWorng;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spelling);
		Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar_spelling);
		mToolbar.setNavigationIcon(R.drawable.ic_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		mBtn_Spell = (Button) findViewById(R.id.btn_ready);
		mBtn_Skip = (Button) findViewById(R.id.btn_skip);
		mSb_SpeechRate = (SeekBar) findViewById(R.id.sb_speech);
		mTv_WordNumber = (TextView) findViewById(R.id.tv_word_number);
		textToSpeech = new TextToSpeech(this, this);
		mSpellingModel=SpellingsModel.getInstance();
        mToolbar.setTitle(mSpellingModel.getPuzzleName());
		mWordList = mSpellingModel.getSpellingsList();
		count = mSpellingModel.getActiveCount();
		totalCorrect=mSpellingModel.getTotalCorrect();
		totalWorng=mSpellingModel.getTotalWrong();
		mTv_WordNumber.setText("Word #" + (count + 1) + " of "
                + mWordList.size());
	}

	public void click(View view) {
		switch (view.getId()) {
		case R.id.btn_skip:
			if (count < mWordList.size() - 1) {

				count++;
				mSpellingModel.setActiveCount(count);
				mTv_WordNumber.setText("Word #" + (count + 1) + " of "
						+ mWordList.size());
				mBtn_Spell.setEnabled(false);
				mBtn_Skip.setEnabled(false);
				mBtn_Skip.setTextColor(Color.WHITE);
				mBtn_Spell.setTextColor(Color.WHITE);
			} else {
				Intent resultIntent = new Intent(this, ScoreActivity.class);
				resultIntent.putExtra("Activity",1);// 0: Quiz Template and 1: Spellings Template
				startActivity(resultIntent);
				finish();

			}
			break;

		case R.id.btn_speak:
            if (Build.VERSION.RELEASE.startsWith("5"))convertTextToSpeech21(mWordList.get(count).getWord());
            else convertTextToSpeech(mWordList.get(count).getWord());
			mBtn_Spell.setEnabled(true);
			mBtn_Skip.setEnabled(true);
			break;
		case R.id.btn_ready:

			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(
					R.layout.dialog_spellinginput, null);
			Builder builder = new Builder(this);
			mAlert = builder.create();
			mAlert.setCancelable(true);
			mAlert.setView(textEntryView, 10, 10, 10, 10);
			if (mAlert != null && !mAlert.isShowing()) {
				mAlert.show();
			}
			mEt_Spelling = (EditText) mAlert.findViewById(R.id.et_spelling);
			Button mBtn_Submit = (Button) mAlert.findViewById(R.id.btn_submit);
			mBtn_Submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					submit();
				}
			});

			break;

		}

	}

	/**
	 * Releases the resources used by the TextToSpeech engine. It is good
	 * practice for instance to call this method in the onDestroy() method of an
	 * Activity so the TextToSpeech engine can be cleanly stopped.
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		textToSpeech.shutdown();
	}

	/**
	 * Speaks the string using the specified queuing strategy and speech
	 * parameters.
	 */
	@TargetApi(21)
	private void convertTextToSpeech21(String text) {
		float speechRate = getProgressValue(mSb_SpeechRate.getProgress());
		textToSpeech.setSpeechRate(speechRate);
		textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
	}

	private void convertTextToSpeech(String text) {
		float speechRate = getProgressValue(mSb_SpeechRate.getProgress());
		textToSpeech.setSpeechRate(speechRate);
		textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}


	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = textToSpeech.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("error", "This Language is not supported");
			}
		} else {
			Log.e("error", "Initilization Failed!");
		}
	}

	public void submit() {
		String input = mEt_Spelling.getText().toString().trim();
		if (input == null || input.length() == 0) {
			Toast.makeText(SpellingActivity.this, "Please enter the spelling",
					Toast.LENGTH_SHORT).show();

		} else {
			mAlert.dismiss();
			boolean isCorrect = false;
			if (mEt_Spelling.getText().toString()
					.equalsIgnoreCase(mWordList.get(count).getWord())) {
				isCorrect = true;
				mSpellingModel.setTotalCorrect(totalCorrect+1);
			} else {
				mSpellingModel.setTotalWrong(totalWorng+1);
			}
			Intent wordInfoIntent = new Intent(SpellingActivity.this,
					WordInfoActivity.class);
			wordInfoIntent.putExtra("result", isCorrect);
			wordInfoIntent.putExtra("index", count);
			wordInfoIntent.putExtra("word", input);

			startActivity(wordInfoIntent);
			finish();
		}
	}

	private float getProgressValue(int percent) {
		float temp = ((float) percent / 100);
		float per = temp * 2;
		return per;
	}


}
