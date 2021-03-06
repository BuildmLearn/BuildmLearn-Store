package org.buildmlearn.appstore.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.Question;
import org.buildmlearn.appstore.models.QuizModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity renders Quiz related apps. It populates questions and maintains the current score.
 */
public class QuestionActivity extends AppCompatActivity {

	private TextView iQuestion_no_Label;
	private TextView iQuestionLabel;
	private RadioButton iRad1, iRad2, iRad3, iRad0;
	private Button iNextButton;
	private final List<RadioButton> iRadButtonList = new ArrayList<>();
	private int iQuestionIndex = 0;
	private int iCurrentCorrectAnswer;
	private RadioGroup iRadioGroup;
	private ArrayList<Question> mQuestionAnsList;
	private QuizModel mQuizModel;

	/**
	 * The method is executed first when the activity is created.
	 * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions_view);
		Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar_app_questions_view);
		mToolbar.setNavigationIcon(R.drawable.ic_back);
		mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionActivity.this.onBackPressed();
            }
        });
		mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		mQuizModel=QuizModel.getInstance();
		mQuestionAnsList=mQuizModel.getQueAnsList();
		mToolbar.setTitle(mQuizModel.getQuizName());
		iQuestion_no_Label = (TextView) findViewById(R.id.question_no);
		iQuestionLabel = (TextView) findViewById(R.id.question_label);
		iRad0 = (RadioButton) findViewById(R.id.radio0);
		iRad1 = (RadioButton) findViewById(R.id.radio1);
		iRad2 = (RadioButton) findViewById(R.id.radio2);
		iRad3 = (RadioButton) findViewById(R.id.radio3);
		iRadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		iRadButtonList.add(iRad0);
		iRadButtonList.add(iRad1);
		iRadButtonList.add(iRad2);
		iRadButtonList.add(iRad3);
		iNextButton = (Button) findViewById(R.id.next_button);
		iNextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // set all radios to white
                for (int i = 0; i < iRadButtonList.size(); i++) {
                    iRadButtonList.get(i).setBackgroundColor(Color.TRANSPARENT);
                }
                // Increase the index to next ques
                iQuestionIndex = iQuestionIndex + 1;
                if (iQuestionIndex < mQuestionAnsList.size()) {
                    QuestionActivity.this.populateQuestion(iQuestionIndex);
                } else {
                    // if the quiz is over
                    iQuestionIndex = 0;
                    Intent myIntent = new Intent(arg0.getContext(), ScoreActivity.class);
                    myIntent.putExtra("Activity", 0);
                    QuestionActivity.this.startActivity(myIntent);
                    QuestionActivity.this.finish();
                }
            }
        });
		iRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                group.setEnabled(false);
                iRad0.setEnabled(false);
                iRad1.setEnabled(false);
                iRad2.setEnabled(false);
                iRad3.setEnabled(false);
                int answer = R.id.radio0;
                switch (iCurrentCorrectAnswer) {
                    case 1:
                        answer = R.id.radio0;
                        break;
                    case 2:
                        answer = R.id.radio1;
                        break;
                    case 3:
                        answer = R.id.radio2;
                        break;
                    case 4:
                        answer = R.id.radio3;
                        break;
                }
                RadioButton yourAnswer = (RadioButton) QuestionActivity.this.findViewById(checkedId);
                RadioButton originalAnswer = (RadioButton) QuestionActivity.this.findViewById(answer);
                if (iRad0.isChecked() || iRad1.isChecked() || iRad2.isChecked() || iRad3.isChecked()) {
                    if (iRadioGroup.getCheckedRadioButtonId() == answer)
                        mQuizModel.setTotalCorrect(mQuizModel.getTotalCorrect() + 1);
                    else {
                        mQuizModel.setTotalWrong(mQuizModel.getTotalWrong() + 1);
                        yourAnswer.setTextColor(QuestionActivity.this.getResources().getColor(android.R.color.holo_red_dark));
                    }
                }
                originalAnswer.setTextColor(QuestionActivity.this.getResources().getColor(android.R.color.holo_green_light));
            }
        });
		iNextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iQuestionIndex == mQuestionAnsList.size() - 2) {
                    iQuestionIndex++;
                    QuestionActivity.this.populateQuestion(iQuestionIndex);
                    iNextButton.setText("Submit");
                } else if (iQuestionIndex == mQuestionAnsList.size() - 1) {
                    Intent i = new Intent(QuestionActivity.this, ScoreActivity.class);
                    QuestionActivity.this.startActivity(i);
                    QuestionActivity.this.finish();
                } else {
                    iQuestionIndex++;
                    QuestionActivity.this.populateQuestion(iQuestionIndex);
                }
                iRadioGroup.clearCheck();
                iRadioGroup.setEnabled(true);
                iRad0.setTextColor(QuestionActivity.this.getResources().getColor(android.R.color.black));
                iRad1.setTextColor(QuestionActivity.this.getResources().getColor(android.R.color.black));
                iRad2.setTextColor(QuestionActivity.this.getResources().getColor(android.R.color.black));
                iRad3.setTextColor(QuestionActivity.this.getResources().getColor(android.R.color.black));
                iRad0.setEnabled(true);
                iRad1.setEnabled(true);
                iRad2.setEnabled(true);
                iRad3.setEnabled(true);
            }
        });
		populateQuestion(iQuestionIndex);
	}

	/**
	 * Populates the question according to the index.
	 * @param index:The index of the question, which is to be populated.
	 */
	private void populateQuestion(int index) {
		for (int i = 0; i < iRadButtonList.size(); i++) {
			iRadButtonList.get(i).setBackgroundColor(Color.TRANSPARENT);
			iRadButtonList.get(i).setChecked(false);
			iRadButtonList.get(i).setVisibility(View.INVISIBLE);
		}
		iQuestion_no_Label.setText("Question #" + String.valueOf(index + 1) + " of " + mQuestionAnsList.size());
		iQuestionLabel.setText(mQuestionAnsList.get(index).getQuestion());
		for (int i = 0; i < mQuestionAnsList.get(index).getAnswerOption().size() ; i++) {
			iRadButtonList.get(i).setText(mQuestionAnsList.get(index).getAnswerOption().get(i));
			iRadButtonList.get(i).setVisibility(View.VISIBLE);
		}
		iCurrentCorrectAnswer = mQuestionAnsList.get(index).getOptionNumber();
	}
}