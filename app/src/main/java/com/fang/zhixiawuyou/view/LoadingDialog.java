package com.fang.zhixiawuyou.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.fang.zhixiawuyou.R;

/**
 * Created by Administrator on 2016/6/3.
 */
public class LoadingDialog extends ProgressDialog {
    private ImageView imageView;

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_loading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

        imageView = (ImageView) findViewById(R.id.dialog_loading_iv);
        final AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }
}
