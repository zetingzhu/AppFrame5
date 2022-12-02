package com.zzt.sampleleakcanary;

import android.util.Log;

/**
 * @author: zeting
 * @date: 2021/9/15
 */
public class SDK1 {
    private static final String TAG = SDK1.class.getSimpleName();
    private static volatile SDK1 instance;

    public SDK1() {
        Log.w(TAG, "SDK1 初始化");
    }

    public static SDK1 getInstance() {
        if (instance == null) {
            synchronized (SDK1.class) {
                if (instance == null) {
                    instance = new SDK1();
                }
            }
        }
        return instance;
    }

}
