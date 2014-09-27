package com.littledream.yoblock;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsFragment extends Fragment {

	@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    
	    //this.addPreferencesFromResource(R.xml.setting);
    }
	
}
