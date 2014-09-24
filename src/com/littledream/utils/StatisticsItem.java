package com.littledream.utils;

public class StatisticsItem {
	private String packageName;
	private String date;
	private int useTime;
	private int blockTimes;
	
	private int hashKey;
	
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
		this.hashKey = HashHelper.BKDRHash(packageName + date);
	}
	
	public int getHashKey()
	{
		return hashKey;
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

	@Override
	public String toString() {
		return "StatisticsItem [packageName=" + packageName + ", date=" + date
				+ ", useTime=" + useTime + ", blockTimes=" + blockTimes
				+ ", hashKey=" + hashKey + "]";
	}
	
}
