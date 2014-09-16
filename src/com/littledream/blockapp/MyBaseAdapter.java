package com.littledream.blockapp;

import java.util.List;

import com.littledream.utils.AppsInfo;
import com.littledream.utils.AppsInfo.AppInfoItem;
import com.littledream.utils.LocalSetting;
import com.littledream.view.ImageTextButton;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyBaseAdapter extends BaseAdapter {
	private Context mContext;
	private List<AppInfoItem> mAppsInfo;
	
	public MyBaseAdapter(Context context,List<AppsInfo.AppInfoItem> appsInfo){
		this.mAppsInfo = appsInfo;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return (mAppsInfo==null)?0:mAppsInfo.size();
	}

	@Override
	public AppsInfo.AppInfoItem getItem(int position) {
		return mAppsInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	public class ViewHolder{
		ImageTextButton button;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final AppInfoItem appInfo = (AppInfoItem)getItem(position);
		ViewHolder viewHolder = null;
		if(convertView==null){
			Log.d("MyBaseAdapter", "新建convertView,position="+position);
			convertView = new ImageTextButton(mContext);
			viewHolder = new ViewHolder();
			viewHolder.button = (ImageTextButton)convertView;
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
			Log.d("MyBaseAdapter", "旧的convertView,position="+position);
		}
		
		viewHolder.button.setText(appInfo.appName);
		viewHolder.button.setIcon(appInfo.appIcon);
		viewHolder.button.setBlock(LocalSetting.getBoolean(appInfo.packageName, false));
		
		//对ListView中button配置OnClick事件
		viewHolder.button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, 
						"点击了"+appInfo.appName, 
						Toast.LENGTH_SHORT).show();
				ImageTextButton btn = (ImageTextButton) v;
				btn.setBlock(!btn.isBlock());
				LocalSetting.setBoolean(appInfo.packageName, btn.isBlock());
			}
		});
		
		//对ListView中的每一行信息配置OnLongClick事件
		convertView.setOnLongClickListener(new OnLongClickListener(){
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(mContext, 
						"[convertView.setOnLongClickListener]点击了"+appInfo.appName, 
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		
		return convertView;
	}

}
