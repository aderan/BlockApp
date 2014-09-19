package com.littledream.yoblock;

import java.util.List;

import com.littledream.utils.AppsInfo;
import com.littledream.utils.AppsInfo.AppInfoItem;
import com.littledream.utils.LocalSetting;
import com.littledream.view.ImageTextButton;
import com.littledream.yoblock.R;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
		ImageView img;
		TextView appname;
		CheckBox choose;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final AppInfoItem appInfo = (AppInfoItem)getItem(position);
		ViewHolder viewHolder = null;
		if(convertView==null){
			Log.d("MyBaseAdapter", "新建convertView,position="+position);
			convertView = View.inflate(mContext,R.layout.app_item, null);
			viewHolder = new ViewHolder();
			viewHolder.img = (ImageView)convertView.findViewById(R.id.appitem_img);
			viewHolder.appname = (TextView)convertView.findViewById(R.id.appitem_name);
			viewHolder.choose = (CheckBox)convertView.findViewById(R.id.appitem_choose);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
			Log.d("MyBaseAdapter", "旧的convertView,position="+position);
		}
		
		viewHolder.appname.setText(appInfo.appName);
		viewHolder.img.setImageDrawable(appInfo.appIcon);
		
		//解决checkBox错乱问题
		viewHolder.choose.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
	            // TODO Auto-generated method stub
				Log.d("Aderan","isChecked:"+isChecked + " appInfo.packageName:"+appInfo.packageName);
				LocalSetting.setBoolean(appInfo.packageName, isChecked);
            }});
		
		viewHolder.choose.setChecked(LocalSetting.getBoolean(appInfo.packageName, false));
		
//		//对ListView中button配置OnClick事件
//		viewHolder.choose.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
////				Toast.makeText(mContext, 
////						"点击了"+appInfo.appName, 
////						Toast.LENGTH_SHORT).show();
//				//ShowChangeOnClick
//				CheckBox cbox = (CheckBox)v;
//				cbox.setChecked(!cbox.isChecked());
//				LocalSetting.setBoolean(appInfo.packageName, cbox.isChecked());
//			}
//		});
		
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
