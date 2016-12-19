package com.fang.zhixiawuyou.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.adapter.DemandAdapter;
import com.fang.zhixiawuyou.adapter.InternshipAdapter;
import com.fang.zhixiawuyou.adapter.RecruitmentAdapter;
import com.fang.zhixiawuyou.base.BaseActivity;
import com.fang.zhixiawuyou.bean.Demand;
import com.fang.zhixiawuyou.bean.Internship;
import com.fang.zhixiawuyou.bean.Recruitment;
import com.fang.zhixiawuyou.view.LoadingDialog;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by Administrator on 2016/6/7.
 */
public class SearchActivity extends BaseActivity {
    private String infoType;
    private ImageButton btnBack;
    private TextView tvTitle;
    private TextView tvSearch;
    private EditText etKeywords;
    private ImageView ivTip;
    private ListView listView;
    private LoadingDialog loadingDialog;

    private RecruitmentAdapter recruitmentAdapter;
    private InternshipAdapter internshipAdapter;
    private DemandAdapter demandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
    }

    private void initView() {
        infoType = getIntent().getStringExtra("infoType");
        btnBack = (ImageButton) findViewById(R.id.header_user_btn_back);
        tvTitle = (TextView) findViewById(R.id.header_user_tv_title);
        tvSearch = (TextView) findViewById(R.id.search_tv_search);
        etKeywords = (EditText) findViewById(R.id.search_et_keywords);
        ivTip = (ImageView) findViewById(R.id.search_iv_tip);
        listView = (ListView) findViewById(R.id.search_listview);
        loadingDialog = new LoadingDialog(SearchActivity.this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("信息搜索");
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etKeywords.getText().toString())) {
                    switch (infoType) {
                        case "com.fang.zhixiawuyou.fragment.RecruitmentFragment":
                            queryRecruitmentData(etKeywords.getText().toString());
                            break;
                        case "com.fang.zhixiawuyou.fragment.InternshipFragment":
                            queryInternshipData(etKeywords.getText().toString());
                            break;
                        case "com.fang.zhixiawuyou.fragment.DemandFragment":
                            queryDemandData(etKeywords.getText().toString());
                            break;
                        default:
                            break;
                    }
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etKeywords.getWindowToken(), 0);
            }
        });
        listView.setOnItemClickListener(new mOnItemClickListener());
    }

    private class mOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
            switch (infoType) {
                case "com.fang.zhixiawuyou.fragment.RecruitmentFragment":
                    intent.putExtra("title", recruitmentAdapter.getItem(position).getCompany());
                    intent.putExtra("date", recruitmentAdapter.getItem(position).getDate());
                    intent.putExtra("image", recruitmentAdapter.getItem(position).getImage().getUrl());
                    intent.putExtra("link", recruitmentAdapter.getItem(position).getLink());
                    break;
                case "com.fang.zhixiawuyou.fragment.InternshipFragment":
                    intent.putExtra("title", internshipAdapter.getItem(position).getCompany());
                    intent.putExtra("date", internshipAdapter.getItem(position).getDate());
                    intent.putExtra("image", internshipAdapter.getItem(position).getImage().getUrl());
                    intent.putExtra("link", internshipAdapter.getItem(position).getLink());
                    break;
                case "com.fang.zhixiawuyou.fragment.DemandFragment":
                    intent.putExtra("title", demandAdapter.getItem(position).getCompany());
                    intent.putExtra("date", demandAdapter.getItem(position).getDate());
                    intent.putExtra("image", demandAdapter.getItem(position ).getImage().getUrl());
                    intent.putExtra("link", demandAdapter.getItem(position).getLink());
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }
    }

    private void queryRecruitmentData(String keywords) {
        ivTip.setVisibility(View.GONE);
        loadingDialog.show();

        BmobQuery<Recruitment> query = new BmobQuery<Recruitment>();
        query.addWhereContains("company", keywords);
        query.order("-createdAt");
        //执行查询方法
        query.findObjects(this, new FindListener<Recruitment>() {
            @Override
            public void onSuccess(List<Recruitment> object) {
                loadingDialog.dismiss();

                recruitmentAdapter = new RecruitmentAdapter(SearchActivity.this, object);
                isResultEmpty(recruitmentAdapter);
                listView.setAdapter(recruitmentAdapter);
            }

            @Override
            public void onError(int code, String msg) {
                loadingDialog.dismiss();
            }
        });
    }

    private void queryInternshipData(String keywords) {
        ivTip.setVisibility(View.GONE);
        loadingDialog.show();

        BmobQuery<Internship> query = new BmobQuery<Internship>();
        query.addWhereContains("company", keywords);
        query.order("-createdAt");
        //执行查询方法
        query.findObjects(this, new FindListener<Internship>() {
            @Override
            public void onSuccess(List<Internship> object) {
                loadingDialog.dismiss();

                internshipAdapter = new InternshipAdapter(SearchActivity.this, object);
                isResultEmpty(internshipAdapter);
                listView.setAdapter(internshipAdapter);
            }

            @Override
            public void onError(int code, String msg) {
                loadingDialog.dismiss();
            }
        });
    }

    private void queryDemandData(String keywords) {
        ivTip.setVisibility(View.GONE);
        loadingDialog.show();

        BmobQuery<Demand> query = new BmobQuery<Demand>();
        query.addWhereContains("company", keywords);
        query.order("-createdAt");
        //执行查询方法
        query.findObjects(this, new FindListener<Demand>() {
            @Override
            public void onSuccess(List<Demand> object) {
                loadingDialog.dismiss();

                demandAdapter = new DemandAdapter(SearchActivity.this, object);
                isResultEmpty(demandAdapter);
                listView.setAdapter(demandAdapter);
            }

            @Override
            public void onError(int code, String msg) {
                loadingDialog.dismiss();
            }
        });
    }

    private void isResultEmpty(BaseAdapter baseAdapter) {
        if (baseAdapter.getCount() == 0) {
            ivTip.setBackgroundResource(R.drawable.search_icon_tip2);
            ivTip.setVisibility(View.VISIBLE);
        } else {
            ivTip.setVisibility(View.GONE);
        }
    }
}
