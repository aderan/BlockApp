package com.littledream.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper{
	//定义Sqlite数据库的创建，和相关操作的分装
	private static final String DATABASE_NAME = "yoblock.db";  
    private static final int DATABASE_VERSION = 1;  
	
	public SqliteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    // TODO Auto-generated constructor stub
    }

	@Override
    public void onCreate(SQLiteDatabase db) {
	    // TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS statistics" +  
	            "(id INTEGER PRIMARY KEY AUTOINCREMENT, packageName VARCHAR, useTime INTEGER, blockTimes INTEGER, data DATE)");  
    }

	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    // TODO Auto-generated method stub
	    
    }
	
}
