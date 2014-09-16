package com.littledream.blockapp;

import com.littledream.utils.LocalSetting;

import android.app.Application;

public class BlockAppApplication extends Application {

	@Override
    public void onCreate() {
	    // TODO Auto-generated method stub
		LocalSetting.init(this);
	    super.onCreate();
    }

}
