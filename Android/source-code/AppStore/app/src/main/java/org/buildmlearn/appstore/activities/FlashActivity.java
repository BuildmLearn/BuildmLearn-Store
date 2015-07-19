package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 06-06-2015.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.Card;
import org.buildmlearn.appstore.models.FlashModel;

import java.util.ArrayList;

public class FlashActivity extends AppCompatActivity implements Animation.AnimationListener {
	private View answerView, questionView;
	private Button preButton;
	private boolean isFlipped = false;
	private int iQuestionIndex = 0;
	private ImageView questionImage;
	private TextView flashCardText, flashcardNumber;
	private ArrayList<Card> mCardList;
	private TextView questionText;
	private String flashCardanswer;
	private Animation animation1;
	private Animation animation2;
	private View currentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flashcards);
		Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar_flashcards);
		mToolbar.setNavigationIcon(R.drawable.ic_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		FlashModel mFlashModel = FlashModel.getInstance();
		mToolbar.setTitle(mFlashModel.getName());
		mCardList = mFlashModel.getList();
		animation1 = AnimationUtils.loadAnimation(this, R.anim.to_middle);
		animation1.setAnimationListener(this);
		animation2 = AnimationUtils.loadAnimation(this, R.anim.from_middle);
		animation2.setAnimationListener(this);

		questionView = findViewById(R.id.questionInMain);
		answerView = findViewById(R.id.answerInMain);

		questionView.setVisibility(View.VISIBLE);
		answerView.setVisibility(View.GONE);
		iQuestionIndex = 0;

		questionImage = (ImageView) findViewById(R.id.questionImage);
		flashCardText = (TextView) findViewById(R.id.flashCardText);
		questionText = (TextView) findViewById(R.id.questionhint);
		flashcardNumber = (TextView) findViewById(R.id.flashCardNumber);

		Button flipButton = (Button) findViewById(R.id.flip_button);
		preButton = (Button) findViewById(R.id.pre_button);
		Button nextButton = (Button) findViewById(R.id.next_button);

		populateQuestion(iQuestionIndex);
		currentView = questionView;

		flipButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				currentView.clearAnimation();
				currentView.setAnimation(animation1);
				currentView.startAnimation(animation1);

			}
		});

		preButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (iQuestionIndex != 0) {
					isFlipped = false;

					iQuestionIndex--;
					questionView.setVisibility(View.VISIBLE);
					answerView.setVisibility(View.GONE);
					currentView = questionView;

					populateQuestion(iQuestionIndex);
				}

			}
		});

		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (iQuestionIndex < mCardList.size() - 1) {
					isFlipped = false;
					iQuestionIndex++;
					questionView.setVisibility(View.VISIBLE);
					answerView.setVisibility(View.GONE);
					currentView = questionView;
					populateQuestion(iQuestionIndex);

				} else {
					finish();
				}

			}
		});
	}

	private void populateQuestion(int index) {
		if (index == 0) {
			preButton.setEnabled(false);

		} else
			preButton.setEnabled(true);

		int cardNum = index + 1;
		flashcardNumber.setText("Card #" + cardNum + " of " + mCardList.size());

		if (mCardList.get(index).getImagePath() != null) {
			questionImage.setVisibility(View.VISIBLE);
			try {
				byte[] decodedString = Base64.decode(mCardList.get(index).getImagePath(), Base64.DEFAULT);
				Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
				questionImage.setImageBitmap(decodedByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			questionImage.setVisibility(View.GONE);

		}
		flashCardanswer = mCardList.get(index).getAnswer();
		questionText.setText(mCardList.get(index).getHint());
		flashCardText.setVisibility(View.GONE);
		if (mCardList.get(index).getQuestion() != null) {
            flashCardText.setVisibility(View.VISIBLE);
            flashCardText.setText(mCardList.get(index).getQuestion());
		}

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation == animation1) {

			TextView answerText = (TextView) findViewById(R.id.answerText);

			if (!isFlipped) {

				answerView.setVisibility(View.VISIBLE);
				questionView.setVisibility(View.GONE);
				isFlipped = true;
				answerText.setText(flashCardanswer);
				currentView = answerView;
			} else {

				isFlipped = false;
				answerText.setText("");
				questionView.setVisibility(View.VISIBLE);
				answerView.setVisibility(View.GONE);
				currentView = questionView;
			}
			currentView.clearAnimation();
			currentView.setAnimation(animation2);
			currentView.startAnimation(animation2);

		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {}

	@Override
	public void onAnimationStart(Animation animation) {}

}
