package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.widget.recylerview.BaseRecyclerAdapter;

/**
 * Created by vic_zhang .
 * on 2017/3/30
 */

public class FirstPageMainListAdapter extends BaseRecyclerAdapter<ItemFirstPageMainList> {

    private Context context;


    public FirstPageMainListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_first_page_main_list, parent, false);
        return new FirstPageMainListViewHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, ItemFirstPageMainList data) {


        if (viewHolder instanceof FirstPageMainListViewHolder) {
            FirstPageMainListViewHolder vh = (FirstPageMainListViewHolder) viewHolder;
            vh.title.setText(data.getName());
            vh.sub_title.setText(data.getTxt());
            vh.money.setText(data.getPrice());
            Glide.with(context).load(data.getPic()).into(vh.img);

        }
    }

    private class FirstPageMainListViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView sub_title;
        private ImageView img;
        private TextView money;

        public FirstPageMainListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            sub_title = (TextView) itemView.findViewById(R.id.sub_title);
            money = (TextView) itemView.findViewById(R.id.money);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
