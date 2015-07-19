package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 06-06-2015.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.InfoModel;

import java.util.HashMap;

public class InfoDetailActivity extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_detail);
		Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar_info_detail);
		mToolbar.setNavigationIcon(R.drawable.ic_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
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
