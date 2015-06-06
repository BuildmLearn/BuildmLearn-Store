package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 06-06-2015.
 */

import java.util.HashMap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.InfoModel;

public class InfoDetailActivity extends AppCompatActivity {
	private TextView mTv_title;
	private TextView mTv_details;

	private String title;
	private HashMap<String, String> mMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_detail);
		title = getIntent().getStringExtra("detail_title");
		mMap = InfoModel.getInstance().getInfoMap();

		mTv_title = (TextView) findViewById(R.id.titleText);
		mTv_details = (TextView) findViewById(R.id.detailText);
		mTv_title.setText(title);
		mTv_details.setText(mMap.get(title));

	}

}
