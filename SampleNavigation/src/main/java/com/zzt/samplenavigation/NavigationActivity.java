package com.zzt.samplenavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        initEventBus();
    }

    private void initEventBus() {
//        XEventBus.post(EventName.REFRESH_HOME_LIST, "领现金页面通知首页刷新数据")
//        XEventBus.observe(viewLifecycleOwner, EventName.REFRESH_HOME_LIST) { message: String ->
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//        }

    }
}