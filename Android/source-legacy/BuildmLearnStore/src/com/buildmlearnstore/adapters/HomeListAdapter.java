package com.buildmlearnstore.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildmlearnstore.activities.R;
import com.buildmlearnstore.model.AppModel;

@SuppressWarnings("unused")
public class HomeListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<AppModel> mList;

	public HomeListAdapter(Context context) {
		mList = new ArrayList<AppModel>();
		mContext = context;
	}

	public void setList(ArrayList<AppModel> list) {
		mList = list;
		this.notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.home_list, parent,false);

			holder = new ViewHolder();
			holder.mIv_List_Icon = (ImageView) convertView
					.findViewById(R.id.iv_list_icon);
			holder.mTv_List_Content = (TextView) convertView
					.findViewById(R.id.tv_list_content);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mIv_List_Icon.setTag(R.id.iv_list_icon);
		holder.mIv_List_Icon.setTag(R.id.tv_list_content);

		holder.mIv_List_Icon.setImageResource(mList.get(position)
				.getAppImageId());
		holder.mTv_List_Content.setText(mList.get(position).getAppName());

		return convertView;
	}

	public class ViewHolder {
		private TextView mTv_List_Content;
		private ImageView mIv_List_Icon;
	}

}
