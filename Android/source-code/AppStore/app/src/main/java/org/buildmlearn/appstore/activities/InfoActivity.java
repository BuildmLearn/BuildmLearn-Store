package org.buildmlearn.appstore.activities;

/**
 * Created by Srujan Jha on 06-06-2015.
 */

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.buildmlearn.appstore.R;
import org.buildmlearn.appstore.models.InfoModel;

public class InfoActivity extends AppCompatActivity {

	private static ArrayList<String> stringList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		ListView listView = (ListView) findViewById(R.id.list);
		stringList = InfoModel.getInstance().getListTitleList();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stringList);

		listView.setAdapter(adapter);

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