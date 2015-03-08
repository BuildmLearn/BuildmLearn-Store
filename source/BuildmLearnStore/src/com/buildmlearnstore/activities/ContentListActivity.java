package com.buildmlearnstore.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.buildmlearnstore.utils.Constants;
import com.buildmlearnstore.utils.Utility;

public class ContentListActivity extends SherlockActivity implements
		OnItemClickListener {

	private ListView mLv_FileList;
	private int option;
	private ArrayList<String> mFileNameList;
	private ArrayAdapter<String> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		option = getIntent().getIntExtra("position", 0);

		mLv_FileList = (ListView) findViewById(R.id.list);
		getFileList();
	}

	private void getFileList() {
		switch (option) {
		case 0:
			mFileNameList = Utility.listFiles(getApplicationContext(), "info");
			break;
		case 1:
			mFileNameList = Utility.listFiles(getApplicationContext(),
					"flashcard");
			break;
		case 2:
			mFileNameList = Utility.listFiles(getApplicationContext(), "quiz");
			break;
		case 3:
			mFileNameList = Utility.listFiles(getApplicationContext(),
					"spellings");
			break;

		}
		mAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, mFileNameList);
		mLv_FileList.setAdapter(mAdapter);
		mLv_FileList.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		String fileName = null;

		switch (option) {
		case 0:
			fileName = "info/" + mFileNameList.get(position)+".txt";

			break;
		case 1:

			fileName = "flashcard/" + mFileNameList.get(position);
			break;
		case 2:

			fileName = "quiz/" + mFileNameList.get(position)+".txt";
			break;
		case 3:

			fileName = "spellings/" + mFileNameList.get(position)+".txt";
			break;

		}
		Intent infotemplate = new Intent(ContentListActivity.this,
				StartActivity.class);
		infotemplate.putExtra(Constants.FILENAME, fileName);
		infotemplate.putExtra(Constants.OPTION, option);
		startActivity(infotemplate);

	}

}
