package com.littledream.yoblock;

import java.util.ArrayList;
import java.util.List;

import com.littledream.utils.AppsInfo;
import com.littledream.utils.HashHelper;
import com.littledream.utils.SqliteHelper;
import com.littledream.utils.Tools;
import com.littledream.utils.AppsInfo.AppInfoItem;
import com.littledream.utils.LocalSetting;
import com.littledream.utils.StatisticsItem;

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
			//Log.d("Aderan", "PackageName:"+packageName + " getPackageName:"+tasksInfo.get(0).topActivity.getPackageName());
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
		final ArrayList<AppInfoItem> appList = AppsInfo.getInstance(this).getAppList();
		
		final SqliteHelper dbHelper = SqliteHelper.getInstance(this);
		dbHelper.saveLastDayStatistics();
		
		final List<StatisticsItem> statisticsItems =  dbHelper.getStatistics();
		mThread = new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				long timer = System.currentTimeMillis();
				while (mFlag){
					try {
						if (System.currentTimeMillis()-timer > MainConfig.Interval_SaveData)
						{
							Log.d(LOGTAG,"保存数据");
							for(StatisticsItem item:statisticsItems){
								Log.d(LOGTAG,item.toString());
							}
							dbHelper.updateStatistics(statisticsItems);
							timer = System.currentTimeMillis();
						}
						
						for (AppInfoItem appinfo: appList)
						{
							boolean isTop = isTopActivity(appinfo.packageName);
							//统计相关代码
							if (isTop)
							{
								int hashKey = HashHelper.BKDRHash(appinfo.packageName + Tools.getDateStrNow());
								boolean updated = false;
								StatisticsItem tmpItem = null;
								for (StatisticsItem item: statisticsItems)
								{
									if (item.getHashKey() == hashKey)
									{
										if (!item.getPackageName().equals(appinfo.packageName))
										{
											Log.w(LOGTAG,"HashKey相同："   +
													item.getPackageName() +
													"vs" +
													appinfo.packageName);
										}
										//item.addBlockTimes();
										item.addUseTime(MainConfig.Interval_CheckTop);
										tmpItem = item;
										updated = true;
									}
								}
								//未在列表内，添加入列表
								if (!updated)
								{
									Log.d(LOGTAG,"新建统计项，加入列表");
									StatisticsItem item = new StatisticsItem(
											appinfo.packageName,
											Tools.getDateStrNow(),
											MainConfig.Interval_CheckTop, 0);
									statisticsItems.add(item);
									tmpItem = item;
								}
								
								if (LocalSetting.getBoolean("yoblock_enable", true)
										&& LocalSetting.getBoolean(appinfo.packageName, false))
								{
									tmpItem.addBlockTimes();
									Intent i = new Intent(BlockAppService.this, NotifyActivity.class);
									i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
									startActivity(i);
								}
							}
						}
						sleep(MainConfig.Interval_CheckTop);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};
		mThread.start();
		//修复从任务列表移除之后service重启的问题
		return LocalSetting.getBoolean("superbg_enable", false)?super.onStartCommand(intent, flags, startId):START_NOT_STICKY;
    } 

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(LOGTAG , "start onBind~~~");
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
		Log.d(LOGTAG , "start onDestroy~~~");
		mFlag = false;
	    super.onDestroy();
    }
}
