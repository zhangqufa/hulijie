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
import com.ssj.hulijie.pro.firstpage.bean.FourpartData;

import java.util.List;

/**
 * Created by Administrator on 2017/5/14.
 */

public class FirstPageHeaderFourPartAdappter extends RecyclerView.Adapter<FirstPageHeaderFourPartAdappter.FourPartViewHolder> {

    private Context context;

    public FirstPageHeaderFourPartAdappter(Context context) {
        this.context = context;
    }

    public void setLists(List<FourpartData> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    private List<FourpartData> lists;


    @Override
    public FourPartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_rv_four_part, parent, false);
        return new FourPartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FourPartViewHolder holder, final int position) {
        final FourpartData item = lists.get(position);
        holder.tv_title.setText(item.getName());
        holder.tv_sub_titlle.setText(item.getTxt());
        Glide.with(context).load(item.getPic()).into(holder.iv_image);
//        holder.iv_image.setImageURI(item.getDefault_image());


        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position, item);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    class FourPartViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_sub_titlle;
        private ImageView iv_image;

        public FourPartViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.title);
            tv_sub_titlle = (TextView) itemView.findViewById(R.id.sub_title);
            iv_image = (ImageView) itemView.findViewById(R.id.header_rv_four_part_iv);
        }

    }

    public void setOnItemClickListener(FirstPageHeaderFourPartAdappter.OnItemClickListener li) {
        listener = li;
    }

    private FirstPageHeaderFourPartAdappter.OnItemClickListener listener;

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
