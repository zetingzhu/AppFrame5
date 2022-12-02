package com.zzt.daynight.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zzt.daynight.R;
import com.zzt.daynight.dialog.MyTestDialog;

public class ActivityTestV2 extends AppCompatActivity {
    Button button, button1, button_v5;

    public static void start(Context c, int type) {
        Intent intent = new Intent(c, ActivityTestV2.class);
        intent.putExtra("type", type);
        c.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_v2);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);
        button_v5 = findViewById(R.id.button_v5);
        button_v5.setOnClickListener(v -> {
            MyTestDialog myTextDialog = new MyTestDialog(ActivityTestV2.this);
            myTextDialog.setButtonClick1(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            myTextDialog.setButtonClick2(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            myTextDialog.setCancelable(true);
            myTextDialog.show();
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //applyDayNight
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
        ActivityTestV2.start(ActivityTestV2.this, 1);
        finish();
    }
}