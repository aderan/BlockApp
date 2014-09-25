package com.littledream.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteHelper extends SQLiteOpenHelper{
	//定义Sqlite数据库的创建，和相关操作的分装
	private static final String DATABASE_NAME = "yoblock.db";  
    private static final int DATABASE_VERSION = 1;  
    private static SqliteHelper helper = null;
	
	private SqliteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    // TODO Auto-generated constructor stub
    }

	public static synchronized SqliteHelper getInstance(Context context)
	{
		if (helper == null)
			helper = new SqliteHelper(context);
		return helper;
	}
	
	@Override
    public void onCreate(SQLiteDatabase db) {
	    // TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS statistics" +  
	            "(id INTEGER PRIMARY KEY AUTOINCREMENT, packageName VARCHAR, useTime INTEGER, blockTimes INTEGER, date TEXT)");  
    }

	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    // TODO Auto-generated method stub
	    
    }
	
	public void updateStatistics(List<StatisticsItem> statisticses)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		db.beginTransaction();
		try{
			for (StatisticsItem item: statisticses)
			{
				ContentValues values = new ContentValues();
				values.put("packageName", item.getPackageName());
				values.put("date", item.getDate());
				values.put("useTime", item.getUseTime());
				values.put("blockTimes", item.getBlockTimes());
				//可能能更简单一点：
				Cursor cursor = db.rawQuery("select * from statistics where packageName = ? and date=?", new String[]{item.getPackageName(),item.getDate()});
				if (!cursor.moveToFirst())
				{
					db.insertOrThrow("statistics", null, values); 
					Log.d("Aderan","插入:"+item.toString());
				}
				else
				{
					int ret = db.update("statistics", values, "packageName = ? and date = ?", new String[]{item.getPackageName(),item.getDate()});
					Log.d("Aderan","Effect Rows:"+ret);
				}
			}
			db.setTransactionSuccessful();
		}finally
		{
			db.endTransaction();
		}
	}
	
	//思考使用Hash的方式,无法评估效果，先使用List，将HashCode内置于StatisticsItem，便于查找
	public List<StatisticsItem> getStatistics()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		String dateStr = Tools.getDateStrNow();
		Cursor cursor = db.rawQuery("select * from statistics where date=?", new String[]{dateStr});
		List<StatisticsItem> list = new ArrayList<StatisticsItem>();
		while (cursor.moveToNext())
		{
			Log.d("Aderan",
				"packageName:"+cursor.getString(cursor.getColumnIndex("packageName"))
				+ " date:"+cursor.getString(cursor.getColumnIndex("date"))
				+ " useTime:"+cursor.getInt(cursor.getColumnIndex("useTime"))
				+ " blockTimes:"+cursor.getInt(cursor.getColumnIndex("blockTimes"))
			);
			StatisticsItem item = new StatisticsItem(
					cursor.getString(cursor.getColumnIndex("packageName")),
					cursor.getString(cursor.getColumnIndex("date")),
					cursor.getInt(cursor.getColumnIndex("useTime")),
					cursor.getInt(cursor.getColumnIndex("blockTimes"))
					);
			list.add(item);
		}
		cursor.close();
		return list;
	}
}
