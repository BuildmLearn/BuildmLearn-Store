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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.buildmlearnstore.model.FlashModel;
import com.buildmlearnstore.model.InfoModel;
import com.buildmlearnstore.model.QuizModel;
import com.buildmlearnstore.model.SpellingsModel;
import com.buildmlearnstore.utils.Constants;
import com.buildmlearnstore.utils.Utility;

public class StartActivity extends SherlockActivity implements OnClickListener {

	/** Called when the activity is first created. */

	private int mOption;
	private String filePath;
	private TextView author, title;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR); // Add this
		// line

		setContentView(R.layout.activity_start);

		mOption = getIntent().getIntExtra(Constants.OPTION, 0);
		filePath = getIntent().getStringExtra(Constants.FILENAME);

		author = (TextView) findViewById(R.id.tv_author);
		title = (TextView) findViewById(R.id.tv_apptitle);

		Button startButton = (Button) findViewById(R.id.btn_start);
		startButton.setOnClickListener(this);
		new FileReadTask(mOption, filePath).execute();

	}

	private class FileReadTask extends AsyncTask<Void, Void, Void> {

		int mOption;
		String mFilePath;

		public FileReadTask(int option, String filePath) {

			this.mOption = option;

			this.mFilePath = filePath;
		}

		@Override
		protected Void doInBackground(Void... params) {

			switch (mOption) {
			case 0:
				Utility.readInfoFile(getApplicationContext(), mFilePath);
				break;
			case 1:
				Utility.readFlashContent(getApplicationContext(), mFilePath);

				break;
			case 2:
				Utility.readQuizFile(getApplicationContext(), mFilePath);
				
				break;
			case 3:

				Utility.readSpellingsContent(getApplicationContext(), mFilePath);
				break;

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			switch (mOption) {
			case 0:
				author.setText(InfoModel.getInstance().getInfoAuthor());
				title.setText(InfoModel.getInstance().getInfoName());

				break;
			case 1:
				author.setText(FlashModel.getInstance().getAuthor());
				title.setText(FlashModel.getInstance().getName());

				break;

			case 2:
				author.setText(QuizModel.getInstance().getQuizAuthor());
				title.setText(QuizModel.getInstance().getQuizName());

				break;

			case 3:
				author.setText(SpellingsModel.getInstance().getPuzzleAuthor());
				title.setText(SpellingsModel.getInstance().getPuzzleName());
				break;
				
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (mOption) {
		case 0:
			Intent infoIntent = new Intent(StartActivity.this,
					InfoActivity.class);
			startActivity(infoIntent);

			break;

		case 1:
			Intent flashIntent = new Intent(StartActivity.this,
					FlashActivity.class);
			startActivity(flashIntent);
			break;

		case 2:
			Intent quizIntent = new Intent(StartActivity.this,
					QuestionActivity.class);
			startActivity(quizIntent);
			break;
		case 3:
			Intent spellingIntent = new Intent(StartActivity.this,
					SpellingActivity.class);
			startActivity(spellingIntent);
			
		}

	}

}
