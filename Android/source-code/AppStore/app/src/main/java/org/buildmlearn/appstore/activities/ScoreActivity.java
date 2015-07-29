package org.buildmlearn.appstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.QuizModel;
import org.buildmlearn.appstore.models.SpellingsModel;

/**
 * This activity shows the current score (total answered, unanswered and total wrong attempts) related to
 * the current Quiz or Spellings Puzzle app.
 */
public class ScoreActivity extends AppCompatActivity {
	private QuizModel mQuizModel;
    private SpellingsModel mSpellingsModel;
    private int activity=0;// 0: Quiz Template and 1: Spellings Template
	/**
     * The method is executed first when the activity is created.
     * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
     */
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.score_view);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar_app_score_view);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoreActivity.this.onBackPressed();
            }
        });
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        Intent intent=getIntent();
        activity=intent.getIntExtra("Activity",0);
        if(activity==1)
        {mSpellingsModel=SpellingsModel.getInstance();mToolbar.setTitle(mSpellingsModel.getPuzzleName());}
        else
        {mQuizModel = QuizModel.getInstance();mToolbar.setTitle(mQuizModel.getQuizName());}

        TextView mTv_correct = (TextView) findViewById(R.id.tv_correct);
        TextView mTv_wrong = (TextView) findViewById(R.id.tv_wrong);
        TextView mTv_unanswered = (TextView) findViewById(R.id.tv_unanswered);
        if(activity==1)
        {
            mTv_correct.setText("Total Correct: " + mSpellingsModel.getTotalCorrect());
          mTv_wrong.setText("Total Wrong: " + mSpellingsModel.getTotalWrong());
                    int unanswered = mSpellingsModel.getSpellingsList().size()- mSpellingsModel.getTotalCorrect() - mSpellingsModel.getTotalWrong();
                    mTv_unanswered.setText("Unanswered: " + unanswered);
               }
               else {
            mTv_correct.setText("Total Correct: " + mQuizModel.getTotalCorrect());
            mTv_wrong.setText("Total Wrong: " + mQuizModel.getTotalWrong());
            int unanswered = mQuizModel.getQueAnsList().size() - mQuizModel.getTotalCorrect() - mQuizModel.getTotalWrong();
            mTv_unanswered.setText("Unanswered: " + unanswered);
        }
		QuizModel.clearInstance();
        SpellingsModel.clearInstance();
		Button startAgainButton = (Button) findViewById(R.id.start_again_button);
		startAgainButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (activity == 1) {
                    Intent myIntent = new Intent(arg0.getContext(),
                            SpellingActivity.class);
                    ScoreActivity.this.startActivityForResult(myIntent, 0);
                    ScoreActivity.this.finish();
                } else {
                    Intent myIntent = new Intent(arg0.getContext(),
                            QuestionActivity.class);
                    ScoreActivity.this.startActivityForResult(myIntent, 0);
                    ScoreActivity.this.finish();
                }
            }
        });

		Button quitButton = (Button) findViewById(R.id.quit_button);
		quitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ScoreActivity.this.finish();
            }
        });
	}

}
