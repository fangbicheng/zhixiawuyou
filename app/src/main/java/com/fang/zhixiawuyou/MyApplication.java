package com.fang.zhixiawuyou;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016/6/5.
 */
public class MyApplication extends Application {
    private String userMajor;

    @Override
    public void onCreate() {
        super.onCreate();

        initData();
    }

    public String getUserMajor() {
        if (userMajor == null) {
            return "";
        } else {
            return userMajor;
        }
    }

    public void setUserMajor(String userMajor) {
        this.userMajor = userMajor;
    }

    private void initData() {
        //初始化Bmob功能
        Bmob.initialize(this, "65c50f82aa83a255258e2723dca481a8");

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }
}

