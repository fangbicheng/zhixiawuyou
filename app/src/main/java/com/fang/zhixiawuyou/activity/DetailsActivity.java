package com.fang.zhixiawuyou.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.base.BaseActivity;
import com.fang.zhixiawuyou.bean.Collection;
import com.fang.zhixiawuyou.dao.CollectionDao;
import com.fang.zhixiawuyou.view.LoadingDialog;



/**
 * Created by Administrator on 2016/6/2.
 */
public class DetailsActivity extends BaseActivity {
    private String title, date, image, link;
    private ImageView ivTip;
    private ImageButton btnBack, btnCollection;
    private TextView tvTitle;
    private WebView webView;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initView();
        initData();

        webView.loadDataWithBaseURL(null, link, "text/html", "UTF-8", null);
    }

    private void initView() {
        ivTip = (ImageView) findViewById(R.id.details_iv_tip);
        btnBack = (ImageButton) findViewById(R.id.header_content_btn_back);
        btnCollection = (ImageButton) findViewById(R.id.header_content_btn_collection);
        tvTitle = (TextView) findViewById(R.id.header_content_tv_title);
        webView = (WebView) findViewById(R.id.details_webview);
        loadingDialog = new LoadingDialog(DetailsActivity.this);

        isFirstIn();
        cancelNotification();

        ivTip.setOnClickListener(new mOnClickListener());
        btnBack.setOnClickListener(new mOnClickListener());
        btnCollection.setOnClickListener(new mOnClickListener());
        tvTitle.setText("详细信息");
        webView.setWebViewClient(new mWebViewClient());
        webView.setDownloadListener(new mDownLoadListener());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    }

    private void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        image = intent.getStringExtra("image");
        link = intent.getStringExtra("link");
    }

    private class mOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.details_iv_tip:
                    ivTip.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = getSharedPreferences("first_pre", MODE_PRIVATE).edit();
                    editor.putBoolean("isFirstInDetails", false);
                    editor.commit();
                    break;
                case R.id.header_content_btn_back:
                    finish();
                    break;
                case R.id.header_content_btn_collection:
                    doCollection(DetailsActivity.this, title, date, image, link);
                    break;
                default:
                    break;
            }
        }
    }

    private class mWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingDialog.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            loadingDialog.dismiss();
        }
    }

    private class mDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private void doCollection(Context context, String title, String date, String image, String link) {
        CollectionDao collectionDao = new CollectionDao(context);
        if(! collectionDao.isExistCollection(title)) {
            Collection collection = new Collection();
            collection.setTitle(title);
            collection.setDate(date);
            collection.setImage(image);
            collection.setLink(link);

            collectionDao.addCollection(collection);

            Toast.makeText(DetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailsActivity.this, "您已收藏", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 判断是否是第一次进入
     */
    private void isFirstIn() {
        SharedPreferences preferences = getSharedPreferences("first_pre", MODE_PRIVATE);
        Boolean isFirstIn = preferences.getBoolean("isFirstInDetails", true);

        if(isFirstIn) {
            ivTip.setVisibility(View.VISIBLE);
        } else {
            ivTip.setVisibility(View.GONE);
        }
    }

    private void cancelNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);
    }
}

