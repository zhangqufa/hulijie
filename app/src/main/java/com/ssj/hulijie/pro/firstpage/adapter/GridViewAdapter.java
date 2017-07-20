package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.CatetoryItem;
import com.ssj.hulijie.pro.firstpage.view.OrderActivity;
import com.ssj.hulijie.pro.firstpage.view.SearchResultActivity;

import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/4/1
 */

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<CatetoryItem.CatetoryChildItem> mList;

    public GridViewAdapter(Context mContext, List<CatetoryItem.CatetoryChildItem> mList) {
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
            if (holder.button != null) {
                holder.button.setText(mList.get(position).getCate_name());
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, mList.get(position).getParent_id() + "_" + mList.get(position).getCate_id(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, SearchResultActivity.class);
                        intent.putExtra("key", mList.get(position).getCate_name());
                        mContext.startActivity(intent);
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
