package com.fang.zhixiawuyou.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fang.zhixiawuyou.R;

/**
 * Created by Administrator on 2016/6/3.
 */
public class MyAlertDialog extends Dialog {
    private Context context;
    private String title;
    private String content;
    private String cancelButtonText;
    private String confirmButtonText;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        public void doCancel();
        public void doConfirm();
    }

    public MyAlertDialog(Context context, String title, String content, String cancelButtonText,
                         String confirmButtonText) {
        super(context);
        this.context = context;
        this.title = title;
        this.content = content;
        this.cancelButtonText = cancelButtonText;
        this.confirmButtonText = confirmButtonText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert, null);
        setContentView(view);

        TextView tvTitle = (TextView) view.findViewById(R.id.dialog_alert_tv_title);
        TextView tvContent = (TextView) view.findViewById(R.id.dialog_alert_tv_content);
        Button btnCancel = (Button) view.findViewById(R.id.dialog_alert_btn_cancel);
        Button btnConfirm = (Button) view.findViewById(R.id.dialog_alert_btn_confirm);

        tvTitle.setText(title);
        tvContent.setText(content);
        btnCancel.setText(cancelButtonText);
        btnConfirm.setText(confirmButtonText);

        btnCancel.setOnClickListener(new mOnClickListener());
        btnConfirm.setOnClickListener(new mOnClickListener());
    }

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class mOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_alert_btn_cancel:
                    clickListenerInterface.doCancel();
                    break;
                case R.id.dialog_alert_btn_confirm:
                    clickListenerInterface.doConfirm();
                    break;
            }
        }
    }
}
