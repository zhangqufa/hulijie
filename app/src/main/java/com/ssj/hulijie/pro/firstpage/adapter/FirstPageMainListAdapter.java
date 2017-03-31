package com.ssj.hulijie.pro.firstpage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.widget.recylerview.BaseRecyclerAdapter;

/**
 * Created by vic_zhang .
 * on 2017/3/30
 */

public class FirstPageMainListAdapter extends BaseRecyclerAdapter<ItemFirstPageMainList> {

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_first_page_main_list, parent, false);
        return new FirstPageMainListViewHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, ItemFirstPageMainList data) {
        if(viewHolder instanceof FirstPageMainListViewHolder) {
            ((FirstPageMainListViewHolder) viewHolder).tv.setText(data.getStr());
        }
    }

    private class FirstPageMainListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv ;

        public FirstPageMainListViewHolder(View itemView) {
            super(itemView);
            tv= (TextView)itemView.findViewById(R.id.item_tv);
        }
    }
}
