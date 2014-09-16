package com.littledream.blockapp;

import java.util.ArrayList;
import java.util.List;

import com.littledream.utils.AppsInfo;
import com.littledream.utils.AppsInfo.AppInfoItem;
import com.littledream.utils.LocalSetting;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class BlockAppService extends Service {
	private static final String LOGTAG = "Aderan_BlockAppService";
	
	private Thread mThread;
	protected boolean mFlag = true;

	private AppStartReceiver appStartReceiver;
	
	@Override
    public void onCreate() {
	    // TODO Auto-generated method stub
	    super.onCreate();
    }
	
	private class AppStartReceiver extends BroadcastReceiver 
	{
		@Override
        public void onReceive(Context context, Intent intent) {
	        // TODO Auto-generated method stub
	        Log.d ("Aderan_AppStartReceiver","Action:" + intent.getAction());
        }
	}
	
	private boolean isTopActivity(String packageName){    
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);  
		//ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		List<RunningTaskInfo>  tasksInfo = am.getRunningTasks(1);    
		if(tasksInfo.size() > 0){    
			//应用程序位于堆栈的顶层
			Log.d("Aderan", "PackageName:"+packageName + " getPackageName:"+tasksInfo.get(0).topActivity.getPackageName());
			if(packageName.equals(tasksInfo.get(0).topActivity.getPackageName())){    
				return true;    
			}    
		}    
		return false;    
	}
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	    // TODO 实现BroadcastReceiver，接受启动信息，或者监听运行状态，启动相应的Activity
		Log.d(LOGTAG , "start onStart~~~");
		final ArrayList<AppInfoItem> appList = AppsInfo.getInstance(null).getAppList();
		
		mThread = new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (mFlag){
					try {
						for (AppInfoItem appinfo: appList)
						{
							if (isTopActivity(appinfo.packageName) && LocalSetting.getBoolean(appinfo.packageName, false))
							{
								Intent i = new Intent(BlockAppService.this, HelloActivity.class);
								i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
								startActivity(i);
							}
						}
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};
		mThread.start();
		return super.onStartCommand(intent, flags, startId);
    }

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public boolean onUnbind(Intent intent) {
	    // TODO Auto-generated method stub
	    return super.onUnbind(intent);
    }

	@Override
    public void onRebind(Intent intent) {
	    // TODO Auto-generated method stub
	    super.onRebind(intent);
    }

	@Override
    public void onDestroy() {
	    // TODO Auto-generated method stub
	    super.onDestroy();
    }
}
