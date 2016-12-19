package com.fang.zhixiawuyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.bean.Internship;
import com.fang.zhixiawuyou.util.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/6/7.
 */
public class InternshipAdapter extends BaseAdapter {
    private Context context;
    private List<Internship> internshipList;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public InternshipAdapter(Context context, List<Internship> internshipList) {
        this.context = context;
        this.internshipList = internshipList;

        //图片加载配置
        options = Options.getListOptions();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return internshipList == null ? 0 : internshipList.size();
    }

    @Override
    public Internship getItem(int position) {
        if(internshipList != null && internshipList.size() != 0) {
            return internshipList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_internship, null);

            viewHolder.tvCompany = (TextView) convertView.findViewById(R.id.item_internship_tv_company);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.item_internship_tv_date);
            viewHolder.ivLogo = (ImageView) convertView.findViewById(R.id.item_internship_iv_logo);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Internship internship = getItem(position);
        viewHolder.tvCompany.setText(internship.getCompany());
        viewHolder.tvDate.setText(internship.getDate());
        imageLoader.displayImage(internship.getImage().getUrl(), viewHolder.ivLogo, options);

        return convertView;
    }

    private class ViewHolder {
        public TextView tvCompany;
        public TextView tvDate;
        public ImageView ivLogo;
    }
}
