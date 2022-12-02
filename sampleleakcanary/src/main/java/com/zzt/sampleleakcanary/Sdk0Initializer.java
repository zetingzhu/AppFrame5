package com.zzt.sampleleakcanary;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zeting
 * @date: 2021/9/15
 */
public class Sdk0Initializer implements Initializer<SDK0> {


    @NonNull
    @Override
    public SDK0 create(@NonNull Context context) {
        return SDK0.getInstance();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        List<Class<? extends Initializer<?>>> dependencies = new ArrayList<>();
        // 初始化 0 需要依赖 1
        dependencies.add(Sdk1Initializer.class);
        return dependencies;
    }
}