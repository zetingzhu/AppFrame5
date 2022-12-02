package com.zzt.daynight.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zzt.daynight.R;
import com.zzt.daynight.dialog.MyTestDialog;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    TextView tv_click;
    Button button, button1, button_v3, button_v4, button_v5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "------------onCreate-----------" + Locale.getDefault().getLanguage());

        int appBaseTheme = R.style.AppBaseTheme;

        Log.w(TAG, "------------onCreate-----------appBaseTheme：" + appBaseTheme);
//        setTheme(appBaseTheme);

        setContentView(R.layout.activity_main);
        tv_click = findViewById(R.id.tv_click);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);
        button_v3 = findViewById(R.id.button_v3);
        button_v4 = findViewById(R.id.button_v4);
        button_v5 = findViewById(R.id.button_v5);

        showNightState();

        Resources.Theme theme = getTheme();
        Log.w(TAG, "------------onCreate-----------theme：" + theme);

        int color = getResources().getColor(R.color.text_color);
        Log.w(TAG, "------------onCreate-----------color：" + color);
        tv_click.setTextColor(color);

        button_v5.setOnClickListener(v -> {
            MyTestDialog myTextDialog = new MyTestDialog(MainActivity.this);
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

        tv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED);
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    // 关闭暗黑模式
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    // 开启暗黑模式
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                showNightState();
            }
        });

        /**

         AppCompatDelegate.MODE_NIGHT_NO  ：白天模式
         AppCompatDelegate.MODE_NIGHT_YES  ：夜间模式
         AppCompatDelegate.MODE_NIGHT_AUTO  ：根据当前时间自动切换日夜间模式（比如北京时间22：00会自动切换为夜间）
         AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM ：跟随系统

         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //applyDayNight
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                showNightState();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                showNightState();
            }
        });

        button_v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, ActivityTestV2.class));
                ActivityTestV2.start(MainActivity.this, 0);
            }
        });
        button_v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                showNightState();
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if ((newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            Log.d(TAG, "-------- onConfigurationChanged  深色");

        } else {
            Log.d(TAG, "-------- onConfigurationChanged  亮色");

        }
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);//去掉Activity切换间的动画
        finish();
    }

    public void showNightState() {
        StringBuffer stringBuffer = new StringBuffer();

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            stringBuffer.append(" -:当前 Application 是暗黑模式");
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            stringBuffer.append(" -:当前 Application 是亮色模式");
        } else {
            stringBuffer.append(" -:当前 Application 其他模式：" + AppCompatDelegate.getDefaultNightMode());
        }
        stringBuffer.append("\n");
        int localNightMode = getDelegate().getLocalNightMode();
        if (localNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            stringBuffer.append(" -:当前 Context 是暗黑模式");
        } else if (localNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
            stringBuffer.append(" -:当前 Context 是亮色模式");
        } else {
            stringBuffer.append(" -:当前 Context 其他模式：" + localNightMode);
        }

        stringBuffer.append("\n");
        /**
         * Bit mask of the ui mode. Currently there are two fields:
         * The UI_MODE_TYPE_MASK bits define the overall ui mode of the device.
         * They may be one of
         * UI_MODE_TYPE_UNDEFINED, UI_MODE_TYPE_NORMAL, UI_MODE_TYPE_DESK, UI_MODE_TYPE_CAR, UI_MODE_TYPE_TELEVISION, UI_MODE_TYPE_APPLIANCE, UI_MODE_TYPE_WATCH, or UI_MODE_TYPE_VR_HEADSET.
         * The UI_MODE_NIGHT_MASK defines whether the screen is in a special mode.
         * They may be one of UI_MODE_NIGHT_UNDEFINED, UI_MODE_NIGHT_NO or UI_MODE_NIGHT_YES.
         */
        boolean a = (getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_YES) != Configuration.UI_MODE_TYPE_UNDEFINED;
        stringBuffer.append(" -:当前 系统 模式-暗--" + a);
        stringBuffer.append("\n");
        boolean l = (getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_NO) != Configuration.UI_MODE_TYPE_UNDEFINED;
        stringBuffer.append(" -:当前 系统 模式-亮--" + l);
        tv_click.setText(stringBuffer);
    }

    @Override
    public void recreate() {
        super.recreate();
        Log.w(TAG, "------------recreate-----------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "------------onRestart-----------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "------------onStart-----------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "------------onResume-----------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "------------onPause-----------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "------------onStop-----------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "------------onDestroy-----------");
    }
}