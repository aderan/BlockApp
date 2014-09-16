package com.littledream.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;


public class AppsInfo {
	ArrayList<AppInfoItem> appList = new ArrayList(); //用来存储获取的应用信息数据

	public ArrayList<AppInfoItem> getAppList() {
		return appList;
	}

	static AppsInfo appsInfo;
	
	public static AppsInfo getInstance(Activity activity)
	{
		if (appsInfo == null)
			appsInfo = new AppsInfo(activity);
		return appsInfo;
	}
	
	private AppsInfo(Activity activity)
	{
		PackageManager pm = activity.getPackageManager();
		List<PackageInfo> packages = pm.getInstalledPackages(0);
		for(int i=0;i<packages.size();i++)
		{
				PackageInfo packageInfo = packages.get(i); 
				AppInfoItem tmpInfo =new AppInfoItem(); 
				tmpInfo.appName = packageInfo.applicationInfo.loadLabel(pm).toString(); 
				tmpInfo.packageName = packageInfo.packageName; 
				tmpInfo.versionName = packageInfo.versionName; 
				tmpInfo.versionCode = packageInfo.versionCode; 
				tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(pm);
				if ((packageInfo.applicationInfo.flags & packageInfo.applicationInfo.FLAG_SYSTEM) <= 0
						&& !packageInfo.packageName.equals("com.littledream.blockapp")) {  
					appList.add(tmpInfo);
				}
		}
	}
	
	public class AppInfoItem {
	    public String appName="";
	    public String packageName="";
	    public String versionName="";
	    public int versionCode=0;
	    public Drawable appIcon=null;
	    
	    public String toString()
	    {
			return "Name:"+ appName +
				   " PackageName:"+packageName+
				   " VersionName:"+versionName+
				   " VersionCode:"+versionCode;
	    }
	}

}