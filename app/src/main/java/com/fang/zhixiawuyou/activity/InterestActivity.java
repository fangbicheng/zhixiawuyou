package com.fang.zhixiawuyou.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fang.zhixiawuyou.MyApplication;
import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.base.BaseActivity;

/**
 * Created by Administrator on 2016/6/25.
 */
public class InterestActivity extends BaseActivity {
    private EditText etMajor;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        initView();
        initData();
    }

    private void initView() {
        etMajor = (EditText) findViewById(R.id.interest_et_major);
        btnStart = (Button) findViewById(R.id.interest_btn_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("first_pre", MODE_PRIVATE).edit();
                editor.putBoolean("isFirstInApp", false);
                editor.commit();

                Intent intent = new Intent(InterestActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {
        MyApplication myApplication = (MyApplication) getApplication();
        myApplication.setUserMajor(etMajor.getText().toString());
    }
}
