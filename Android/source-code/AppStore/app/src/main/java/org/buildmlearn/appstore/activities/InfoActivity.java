package org.buildmlearn.appstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.InfoModel;

import java.util.ArrayList;

/**
 * This class deals with rendering Info-Type apps. It shows a list of words, which has their meaning and respective information shown in the InfoDetailActivity.
 */
public class InfoActivity extends AppCompatActivity {

	private static ArrayList<String> stringList;

	/**
	 * The method is executed first when the activity is created.
	 * @param savedInstanceState The bundle stores all the related parameters, if it has to be used when resuming the app.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar_info);
		mToolbar.setNavigationIcon(R.drawable.ic_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				InfoActivity.this.onBackPressed();
			}
		});
		mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
		ListView listView = (ListView) findViewById(R.id.list);
		stringList = InfoModel.getInstance().getListTitleList();
		mToolbar.setTitle(InfoModel.getInstance().getInfoName());
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, stringList);
		listView.setAdapter(adapter);
		listView.setDivider(getResources().getDrawable(android.R.drawable.divider_horizontal_bright));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent myIntent = new Intent(view.getContext(),
						InfoDetailActivity.class);
				myIntent.putExtra("detail_title", stringList.get(position));
				InfoActivity.this.startActivity(myIntent);
			}
		});
	}
}