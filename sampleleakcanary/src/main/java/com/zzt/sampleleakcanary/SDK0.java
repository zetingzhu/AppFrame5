package com.zzt.sampleleakcanary;

import android.util.Log;

/**
 * @author: zeting
 * @date: 2021/9/15
 */
public class SDK0 {
    private static final String TAG = SDK0.class.getSimpleName();
    private static volatile SDK0 instance;

    public SDK0() {
        Log.i(TAG, "SDK0 初始化");
    }

    public static SDK0 getInstance() {
        if (instance == null) {
            synchronized (SDK0.class) {
                if (instance == null) {
                    instance = new SDK0();
                }
            }
        }
        return instance;
    }

}
