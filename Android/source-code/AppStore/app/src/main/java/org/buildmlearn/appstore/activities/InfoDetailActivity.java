package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 06-06-2015.
 */

import android.os.Bundle;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.InfoModel;

import java.util.HashMap;

public class InfoDetailActivity extends NavigationActivity {
	private TextView mTv_title;
	private TextView mTv_details;

	private String title;
	private HashMap<String, String> mMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.activity_info_detail, frameLayout);
		title = getIntent().getStringExtra("detail_title");
		mMap = InfoModel.getInstance().getInfoMap();
		getSupportActionBar().setTitle(InfoModel.getInstance().getInfoName());
		mTv_title = (TextView) findViewById(R.id.titleText);
		mTv_details = (TextView) findViewById(R.id.detailText);
		mTv_title.setText(title);
		mTv_details.setText(mMap.get(title));

	}

}
