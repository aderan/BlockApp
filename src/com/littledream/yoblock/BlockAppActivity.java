package com.littledream.yoblock;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.littledream.utils.AppsInfo;
import com.littledream.utils.AppsInfo.AppInfoItem;
import com.littledream.utils.DebugHelper;
import com.littledream.yoblock.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BlockAppActivity extends ActionBarActivity {

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DebugHelper.timerStart();
		AppsInfo appsinfo = AppsInfo.getInstance(this);
		DebugHelper.showCostTime("step 1");
		List applist = appsinfo.getAppList();
		Collections.sort(applist, new Comparator<AppInfoItem>(){

			@Override
			public int compare(AppInfoItem arg0, AppInfoItem arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
			
		});
		BaseAdapter adapter = new MyBaseAdapter(this, applist);
		DebugHelper.showCostTime("step 2");
		mListView = new ListView(this);
		mListView.setAdapter(adapter);
		DebugHelper.showCostTime("step 3");
		setContentView(mListView);
		DebugHelper.showCostTime("step 4");
		//启用service
		Intent intent = new Intent(this,BlockAppService.class);  
		startService(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.block_app, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}
