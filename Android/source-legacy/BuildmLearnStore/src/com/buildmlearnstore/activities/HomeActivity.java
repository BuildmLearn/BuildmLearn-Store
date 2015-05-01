package com.buildmlearnstore.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.buildmlearnstore.adapters.HomeListAdapter;
import com.buildmlearnstore.model.AppModel;

public class HomeActivity extends SherlockActivity implements OnItemClickListener{

	private ListView mLv_Home;
	private HomeListAdapter mListAdapter;
	private ArrayList<AppModel> mAppList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAppList=new ArrayList<AppModel>();
		mAppList.add(new AppModel("InfoTemplate",R.drawable.ic_info));
		mAppList.add(new AppModel("FlashCardTemplate",R.drawable.ic_flashcards));
		mAppList.add(new AppModel("QuizTemplate",R.drawable.ic_quiz));
		mAppList.add(new AppModel("SpellingsTemplate",R.drawable.ic_spellings));
		
		
		mLv_Home = (ListView) findViewById(R.id.list);
		mListAdapter = new HomeListAdapter(getApplicationContext());
		mLv_Home.setAdapter(mListAdapter);
		mListAdapter.setList(mAppList);
		mLv_Home.setOnItemClickListener(this);

	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	Intent contentIntent=new Intent(HomeActivity.this,ContentListActivity.class);
	contentIntent.putExtra("position", position);
	startActivity(contentIntent);
	}

}
