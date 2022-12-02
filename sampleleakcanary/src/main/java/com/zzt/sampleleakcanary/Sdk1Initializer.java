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
public class Sdk1Initializer implements Initializer<SDK1> {


    @NonNull
    @Override
    public SDK1 create(@NonNull Context context) {
        return SDK1.getInstance();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return new ArrayList<>();
    }

}