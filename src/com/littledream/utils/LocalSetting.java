package com.littledream.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LocalSetting {
	public static SharedPreferences setting = null ;
	private static final String SETTINGNAME = "LocalSetting";
	
	public static void init(Context context)
	{
		if (setting == null)
			setting = context.getSharedPreferences(SETTINGNAME, Context.MODE_PRIVATE);
	}
	
	public static void setBoolean(String key, boolean value)
	{
		Editor editor = setting.edit();//获取编辑器
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
	    // TODO Auto-generated method stub
		return setting.getBoolean(key, defaultValue);
    }
}
