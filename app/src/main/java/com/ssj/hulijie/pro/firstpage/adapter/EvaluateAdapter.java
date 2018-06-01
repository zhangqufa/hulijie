package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.ItemEvaluate;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.EvaluateViewHolder> {

    private Context context;

    private List<ItemEvaluate.DataBean.RowsBean> lists;

    public EvaluateAdapter(Context context) {
        this.context = context;
    }

    public void setLists(List<ItemEvaluate.DataBean.RowsBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public EvaluateViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new EvaluateViewHolder(LayoutInflater.from(context).inflate(R.layout.item_evaluate, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(EvaluateViewHolder evaluateViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return lists ==null?10:lists.size();
    }

    class EvaluateViewHolder extends RecyclerView.ViewHolder {

        public EvaluateViewHolder(View itemView) {
            super(itemView);
        }
    }

}
