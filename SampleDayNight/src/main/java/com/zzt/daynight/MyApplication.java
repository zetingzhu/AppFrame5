package com.zzt.daynight;

import android.app.Application;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

/**
 * @author: zeting
 * @date: 2021/8/19
 */
public class MyApplication extends MultiDexApplication {
    private static final String TAG = MyApplication.class.getSimpleName();

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.w(TAG, "------------ MyApplication onCreate -----------");
    }
}
