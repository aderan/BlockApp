package com.littledream.yoblock;

import com.littledream.utils.LocalSetting;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.setting);
        
        CheckBoxPreference yoblock_enable = (CheckBoxPreference)this.findPreference("yoblock_enable");
        yoblock_enable.setOnPreferenceClickListener(new OnPreferenceClickListener(){

			@Override
            public boolean onPreferenceClick(Preference preference) {
	            // TODO Auto-generated method stub
	            return false;
            }
        	
        });
        
        yoblock_enable.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

			@Override
            public boolean onPreferenceChange(Preference preference,
                    Object newValue) {
	            // TODO Auto-generated method stub
	            LocalSetting.setBoolean("yoblock_enable", (boolean) newValue);
	            //Toast.makeText(SettingActivity.this, "yoblock_enable改变的值为" +  (Boolean)newValue, Toast.LENGTH_LONG).show();  
				return true;
            }
        	
        });
        
        CheckBoxPreference superbg_enable = (CheckBoxPreference)this.findPreference("superbg_enable");
        
        superbg_enable.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			@Override
            public boolean onPreferenceChange(Preference preference,
                    Object newValue) {
	            // TODO Auto-generated method stub
	            LocalSetting.setBoolean("superbg_enable", (boolean) newValue);
	            Toast.makeText(SettingActivity.this, "superbg_enable改变的值为" +  (Boolean)newValue, Toast.LENGTH_LONG).show();  
				return true;
            }
        	
        });
        
    }
}
