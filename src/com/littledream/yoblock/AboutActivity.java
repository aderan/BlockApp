package com.littledream.yoblock;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		TextView tv = (TextView)this.findViewById(R.id.about_textview);
		//TODO:可以使用配置文件或者其他方式设置
		tv.setText("\n"
				+  "开发者\n"
				+  "    littledream\n"
				+  "意见或建议\n"
				+  "    fenglibinjie@gmail.com\n"
				+  "该项目源码托管到github\n"
				+  "    https://github.com/aderan/BlockApp\n"
				+  "需要的朋友可随意使用\n");
	
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
