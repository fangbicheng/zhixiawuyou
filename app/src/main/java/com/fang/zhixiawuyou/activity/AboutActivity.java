package com.fang.zhixiawuyou.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.base.BaseActivity;

/**
 * Created by Administrator on 2016/6/1.
 */
public class AboutActivity extends BaseActivity {
    private ImageButton btnBack;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.header_user_btn_back);
        tvTitle = (TextView) findViewById(R.id.header_user_tv_title);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("关于我们");
    }
}
