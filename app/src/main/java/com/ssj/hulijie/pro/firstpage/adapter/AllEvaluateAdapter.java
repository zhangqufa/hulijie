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
import com.ssj.hulijie.pro.firstpage.bean.EvaluateItem;
import com.ssj.hulijie.pro.msg.bean.MsgListData;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class AllEvaluateAdapter extends RecyclerView.Adapter<AllEvaluateAdapter.MsgViewHolder> {
    private Context context;
    private List<EvaluateItem> lists;

    public void setLists(List<EvaluateItem> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public AllEvaluateAdapter(Context mContext) {
        this.context = mContext;
    }


    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgViewHolder(LayoutInflater.from(context).inflate(R.layout.item_all_evaluate, parent, false));
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, int position) {
        EvaluateItem item = lists.get(position);


    }

    @Override
    public int getItemCount() {
        return lists == null ?5 : lists.size();
    }

    class MsgViewHolder extends RecyclerView.ViewHolder {

        public MsgViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(AllEvaluateAdapter.OnItemClickListener li) {
        listener = li;
    }

    private AllEvaluateAdapter.OnItemClickListener listener;

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
