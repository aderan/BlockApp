package com.littledream.yoblock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 在这里干你想干的事（启动一个Service，Activity等），本例是启动一个定时调度程序，每30分钟启动一个Service去更新数据
    	String action = intent.getAction();
    	//使用以下命令测试：adb shell am broadcast -a android.intent.action.BOOT_COMPLETED
    	if (action.equals(Intent.ACTION_BOOT_COMPLETED))
    	{
    		Log.d("Aderan","开机启动服务");
	    	Intent i = new Intent(context, BlockAppService.class);
	    	context.startService(i);
    	}
    }
}