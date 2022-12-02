package com.qmuiteam.qmui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.qmuiteam.qmui.skin.R;

public class MainActivity extends AppCompatActivity {
    private int themeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        themeType = getSharedPreferences("theme", MODE_PRIVATE).getInt("themeType", 0);
        if (themeType == 0) {
            setTheme(R.style.app_skin_white);
        } else {
            setTheme(R.style.app_skin_dark);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_night).setOnClickListener(v -> {
//            setTheme(R.style.app_skin_dark);
            getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("themeType", 0).commit();
            recreate();
        });

        findViewById(R.id.tv_light).setOnClickListener(v -> {
//            setTheme(R.style.app_skin_white);
            getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("themeType", 1).commit();
            recreate();
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}