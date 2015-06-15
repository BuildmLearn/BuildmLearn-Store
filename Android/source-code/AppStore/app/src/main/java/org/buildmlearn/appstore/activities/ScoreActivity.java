package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 06-06-2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.QuizModel;
import org.buildmlearn.appstore.models.SpellingsModel;

public class ScoreActivity extends NavigationActivity {
	private QuizModel mQuizModel;
    private SpellingsModel mSpellingsModel;
	private TextView mTv_correct, mTv_wrong, mTv_unanswered;
    private int activity=0;// 0: Quiz Template and 1: Spellings Template
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.score_view, frameLayout);

        Intent intent=getIntent();
        activity=intent.getIntExtra("Activity",0);
        if(activity==1)
        {mSpellingsModel=SpellingsModel.getInstance();getSupportActionBar().setTitle(mSpellingsModel.getPuzzleName());}
        else
        {mQuizModel = QuizModel.getInstance();getSupportActionBar().setTitle(mQuizModel.getQuizName());}

		mTv_correct = (TextView) findViewById(R.id.tv_correct);
		mTv_wrong = (TextView) findViewById(R.id.tv_wrong);
		mTv_unanswered = (TextView) findViewById(R.id.tv_unanswered);
        if(activity==1)
        {
            mTv_correct.setText("Total Correct: " + mSpellingsModel.getTotalCorrect());
          mTv_wrong.setText("Total Wrong: " + mSpellingsModel.getTotalWrong());
                    int unanswered = mSpellingsModel.getSpellingsList().size()- mSpellingsModel.getTotalCorrect() - mSpellingsModel.getTotalWrong();
                    mTv_unanswered.setText("Unanswered: " + unanswered);
               }
               else
        {
                           mTv_correct.setText("Total Correct: " + mQuizModel.getTotalCorrect());
                   mTv_wrong.setText("Total Wrong: " + mQuizModel.getTotalWrong());
                   int unanswered = mQuizModel.getQueAnsList().size()-mQuizModel.getTotalCorrect() - mQuizModel.getTotalWrong();
                    mTv_unanswered.setText("Unanswered: " + unanswered);
                }
		mQuizModel.clearInstance();

		Button startAgainButton = (Button) findViewById(R.id.start_again_button);
		startAgainButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (activity == 1) {
                    Intent myIntent = new Intent(arg0.getContext(),
                            SpellingActivity.class);
                    startActivityForResult(myIntent, 0);
                    mSpellingsModel.clearInstance();
                    finish();
                } else {
                    Intent myIntent = new Intent(arg0.getContext(),
                            QuestionActivity.class);
                    startActivityForResult(myIntent, 0);
                    mQuizModel.clearInstance();
                    finish();
                }
            }
        });

		Button quitButton = (Button) findViewById(R.id.quit_button);
		quitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
                mSpellingsModel.clearInstance();
                mQuizModel.clearInstance();
				finish();
			}
		});
	}

}
