package org.buildmlearn.appstore.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.InfoModel;

import java.util.HashMap;

/**
 * This class deals with showing meaning/description related to a particular word listed in the InfoActivity.
 * It deals with rendering Info-Type apps.
 */
public class InfoDetailActivity extends AppCompatActivity {

	/**
	 * The method is executed first when the activity is created.
	 * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_detail);
		Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar_info_detail);
		mToolbar.setNavigationIcon(R.drawable.ic_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				InfoDetailActivity.this.onBackPressed();
			}
		});
		mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		String title = getIntent().getStringExtra("detail_title");
		HashMap<String, String> mMap = InfoModel.getInstance().getInfoMap();
		mToolbar.setTitle(InfoModel.getInstance().getInfoName());
		TextView mTv_title = (TextView) findViewById(R.id.titleText);
		TextView mTv_details = (TextView) findViewById(R.id.detailText);
		mTv_title.setText(title);
		mTv_details.setText(mMap.get(title));

	}

}
