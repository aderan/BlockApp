package com.littledream.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class StatisticsUseTime implements Parcelable{
	public int lastDayUseTime;
	public int allUseTime;
	public int useTimeAverage;
	
	public StatisticsUseTime(){}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeInt(allUseTime);
		arg0.writeInt(useTimeAverage);
	}
	
    public static final Parcelable.Creator<StatisticsUseTime> CREATOR = new Parcelable.Creator<StatisticsUseTime>() 
    {
        public StatisticsUseTime createFromParcel(Parcel in) 
        {
        	StatisticsUseTime useTime = new StatisticsUseTime();
        	useTime.allUseTime = in.readInt();
        	useTime.useTimeAverage = in.readInt();
        	return useTime;
        }

        public StatisticsUseTime[] newArray(int size) 
        {
            return new StatisticsUseTime[size];
        }
    };
}