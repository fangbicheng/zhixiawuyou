package com.fang.zhixiawuyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.bean.Recruitment;
import com.fang.zhixiawuyou.util.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * Created by Administrator on 2016/6/1.
 */
public class RecruitmentAdapter extends BaseAdapter {
    private Context context;
    private List<Recruitment> recruitmentList;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public RecruitmentAdapter(Context context, List<Recruitment> recruitmentList) {
        this.context = context;
        this.recruitmentList = recruitmentList;

        //图片加载配置
        options = Options.getListOptions();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return recruitmentList == null ? 0 : recruitmentList.size();
    }

    @Override
    public Recruitment getItem(int position) {
        if(recruitmentList != null && recruitmentList.size() != 0) {
            return recruitmentList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recruitment, null);

            viewHolder.tvCompany = (TextView) convertView.findViewById(R.id.item_recruitment_tv_company);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.item_recruitment_tv_date);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.item_recruitment_tv_address);
            viewHolder.ivLogo = (ImageView) convertView.findViewById(R.id.item_recruitment_iv_logo);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Recruitment recruitment = getItem(position);
        viewHolder.tvCompany.setText(recruitment.getCompany());
        viewHolder.tvDate.setText(recruitment.getDate());
        viewHolder.tvAddress.setText(recruitment.getAddress());
        imageLoader.displayImage(recruitment.getImage().getUrl(), viewHolder.ivLogo, options);

        return convertView;
    }

    private final class ViewHolder {
        public TextView tvCompany;
        public TextView tvDate;
        public TextView tvAddress;
        public ImageView ivLogo;
    }
}
