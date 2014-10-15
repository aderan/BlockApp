package com.littledream.yoblock;

import java.text.DecimalFormat;
import com.littledream.utils.StatisticsUseTime;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

@SuppressLint("NewApi")
public class StatisticsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_statistics);
		Intent i = this.getIntent();
		String appName = i.getStringExtra("appName");
		ActionBar actionBar = this.getActionBar(); 
		actionBar.setTitle(appName);
		
		StatisticsUseTime useTime = (StatisticsUseTime)i.getParcelableExtra("useTime");
		TextView tv = (TextView)this.findViewById(R.id.statistics_textview);
		
		DecimalFormat df=new DecimalFormat("0.00");
		tv.setText("\n"
				+ "使用总时\n"
				+ "    "+df.format((double)(useTime.allUseTime)/3600/1000) + "小时\n"
				+ "每日平均时间\n" 
				+ "    " + df.format((double)(useTime.useTimeAverage)/60/1000) + "分钟"
				);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
