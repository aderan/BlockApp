package com.littledream.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
	private static final String LOGTAG = "SqliteHelper";
	//定义Sqlite数据库的创建，和相关操作的分装
	private static final String DATABASE_NAME = "yoblock.db";  
    private static final int DATABASE_VERSION = 1;  
    private static SqliteHelper helper = null;
    //TODO：基本使用静态，需要思考如何处理该类的类变量和对象变量
	private static Context mContext = null;
	
	private SqliteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    // TODO Auto-generated constructor stub
    }

	public static synchronized SqliteHelper getInstance(Context context)
	{
		mContext  = context;
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
				//TODO:可能能更简单一点
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
	
	//TODO:思考使用Hash的方式,无法评估效果，先使用List，将HashCode内置于StatisticsItem，便于查找
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
	
	private final static String STATISTICS_FILENAME = "lastdayStatistics";
	//TODO:做排序，出于Acitvity加载数据的问题，将排序信息存储在文件，
	public void saveLastDayStatistics()
	{
		DebugHelper.timerStart();
		String sql_statistics = "select PackageName,sum(UseTime) as sumOfTime "
							  + "from statistics "
							  + "group by PackageName "
							  + "Order by sumOfTime desc";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql_statistics, null);
		DebugHelper.showCostTime("End Cursor");
		FileOutputStream fos;
		try {
			fos = mContext.openFileOutput(STATISTICS_FILENAME, Context.MODE_PRIVATE);
			while(cursor.moveToNext())
			{
				String strline = cursor.getString(cursor.getColumnIndex("packageName")) 
							+ ":" +  cursor.getInt(cursor.getColumnIndex("sumOfTime")) + "\n";
				fos.write(strline.getBytes());
			}
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DebugHelper.showCostTime("saveLastDayStatistics");
		cursor.close();
	}
	
	//使用数据库排序，放弃使用Map
	public Map<String,Integer> loadLastDayStatistics()
	{
		Map<String,Integer> map= new HashMap<String,Integer>();
		FileReader fr;
		try {
			fr = new FileReader(new File(mContext.getFilesDir(), STATISTICS_FILENAME));
			BufferedReader br = new BufferedReader(fr);
			String strline = null;
			while ((strline = br.readLine()) != null)
			{
				String strs[] = strline.split(":");
				if (strs.length == 2)
				{
					Log.d(LOGTAG, "PackageName:"+strs[0]
								+" sumOfTime:"+strs[1]);
					map.put(strs[0], Integer.valueOf(strs[1]));
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
//	public List<String> loadLastDayStatistics()
//	{
//		List<String> list= new ArrayList<String>();
//		FileReader fr;
//		try {
//			fr = new FileReader(new File(mContext.getFilesDir(), STATISTICS_FILENAME));
//			BufferedReader br = new BufferedReader(fr);
//			String strline = null;
//			while ((strline = br.readLine()) != null)
//			{
//				String strs[] = strline.split(":");
//				if (strs.length == 2)
//				{
//					Log.d(LOGTAG, "PackageName:"+strs[0]
//								+" sumOfTime:"+strs[1]);
//					list.add(strs[0]);
//				}
//			}
//			br.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//	}
}
