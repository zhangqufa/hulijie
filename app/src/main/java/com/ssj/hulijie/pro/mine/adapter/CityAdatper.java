package com.ssj.hulijie.pro.mine.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.adapter.CityGridViewAdapter;
import com.ssj.hulijie.pro.firstpage.adapter.GridViewAdapter;
import com.ssj.hulijie.pro.firstpage.adapter.ListViewAdapter;
import com.ssj.hulijie.pro.firstpage.bean.CatetoryItem;
import com.ssj.hulijie.pro.mine.bean.AddrJsonBean;
import com.ssj.hulijie.pro.mine.bean.CityGridItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/7/17
 */

public class CityAdatper extends BaseAdapter {
    private Context context;

    private List<AddrJsonBean.ProvincesBean> lists;

    public CityAdatper(Context activity, List<AddrJsonBean.ProvincesBean> data) {
        this.context = activity;
        this.lists = data;
    }

    @Override
    public int getCount() {
        return lists == null ? 0 : lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from
                    (context).inflate(R.layout.listview_item_city, null, false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.listview_item_imageview);
            holder.gridView = (GridView) convertView.findViewById(R.id.listview_item_gridview);
            holder.first_title = (TextView) convertView.findViewById(R.id.first_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (this.lists != null) {
            AddrJsonBean.ProvincesBean provincesBean = lists.get(position);
            if (holder.first_title != null) {
                holder.first_title.setText(provincesBean.getName());
            }
            if (holder.gridView != null) {
                List<CityGridItem> lists_city = new ArrayList<>();
                List<String> citys = provincesBean.getCitys();
                for (String s : citys) {
                    CityGridItem item = new CityGridItem();
                    item.setProvince(provincesBean.getName());
                    item.setCity(s);
                    lists_city.add(item);
                }
                CityGridViewAdapter gridViewAdapter = new CityGridViewAdapter(context, lists_city);
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
