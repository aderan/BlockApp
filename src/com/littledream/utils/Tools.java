package com.littledream.utils;

import java.util.Date;

import android.util.Log;

public class Tools {
	
	public static String getDateStrNow()
	{
		Date date = new Date();
		String dateStr = (1900+date.getYear())+"-"+date.getMonth()+"-"+date.getDay();
		//Log.d("Aderan", "dateStr:"+dateStr);
		
		return dateStr;
	}
}
