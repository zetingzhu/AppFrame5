package com.zzt.sampleleakcanary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LeakCanaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary);
    }
}