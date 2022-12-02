package com.zzt.sampleleakcanary;

import android.util.Log;

/**
 * @author: zeting
 * @date: 2021/9/15
 */
public class SDK2 {
    private static final String TAG = SDK2.class.getSimpleName();
    private static volatile SDK2 instance;

    public SDK2() {
        Log.e(TAG, "SDK2 初始化");
    }

    public static SDK2 getInstance() {
        if (instance == null) {
            synchronized (SDK2.class) {
                if (instance == null) {
                    instance = new SDK2();
                }
            }
        }
        return instance;
    }

}
