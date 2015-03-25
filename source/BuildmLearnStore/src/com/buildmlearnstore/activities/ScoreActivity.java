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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.buildmlearnstore.model.QuizModel;

public class ScoreActivity extends SherlockActivity {
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
		if(mQuizModel!=null)
		{
			int unanswered = mQuizModel.getQueAnsList().size()
					- mQuizModel.getTotalCorrect() - mQuizModel.getTotalWrong();
			mTv_unanswered.setText("Unanswered: " + unanswered);
		}
		

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
