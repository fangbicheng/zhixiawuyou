package com.fang.zhixiawuyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.bean.Demand;
import com.fang.zhixiawuyou.util.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 */
public class DemandAdapter extends BaseAdapter {
    private Context context;
    private List<Demand> demandList;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public DemandAdapter(Context context, List<Demand> demandList) {
        this.context = context;
        this.demandList = demandList;

        //图片加载配置
        options = Options.getListOptions();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return demandList == null ? 0 : demandList.size();
    }

    @Override
    public Demand getItem(int position) {
        if(demandList != null && demandList.size() != 0) {
            return demandList.get(position);
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
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.item_demand, null);
            viewHolder.tvCompany = (TextView) convertView.findViewById(R.id.item_demand_tv_company);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.item_demand_tv_date);
            viewHolder.ivLogo = (ImageView) convertView.findViewById(R.id.item_demand_iv_logo);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Demand demand = getItem(position);
        viewHolder.tvCompany.setText(demand.getCompany());
        viewHolder.tvDate.setText(demand.getDate());
        imageLoader.displayImage(demand.getImage().getUrl(), viewHolder.ivLogo, options);

        return convertView;
    }

    private class ViewHolder {
        public TextView tvCompany;
        public TextView tvDate;
        public ImageView ivLogo;
    }
}
