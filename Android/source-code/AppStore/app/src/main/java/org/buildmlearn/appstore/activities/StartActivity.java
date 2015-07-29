package org.buildmlearn.appstore.activities;

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

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This is the stating page, which is navigated to, when an app is launched. It reads through the respective buildmlearn file for the app, which has to be rendered.
 */
public class StartActivity extends AppCompatActivity implements OnClickListener {

	private int mOption;
	private TextView author, title;

	/**
	 * The method is executed first when the activity is created.
	 * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		String filePath = "Apps/" + getIntent().getStringExtra("filename") + ".buildmlearn";
		System.out.println(filePath);
		author = (TextView) findViewById(R.id.tv_author);
		title = (TextView) findViewById(R.id.tv_apptitle);
		Button startButton = (Button) findViewById(R.id.btn_start);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(filePath))); // throwing a FileNotFoundException
            String type=br.readLine();
            if(type.contains("InfoTemplate"))mOption=0;
            else if(type.contains("QuizTemplate"))mOption=2;
            else if(type.contains("FlashCardsTemplate"))mOption=1;
            else if(type.contains("SpellingTemplate"))mOption=3;
            else finish();
        }catch(Exception e){finish();}

        startButton.setOnClickListener(this);
		new FileReadTask(mOption, filePath).execute();
	}

	/**
	 * This class is a private async class, which renders different type of buildmlearn files to launch particular type of apps.
	 */
	private class FileReadTask extends AsyncTask<Void, Void, Void> {

		final int mOption;
		final String mFilePath;

        /**
         * This is a default constructor called when an object is created for this class.
         * @param option: It contains the type of the file which is to be rendered.
         * @param filePath: It contains the path of the file, which is to be rendered.
         */
		public FileReadTask(int option, String filePath) {
			this.mOption = option;
			this.mFilePath = filePath;
		}

        /**
         * This method is automatically called, when the class is set to be executed.
         * @param params: It contains null.
         * @return It being a Void Task, returns null.
         */
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

        /**
         * This method is called after the task has been performed.
         * @param result: It does not contain any valid information, being null from the doInBackground() method.
         */
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

    /**
     * This method is called when the start button is pressed, and it navigates to respective activity depending on the type of the app which is launched.
     * @param v View Object of the button pressed
     */
	@Override
	public void onClick(View v) {
		switch (mOption) {
		case 0:
			Intent infoIntent = new Intent(StartActivity.this,InfoActivity.class);
			startActivity(infoIntent);
			finish();
			break;

		case 1:
			Intent flashIntent = new Intent(StartActivity.this,FlashActivity.class);
			startActivity(flashIntent);
			finish();
			break;

		case 2:
			Intent quizIntent = new Intent(StartActivity.this,QuestionActivity.class);
			startActivity(quizIntent);
			finish();
			break;
		case 3:
			Intent spellingIntent = new Intent(StartActivity.this,SpellingActivity.class);
			startActivity(spellingIntent);
			finish();
		}
	}
}
