package org.buildmlearn.appstore.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.Card;
import org.buildmlearn.appstore.models.FlashModel;

import java.util.ArrayList;

/**
 * This activity deals with the Flash Card apps. Any Flash card app is launched using this activity. It renders XML to show cards in the particular app.
 */
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

	/**
	 * The method is executed first when the activity is created.
	 * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flashcards);
		Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar_flashcards);
		mToolbar.setNavigationIcon(R.drawable.ic_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FlashActivity.this.onBackPressed();
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

		flipButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				currentView.clearAnimation();
				currentView.setAnimation(animation1);
				currentView.startAnimation(animation1);

			}
		});

		preButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (iQuestionIndex != 0) {
					isFlipped = false;
					iQuestionIndex--;
					questionView.setVisibility(View.VISIBLE);
					answerView.setVisibility(View.GONE);
					currentView = questionView;
					FlashActivity.this.populateQuestion(iQuestionIndex);
				}
			}
		});
		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (iQuestionIndex < mCardList.size() - 1) {
					isFlipped = false;
					iQuestionIndex++;
					questionView.setVisibility(View.VISIBLE);
					answerView.setVisibility(View.GONE);
					currentView = questionView;
					FlashActivity.this.populateQuestion(iQuestionIndex);

				} else {
					FlashActivity.this.finish();
				}

			}
		});
	}

	/**
	 * This method populates questions to the cards.
	 * @param index This is the number of the question which has to be loaded.
	 */
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

    /**
     * This method is called when the animation ends. It sets the state of the card, whether it is flipped or not.
     * @param animation Animation Object
     */
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

    /**
     * Default methods implemented when the Class Animation.AnimationListener is implemented. No use as such for this project.
     * @param animation Animation Object
     */
	@Override
	public void onAnimationRepeat(Animation animation) {}

    /**
     * Default methods implemented when the Class Animation.AnimationListener is implemented. No use as such for this project.
     * @param animation Animation Object
     */
	@Override
	public void onAnimationStart(Animation animation) {}

}
