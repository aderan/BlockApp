package com.littledream.utils;

public class StatisticsItem {
	private String packageName;
	private String date;
	private int useTime;
	private int blockTimes;
	
	public StatisticsItem(String packageName,String date)
	{
		this(packageName, date, 0, 0);
	}
	
	public StatisticsItem(String packageName,String date, int useTime, int blockTimes)
	{
		this.packageName = packageName;
		this.date = date;
		this.useTime = useTime;
		this.blockTimes = blockTimes;
	}
	
	public void addUseTime(int seconds)
	{
		useTime += seconds;
	}
	
	public void addBlockTimes()
	{
		blockTimes ++;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getDate() {
		return date;
	}

	public int getUseTime() {
		return useTime;
	}

	public int getBlockTimes() {
		return blockTimes;
	}
}
