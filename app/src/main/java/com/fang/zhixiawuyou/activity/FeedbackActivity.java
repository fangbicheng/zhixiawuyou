package com.fang.zhixiawuyou.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.base.BaseActivity;

/**
 * Created by Administrator on 2016/6/1.
 */
public class FeedbackActivity extends BaseActivity {
    private ImageButton btnBack;
    private TextView tvTitle;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initView();
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.header_user_btn_back);
        tvTitle = (TextView) findViewById(R.id.header_user_tv_title);
        btnSubmit = (Button) findViewById(R.id.feedback_btn_submit);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("意见反馈");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FeedbackActivity.this, "谢谢您宝贵的意见", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
