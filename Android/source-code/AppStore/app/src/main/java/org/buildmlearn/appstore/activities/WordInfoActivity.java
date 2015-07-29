package org.buildmlearn.appstore.activities;

import android.content.Intent;
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

/**
 * This class deals with displaying result for Spellings Puzzle apps.
 */
public class WordInfoActivity extends AppCompatActivity {

	private boolean isCorrect;
	private int position;
	private SpellingsModel mManager;
	private ArrayList<WordModel> mList;
	private TextToSpeech textToSpeech;
	private int activeWordCount;

	/**
	 * The method is executed first when the activity is created.
	 * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_info);
		Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar_word_info);
		mToolbar.setNavigationIcon(R.drawable.ic_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				WordInfoActivity.this.onBackPressed();
			}
		});
		mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		mManager = SpellingsModel.getInstance();
		mList = mManager.getSpellingsList();
		mToolbar.setTitle(mManager.getPuzzleName());
		Intent spellingIntent = getIntent();
		isCorrect = spellingIntent.getBooleanExtra("result", false);
		position = spellingIntent.getIntExtra("index", 0);
		String enteredText = spellingIntent.getStringExtra("word");
		TextView mTv_Result = (TextView) findViewById(R.id.tv_result);
		TextView mTv_Word_num = (TextView) findViewById(R.id.tv_word_num);
		TextView mTv_word = (TextView) findViewById(R.id.tv_word);
		TextView mTv_enteredWord = (TextView) findViewById(R.id.tv_input_word);

		TextView mTv_description = (TextView) findViewById(R.id.tv_description);
		Button mBtn_Next = (Button) findViewById(R.id.btn_next);
		activeWordCount=mManager.getActiveCount();
		
		if (position == mList.size() - 1) {
			
			mBtn_Next.setText("Finish");
		}
		if (isCorrect) {
			mTv_Result.setText(getString(R.string.msg_successful));
			mTv_Result.setTextColor(getResources().getColor(android.R.color.holo_green_light));
			// convertTextToSpeech(getString(R.string.msg_successful));
			mTv_enteredWord.setVisibility(View.GONE);
		} else {
			mTv_Result.setText(getString(R.string.msg_failure));
			mTv_Result.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
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
								WordInfoActivity.this.convertTextToSpeech(getString(R.string.msg_successful));
							else
								WordInfoActivity.this.convertTextToSpeech(getString(R.string.msg_failure));
						}
					}
				});
		mTv_Word_num.setText("Word #" + (position + 1) + " of " + mList.size());
		mTv_word.setText(mList.get(position).getWord().toLowerCase());
		mTv_description.setText(mList.get(position).getDescription());

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
     * This method is called when Next button is pressed. It navigates to next question(if available), otherwise it navigates to ScoreActivity which shows the current score.
     * @param v: It contains the view object of the button pressed.
     */
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

    /**
     * This method converts the given text to speech. It is used by spellings puzzle.
     * @param text: This is converted to speech.
     */
	@SuppressWarnings("deprecation")
	private void convertTextToSpeech(String text) {

		textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	/**
	 * This method is automatically called when the back button is pressed.
	 */
	@Override
	public void onBackPressed()
	{
		SpellingsModel.clearInstance();
		super.onBackPressed();
	}
}
