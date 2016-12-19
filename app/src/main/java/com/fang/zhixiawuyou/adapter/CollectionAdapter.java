package com.fang.zhixiawuyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fang.zhixiawuyou.R;
import com.fang.zhixiawuyou.bean.Collection;
import com.fang.zhixiawuyou.util.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public class CollectionAdapter extends BaseAdapter {
    private Context context;
    private List<Collection> collectionList;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public CollectionAdapter(Context context, List<Collection> collectionList) {
        this.context = context;
        this.collectionList = collectionList;

        //图片加载配置
        options = Options.getListOptions();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return collectionList == null ? 0 : collectionList.size();
    }

    @Override
    public Collection getItem(int position) {
        if(collectionList != null && collectionList.size() != 0) {
            return collectionList.get(position);
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

            convertView = LayoutInflater.from(context).inflate(R.layout.item_collection, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.item_collection_tv_title);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.item_collection_tv_date);
            viewHolder.ivLogo = (ImageView) convertView.findViewById(R.id.item_collection_iv_logo);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Collection collection = getItem(position);
        viewHolder.tvTitle.setText(collection.getTitle());
        viewHolder.tvDate.setText(collection.getDate());
        imageLoader.displayImage(collection.getImage(), viewHolder.ivLogo, options);

        return convertView;
    }

    private class ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView ivLogo;
    }
}
