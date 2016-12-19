package com.fang.zhixiawuyou.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.adapter.CollectionAdapter;
import com.fang.zhixiawuyou.base.BaseActivity;
import com.fang.zhixiawuyou.dao.CollectionDao;

/**
 * Created by Administrator on 2016/6/16.
 */
public class CollectionActivity extends BaseActivity {
    private ImageButton btnBack;
    private TextView tvTitle;
    private ListView listView;
    private CollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initView();
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.header_user_btn_back);
        tvTitle = (TextView) findViewById(R.id.header_user_tv_title);
        listView = (ListView) findViewById(R.id.collection_listview);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("我的收藏");
        adapter = new CollectionAdapter(this, new CollectionDao(CollectionActivity.this).queryCollection());
        listView.setEmptyView(findViewById(R.id.collection_iv_tip));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CollectionActivity.this, DetailsActivity.class);
                intent.putExtra("title", adapter.getItem(position).getTitle());
                intent.putExtra("date", adapter.getItem(position).getDate());
                intent.putExtra("image", adapter.getItem(position).getImage());
                intent.putExtra("link", adapter.getItem(position).getLink());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                View popupWindowView = getLayoutInflater().inflate(R.layout.activity_popupwindow, null);
                Button btnDelete = (Button) popupWindowView.findViewById(R.id.popupwindow_btn_delete);
                final PopupWindow popupWindow = new PopupWindow(popupWindowView, 120, 100);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CollectionDao collectionDao = new CollectionDao(CollectionActivity.this);
                        collectionDao.deleteCollection(adapter.getItem(position).getTitle());
                        adapter = new CollectionAdapter(CollectionActivity.this,
                                collectionDao.queryCollection());
                        listView.setAdapter(adapter);
                        popupWindow.dismiss();
                    }
                });

                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAsDropDown(view, 500, 0);

                return true;
            }
        });
    }
}
