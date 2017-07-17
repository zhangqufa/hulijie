package com.ssj.hulijie.pro.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.mine.bean.AddrJsonBean;
import com.ssj.hulijie.pro.mine.bean.ProvinceItem;

import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/7/17
 */

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder> {
    private Context context;
    private List<ProvinceItem> lists;

    public void setLists(List<ProvinceItem> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public ProvinceAdapter(Context context, List<ProvinceItem> strings) {
        this.lists = strings;
        this.context = context;
    }

    @Override
    public ProvinceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ProvinceViewHolder(LayoutInflater.from(context).inflate(R.layout.item_province, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ProvinceViewHolder holder, final int position) {
        ProvinceItem s = lists.get(position);
        holder.item_province_tv.setText(s.getProvince());
        holder.item_province_tv.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        if (s.isSelect()) {
            holder.item_province_tv.setBackgroundColor(context.getResources().getColor(R.color.app_bg));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickLisener != null) {
                    onItemClickLisener.onItemClickLisener(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists ==null?0:lists.size();
    }

    class ProvinceViewHolder extends RecyclerView.ViewHolder{
        private TextView item_province_tv;


        public ProvinceViewHolder(View itemView) {
            super(itemView);
            item_province_tv = (TextView) itemView.findViewById(R.id.item_province_tv);
        }



    }

    public interface OnItemClickLisener{
        void onItemClickLisener(int position);
    }

    private OnItemClickLisener onItemClickLisener;

    public void setOnItemClickLisener(OnItemClickLisener onItemClickLisener) {
        this.onItemClickLisener = onItemClickLisener;
    }
}
