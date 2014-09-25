package com.littledream.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class Tools {
	
	public static String getDateStrNow()
	{
//		Date date = new Date();
//		String dateStr = (1900+date.getYear())+"-"+date.getMonth()+"-"+date.getDate();
		Calendar cal = Calendar.getInstance();
		String dateStr = (cal.get(Calendar.YEAR))
				+"-"+(cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DAY_OF_MONTH));
		//可以使用SimpleDateFormat
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Log.d("Aderan", "df.Format:"+df.format(cal.getTime()));
//		Log.d("Aderan", "dateStr:"+dateStr);
		return dateStr;
	}
}
