package com.ssj.hulijie.pro.mine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.mine.bean.ItemEvaluateLevel;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public class EvaluateLevelAdapter extends RecyclerView.Adapter<EvaluateLevelAdapter.EvaluateLevelViewHolder> {

    private List<ItemEvaluateLevel> lists;

    public EvaluateLevelAdapter(List<ItemEvaluateLevel> lists) {
        this.lists = lists;
    }


    @Override
    public EvaluateLevelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new EvaluateLevelViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_evaluate_level, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(EvaluateLevelViewHolder holder, final int i) {
        final ItemEvaluateLevel item = lists.get(i);
        holder.item_iv.setImageResource(item.getImgRes());

        if (item.isSelected()) {
            holder.item_iv.setImageResource(item.getImgSelected());
        }
        holder.item_tv.setText(item.getTitle());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickLister(item, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    class EvaluateLevelViewHolder extends RecyclerView.ViewHolder {
        private TextView item_tv;

        private ImageView item_iv;


        public EvaluateLevelViewHolder(View itemView) {
            super(itemView);
            item_tv = (TextView) itemView.findViewById(R.id.item_tv);
            item_iv = (ImageView) itemView.findViewById(R.id.item_iv);
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClickLister(T t, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
