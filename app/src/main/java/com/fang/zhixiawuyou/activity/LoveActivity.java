package com.fang.zhixiawuyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fang.zhixiawuyou.MyApplication;
import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.adapter.RecruitmentAdapter;
import com.fang.zhixiawuyou.base.BaseActivity;
import com.fang.zhixiawuyou.bean.Recruitment;
import com.fang.zhixiawuyou.view.LoadingDialog;
import com.fang.zhixiawuyou.view.XListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/6/25.
 */
public class LoveActivity extends BaseActivity implements XListView.IXListViewListener {
    private ImageButton btnBack;
    private TextView tvTitle;
    private LoadingDialog loadingDialog;
    private XListView xListView;
    private RecruitmentAdapter adapter;
    private List<Recruitment> recruitmentList = new ArrayList<>();

    private int currentPage = 0;
    private static final int STATE_INIT = 1;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);

        initView();
        loadingDialog.show();
        queryData(STATE_INIT);
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.header_user_btn_back);
        tvTitle = (TextView) findViewById(R.id.header_user_tv_title);
        xListView = (XListView) findViewById(R.id.love_listview);
        loadingDialog = new LoadingDialog(LoveActivity.this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("猜你喜欢");
        xListView.setOnItemClickListener(new mOnItemClickListener());
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
    }

    private String getUserMajor() {
        MyApplication myApplication = (MyApplication) getApplication();
        return myApplication.getUserMajor();
    }

    @Override
    public void onRefresh() {
        queryData(STATE_REFRESH);
        xListView.stopRefresh();
        xListView.stopLoadMore();
    }

    @Override
    public void onLoadMore() {
        queryData(STATE_MORE);
        xListView.stopRefresh();
        xListView.stopLoadMore();
    }

    private void queryData(final int actionType) {
        BmobQuery<Recruitment> eq1 = new BmobQuery<Recruitment>();
        eq1.addWhereContains("major", "不限");
        BmobQuery<Recruitment> eq2 = new BmobQuery<Recruitment>();
        eq2.addWhereContains("major", getUserMajor());

        List<BmobQuery<Recruitment>> queries = new ArrayList<BmobQuery<Recruitment>>();
        queries.add(eq1);
        queries.add(eq2);

        BmobQuery<Recruitment> mainQuery = new BmobQuery<Recruitment>();
        mainQuery.or(queries);
        mainQuery.order("-createdAt");

        if(actionType == STATE_INIT || actionType == STATE_REFRESH) {
            currentPage = 0;
        } else if(actionType == STATE_MORE) {
            currentPage++;
            mainQuery.setSkip(currentPage * 10);
        }
        mainQuery.setLimit(10);

        //执行查询方法
        mainQuery.findObjects(LoveActivity.this, new FindListener<Recruitment>() {
            @Override
            public void onSuccess(List<Recruitment> object) {
                loadingDialog.dismiss();

                if(actionType == STATE_INIT || actionType == STATE_REFRESH) {
                    recruitmentList.clear();
                    // 将本次查询的数据添加到recruitmentList中
                    for(Recruitment recruitment : object) {
                        recruitmentList.add(recruitment);
                    }
                    adapter = new RecruitmentAdapter(LoveActivity.this, recruitmentList);
                    xListView.setAdapter(adapter);
                } else if(actionType == STATE_MORE) {
                    for(Recruitment recruitment : object) {
                        recruitmentList.add(recruitment);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                //toast("查询失败："+msg);
            }
        });
    }

    private class mOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(LoveActivity.this, DetailsActivity.class);
            intent.putExtra("title", adapter.getItem(position - 1).getCompany());
            intent.putExtra("date", adapter.getItem(position - 1).getDate());
            intent.putExtra("image", adapter.getItem(position - 1).getImage().getUrl());
            intent.putExtra("link", adapter.getItem(position - 1).getLink());
            startActivity(intent);
        }
    }
}
