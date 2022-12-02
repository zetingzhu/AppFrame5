package com.zzt.sampleleakcanary;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;
import androidx.work.Configuration;
import androidx.work.WorkerParameters;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zeting
 * @date: 2021/9/15
 */
public class Sdk2Initializer implements Initializer<SDK2> {


    @NonNull
    @Override
    public SDK2 create(@NonNull Context context) {
        return SDK2.getInstance();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return new ArrayList<>();
    }


}