package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.CatetoryItem;

import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/4/1
 */

public class ListViewAdapter extends BaseAdapter {
    private List<CatetoryItem> mList;
    private Context mContext;

    public void setLists(List<CatetoryItem> lists) {
        this.mList = lists;
        notifyDataSetChanged();
    }


    public ListViewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from
                    (this.mContext).inflate(R.layout.listview_item, null, false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.listview_item_imageview);
            holder.gridView = (GridView) convertView.findViewById(R.id.listview_item_gridview);
            holder.first_title = (TextView) convertView.findViewById(R.id.first_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (this.mList != null) {
            if (holder.imageView != null) {
                holder.imageView.setImageDrawable
                        (mContext.getResources().getDrawable(R.mipmap.ic_launcher));
            }
            if (holder.first_title != null) {
                holder.first_title.setText(mList.get(position).getCate_name());
            }
            if (holder.gridView != null) {
                CatetoryItem catetoryItem = mList.get(position);
                List<CatetoryItem.CatetoryChildItem> child = catetoryItem.getChild();

                GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext, child);
                holder.gridView.setAdapter(gridViewAdapter);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        GridView gridView;
        TextView first_title;
    }
}
