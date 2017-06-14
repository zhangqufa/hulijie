package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.ServiceItem;
import com.ssj.hulijie.utils.AppLog;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class ServicePriceAdapter extends BaseAdapter {
    private Context context;
    private List<ServiceItem> lists;

    public void setLists(List<ServiceItem> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public ServicePriceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists==null?0: lists.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        ServiceItem item = lists.get(i);
        AppLog.Log("item: "+item);
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_service_price,null, false);
            holder.name = (TextView) view.findViewById(R.id.service_name);
            holder.price = (TextView) view.findViewById(R.id.service_price);
            view.setTag(holder);
        } else {
            holder =(ViewHolder)view.getTag();
        }

        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
        return view;
    }

    class ViewHolder {
        private TextView name;
        private TextView price;


    }
}
