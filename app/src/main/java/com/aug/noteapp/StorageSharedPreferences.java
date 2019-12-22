package com.aug.noteapp;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageSharedPreferences {
    // Shared preferences file name
    private static String MY_DATA_KEY = "MyData";
    private static final String IS_FIRST_TIME_LAUNCH_KEY = "IsFirstTimeLaunch";
    private static final boolean IS_FIRST_TIME_LAUNCH = true;

    private Context mContext;


    public StorageSharedPreferences(Context context) {
        this.mContext = context;
    }

    private SharedPreferences.Editor getPreferencesEditor() {
        return getSharedPreferences().edit();
    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(MY_DATA_KEY, Context.MODE_PRIVATE);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        getPreferencesEditor().putBoolean(IS_FIRST_TIME_LAUNCH_KEY, isFirstTime).commit();
    }

    public boolean isFirstTimeLaunch() {
        return getSharedPreferences().getBoolean(IS_FIRST_TIME_LAUNCH_KEY, IS_FIRST_TIME_LAUNCH);
    }


}
