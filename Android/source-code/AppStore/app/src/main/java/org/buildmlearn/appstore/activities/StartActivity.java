package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 06-06-2015.
 */
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.FlashModel;
import org.buildmlearn.appstore.models.InfoModel;
import org.buildmlearn.appstore.models.QuizModel;
import org.buildmlearn.appstore.models.SpellingsModel;
import org.buildmlearn.appstore.utils.AppReader;

public class StartActivity extends AppCompatActivity implements OnClickListener {

	public static String FILENAME="filename";
	public static String OPTION="option";

	private int mOption;
	private String filePath;
	private TextView author, title;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		mOption = getIntent().getIntExtra(OPTION, 0);
		filePath = getIntent().getStringExtra(FILENAME);
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
				AppReader.readInfoFile(getApplicationContext(), mFilePath);
				break;
			case 1:
				AppReader.readFlashContent(getApplicationContext(), mFilePath);
                break;
			case 2:
				AppReader.readQuizFile(getApplicationContext(), mFilePath);
				break;
			case 3:
				AppReader.readSpellingsContent(getApplicationContext(), mFilePath);
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
			finish();
			break;

		case 1:
			Intent flashIntent = new Intent(StartActivity.this,
					FlashActivity.class);
			startActivity(flashIntent);
			finish();
			break;

		case 2:
			Intent quizIntent = new Intent(StartActivity.this,
					QuestionActivity.class);
			startActivity(quizIntent);
			finish();
			break;
		case 3:
			Intent spellingIntent = new Intent(StartActivity.this,
					SpellingActivity.class);
			startActivity(spellingIntent);
			finish();
		}

	}

}
