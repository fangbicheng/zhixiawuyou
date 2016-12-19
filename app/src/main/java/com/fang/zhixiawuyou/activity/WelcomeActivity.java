package com.fang.zhixiawuyou.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/6/3.
 */
public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Intent InterestIntent = new Intent(this, InterestActivity.class);
        final Intent MainIntent = new Intent(this, MainActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("first_pre", MODE_PRIVATE);
                Boolean isFirstIn = preferences.getBoolean("isFirstInApp", true);
                if(isFirstIn) {
                    startActivity(InterestIntent);
                } else {
                    startActivity(MainIntent);
                }

                finish();
            }
        };
        timer.schedule(task, 2000);
    }
}
