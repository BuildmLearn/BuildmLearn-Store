/* Copyright (c) 2012, BuildmLearn Contributors listed at http://buildmlearn.org/people/
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

 * Neither the name of the BuildmLearn nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.buildmlearnstore.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.buildmlearnstore.model.Question;
import com.buildmlearnstore.model.QuizModel;

public class QuestionActivity extends SherlockActivity {

	private TextView iQuestion_no_Label;
	private TextView iQuestionLabel;
	private RadioButton iRad1, iRad2, iRad3, iRad0;
	private Button iSubmitButton, iNextButton;
	private List<RadioButton> iRadButtonList = new ArrayList<RadioButton>();
	private int iQuestionIndex = 0;
	private int iCurrentCorrectAnswer;
	private RadioGroup iRadioGroup;
	private ArrayList<Question> mQuestionAnsList;
	private QuizModel mQuizModel;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions_view);
		mQuizModel=QuizModel.getInstance();
		mQuestionAnsList=mQuizModel.getQueAnsList();

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

		iSubmitButton = (Button) findViewById(R.id.submit_button);
		iSubmitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int selectedAnswer = getSelectedAnswer();
				if (selectedAnswer == -1) {
					Toast.makeText(QuestionActivity.this,
							"Please select an answer!", 1000).show();
				} else if (selectedAnswer != -1
						&& selectedAnswer == iCurrentCorrectAnswer) {
					iRadButtonList.get(iCurrentCorrectAnswer)
							.setBackgroundColor(Color.GREEN);
					Toast.makeText(QuestionActivity.this,
							"That's the correct answer!", 1000).show();
					mQuizModel.setTotalCorrect(mQuizModel.getTotalCorrect()+1);
					//gd.correct++;
					
					iSubmitButton.setEnabled(false);
					/*
					 * iSubmitButton.setVisibility(View.GONE);
					 * iNextButton.setVisibility(View.VISIBLE);
					 */
				} else {
					iRadButtonList.get(selectedAnswer).setBackgroundColor(
							Color.RED);
					iRadButtonList.get(iCurrentCorrectAnswer)
							.setBackgroundColor(Color.GREEN);
					Toast.makeText(QuestionActivity.this,
							"Sorry, wrong answer!", 1000).show();
					mQuizModel.setTotalWrong(mQuizModel.getTotalWrong()+1);
					iSubmitButton.setEnabled(false);
					//gd.wrong++;
					// iSubmitButton.setVisibility(View.GONE);
					// iNextButton.setVisibility(View.VISIBLE);

				}
			}
		});

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

				if (iQuestionIndex <mQuestionAnsList.size()) {
					populateQuestion(iQuestionIndex);

					iSubmitButton.setEnabled(true);
					// iNextButton.setVisibility(View.GONE);
				} else {
					// if the quiz is over
					reInitialize();
					Intent myIntent = new Intent(arg0.getContext(),
							ScoreActivity.class);
					startActivity(myIntent);
					finish();
				}
			}
		});
		// iNextButton.setVisibility(View.GONE);

		populateQuestion(iQuestionIndex);

	}

	public void radioClick(View v) {

	}

	public void populateQuestion(int index) {
		for (int i = 0; i < iRadButtonList.size(); i++) {
			iRadButtonList.get(i).setBackgroundColor(Color.TRANSPARENT);
			iRadButtonList.get(i).setChecked(false);
			iRadButtonList.get(i).setVisibility(View.INVISIBLE);
		}

		iQuestion_no_Label.setText("Question #" + String.valueOf(index + 1)
				+ " of " + mQuestionAnsList.size());

		//String[] ques_content = mQuestionAnsList.get(index).split("==");

		//String question = ques_content[0];
		iQuestionLabel.setText(mQuestionAnsList.get(index).getQuestion());

		for (int i = 0; i < mQuestionAnsList.get(index).getAnswerOption().size() ; i++) {
			iRadButtonList.get(i).setText(mQuestionAnsList.get(index).getAnswerOption().get(i));
			iRadButtonList.get(i).setVisibility(View.VISIBLE);
		}

		iCurrentCorrectAnswer = mQuestionAnsList.get(index).getOptionNumber();
	}

	public int getSelectedAnswer() {
		int selected = -1;
		for (int i = 0; i < iRadButtonList.size(); i++) {
			if (iRadButtonList.get(i).isChecked()) {
				return i;
			}
		}
		return selected;
	}

	public void reInitialize() {

		iQuestionIndex = 0;
	//	gd.iQuizList.clear();
	}


}