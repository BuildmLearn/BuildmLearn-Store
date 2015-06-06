package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 06-06-2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.QuizModel;

public class ScoreActivity extends AppCompatActivity {
	private QuizModel mQuizModel;
	private TextView mTv_correct, mTv_wrong, mTv_unanswered;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_view);
		mQuizModel = QuizModel.getInstance();

		mTv_correct = (TextView) findViewById(R.id.tv_correct);
		mTv_wrong = (TextView) findViewById(R.id.tv_wrong);
		mTv_unanswered = (TextView) findViewById(R.id.tv_unanswered);
		mTv_correct.setText("Total Correct: " + mQuizModel.getTotalCorrect());
		mTv_wrong.setText("Total Wrong: " + mQuizModel.getTotalWrong());
		int unanswered = mQuizModel.getQueAnsList().size()
				- mQuizModel.getTotalCorrect() - mQuizModel.getTotalWrong();
		mTv_unanswered.setText("Unanswered: " + unanswered);

		Button startAgainButton = (Button) findViewById(R.id.start_again_button);
		startAgainButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(arg0.getContext(),
						QuestionActivity.class);
				startActivityForResult(myIntent, 0);
				finish();
			}
		});

		Button quitButton = (Button) findViewById(R.id.quit_button);
		quitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// android.os.Process.killProcess(android.os.Process.myPid());
				finish();
			}
		});
	}

}
