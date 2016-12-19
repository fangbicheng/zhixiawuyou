package com.fang.zhixiawuyou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.activity.DetailsActivity;
import com.fang.zhixiawuyou.activity.SearchActivity;
import com.fang.zhixiawuyou.adapter.InternshipAdapter;
import com.fang.zhixiawuyou.bean.Internship;
import com.fang.zhixiawuyou.dao.ItemDao;
import com.fang.zhixiawuyou.util.NetUtil;
import com.fang.zhixiawuyou.util.TransferUtil;
import com.fang.zhixiawuyou.view.XListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/5/31.
 */
public class InternshipFragment extends Fragment implements XListView.IXListViewListener {
    private TextView tvTitle;
    private ImageButton btnSearch;
    private XListView xListView;

    private InternshipAdapter adapter;
    private List<Internship> internshipList = new ArrayList<>();
    private ItemDao itemDao;

    private int currentPage = 0;
    private static final int STATE_INIT = 1;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_internship, container, false);

        initView(view);
        itemDao = new ItemDao(getActivity());
        queryData(STATE_INIT);
        return view;
    }

    private void initView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.header_main_tv_title);
        btnSearch = (ImageButton) view.findViewById(R.id.header_main_btn_search);
        xListView = (XListView) view.findViewById(R.id.internship_listview);

        tvTitle.setText("实习信息");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("infoType", InternshipFragment.class.getName());
                startActivity(intent);
            }
        });

        xListView.setOnItemClickListener(new mOnItemClickListener());
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
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
        if (NetUtil.isNetworkConnected(getActivity())) {
            BmobQuery<Internship> query = new BmobQuery<Internship>();
            query.order("-createdAt");

            if(actionType == STATE_INIT || actionType == STATE_REFRESH) {
                currentPage = 0;
            } else if(actionType == STATE_MORE) {
                currentPage++;
                query.setSkip(currentPage * 10);
            }
            query.setLimit(10);

            //执行查询方法
            query.findObjects(getActivity(), new FindListener<Internship>() {
                @Override
                public void onSuccess(List<Internship> object) {
                    if(actionType == STATE_INIT || actionType == STATE_REFRESH) {
                        internshipList.clear();
                        for(Internship internship : object) {
                            internshipList.add(internship);
                        }

                        itemDao.deleteItem("internship");
                        itemDao.addItem(TransferUtil.internshipToItem(internshipList));

                        adapter = new InternshipAdapter(getActivity(), internshipList);
                        xListView.setAdapter(adapter);
                    } else if(actionType == STATE_MORE) {
                        for(Internship internship : object) {
                            internshipList.add(internship);
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
        } else {
            Toast.makeText(getActivity(), "当前无网络，请稍后再试", Toast.LENGTH_SHORT).show();
            adapter = new InternshipAdapter(getActivity(),
                    TransferUtil.itemToInternship(itemDao.queryItem("internship")));
            xListView.setAdapter(adapter);
        }

    }

    private class mOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("title", adapter.getItem(position - 1).getCompany());
            intent.putExtra("date", adapter.getItem(position - 1).getDate());
            intent.putExtra("image", adapter.getItem(position - 1).getImage().getUrl());
            intent.putExtra("link", adapter.getItem(position - 1).getLink());
            startActivity(intent);
        }
    }
}
