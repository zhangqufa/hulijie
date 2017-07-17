package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.mine.bean.CityGridItem;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/4/1
 */

public class CityGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<CityGridItem> mList;

    public CityGridViewAdapter(Context mContext, List<CityGridItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from
                    (this.mContext).inflate(R.layout.gridview_item, null, false);
            holder.button = (Button) convertView.findViewById(R.id.gridview_item_button);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (this.mList != null) {
            final CityGridItem item = mList.get(position);
            if (holder.button != null) {
                holder.button.setText(item.getCity());
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(item);
//                        Toast.makeText(mContext, mList.get(position), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        return convertView;
    }

    private class ViewHolder {
        Button button;
    }
}
