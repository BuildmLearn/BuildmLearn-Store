package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 06-06-2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.InfoModel;

import java.util.ArrayList;

public class InfoActivity extends NavigationActivity {

	private static ArrayList<String> stringList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLayoutInflater().inflate(R.layout.activity_info, frameLayout);

		ListView listView = (ListView) findViewById(R.id.list);
		stringList = InfoModel.getInstance().getListTitleList();
		getSupportActionBar().setTitle(InfoModel.getInstance().getInfoName());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stringList);

		listView.setAdapter(adapter);
		listView.setDivider(getResources().getDrawable(android.R.drawable.divider_horizontal_bright));
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent myIntent = new Intent(view.getContext(),
						InfoDetailActivity.class);
				myIntent.putExtra("detail_title", stringList.get(position));
				startActivity(myIntent);
			}
		});

	}

}