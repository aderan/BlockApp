package com.littledream.utils;

import android.util.Log;

public class DebugHelper {
	private static long timerStart;
	private static long timeCost;
	
	public static void timerStart()
	{
		timerStart = System.currentTimeMillis();
	}
	
	public static void timerEnd()
	{
		if (timerStart != 0)
		{
			timeCost = System.currentTimeMillis() - timerStart;
			timerStart = 0;
		}
	}
	
	private static long getCostTime()
	{
		return timerStart == 0 ? 0 :System.currentTimeMillis() - timerStart;
	}
	
	public static void showCostTime(String msg)
	{
		Log.d("DebugHelper", msg + " CostTime:" + getCostTime());
	}
}
