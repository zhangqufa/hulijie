package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ssj.hulijie.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vic_zhang .
 * on 2017/4/1
 */

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ArrayList<HashMap<String, Object>>> mList;
    private Context mContext;

    public ListViewAdapter(ArrayList<ArrayList<HashMap<String, Object>>> mList, Context mContext) {
        super();
        this.mList = mList;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return this.mList.size();
        }
    }
    @Override
    public Object getItem(int position) {
        if (mList == null) {
            return null;
        } else {
            return this.mList.get(position);
        }
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (this.mList != null) {
            if (holder.imageView != null) {
                holder.imageView.setImageDrawable
                        (mContext.getResources().getDrawable(R.mipmap.ic_launcher));
            }
            if (holder.gridView != null) {
                ArrayList<HashMap<String, Object>> arrayListForEveryGridView = this.mList.get(position);
                GridViewAdapter gridViewAdapter=new GridViewAdapter(mContext, arrayListForEveryGridView);
                holder.gridView.setAdapter(gridViewAdapter);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        GridView gridView;
    }
}
