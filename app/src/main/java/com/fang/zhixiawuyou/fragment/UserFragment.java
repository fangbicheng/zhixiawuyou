package com.fang.zhixiawuyou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.activity.AboutActivity;
import com.fang.zhixiawuyou.activity.CollectionActivity;
import com.fang.zhixiawuyou.activity.FeedbackActivity;
import com.fang.zhixiawuyou.activity.LoveActivity;
import com.fang.zhixiawuyou.util.DataCleanUtil;
import com.fang.zhixiawuyou.view.MyAlertDialog;

import java.io.File;

/**
 * Created by Administrator on 2016/6/1.
 */
public class UserFragment extends Fragment {
    private final static int UPDATE_CACHE = 1;

    private TextView tvTitle;
    private RelativeLayout love, collection, clear, feedback, update, about;
    private TextView tvCache;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                if(msg.what == UPDATE_CACHE) {
                    tvCache.setText(DataCleanUtil.getCacheSize(new File("/data/data/" +
                            getActivity().getPackageName() + "/cache")));
                }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.user_tv_title);
        love = (RelativeLayout) view.findViewById(R.id.user_layout_love);
        collection = (RelativeLayout) view.findViewById(R.id.user_layout_collection);
        clear = (RelativeLayout) view.findViewById(R.id.user_layout_clear);
        feedback = (RelativeLayout) view.findViewById(R.id.user_layout_feedback);
        update = (RelativeLayout) view.findViewById(R.id.user_layout_update);
        about = (RelativeLayout) view.findViewById(R.id.user_layout_about);
        tvCache = (TextView) view.findViewById(R.id.user_tv_cache);

        tvTitle.setText("我的职厦");
        love.setOnClickListener(new mOnClickListener());
        collection.setOnClickListener(new mOnClickListener());
        clear.setOnClickListener(new mOnClickListener());
        feedback.setOnClickListener(new mOnClickListener());
        update.setOnClickListener(new mOnClickListener());
        about.setOnClickListener(new mOnClickListener());
        tvCache.setText(DataCleanUtil.getCacheSize(new File("/data/data/" +
                getActivity().getPackageName() + "/cache")));
    }

    private class mOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.user_layout_love:
                    intent = new Intent(getActivity(), LoveActivity.class);
                    startActivity(intent);
                    break;
                case R.id.user_layout_collection:
                    intent = new Intent(getActivity(), CollectionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.user_layout_clear:
                    deleteCache();
                    break;
                case R.id.user_layout_feedback:
                    intent = new Intent(getActivity(), FeedbackActivity.class);
                    startActivity(intent);
                    break;
                case R.id.user_layout_update:
                    Toast.makeText(getActivity(), "已经是最新版本了", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.user_layout_about:
                    intent = new Intent(getActivity(), AboutActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

    private void deleteCache() {
        final MyAlertDialog dialog = new MyAlertDialog(getActivity(), "清除缓存",
                "是否要清除所有缓存？", "取消", "确定");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.setClickListenerInterface(new MyAlertDialog.ClickListenerInterface() {
            @Override
            public void doCancel() {
                dialog.dismiss();
            }

            @Override
            public void doConfirm() {
                DataCleanUtil.deleteAllCache(getActivity());
                Message message = Message.obtain();
                message.what = UPDATE_CACHE;
                handler.sendMessage(message);
                Toast.makeText(getActivity(), "缓存清理成功", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
