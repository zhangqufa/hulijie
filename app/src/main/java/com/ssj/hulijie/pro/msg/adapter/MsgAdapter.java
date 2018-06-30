package com.ssj.hulijie.pro.msg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.msg.bean.MsgData;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgViewHolder> {
    private Context context;
    private List<MsgData> lists;

    public void setLists(List<MsgData> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public MsgAdapter(Context mContext) {
        this.context = mContext;
    }


    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgViewHolder(LayoutInflater.from(context).inflate(R.layout.item_msg, parent, false));
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, final int position) {
        final MsgData item = lists.get(position);

        holder.title.setText(item.getTitle());
        holder.sub_title.setText(item.getSub_title());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String time_str = sdf.format(item.getTime());
        holder.time.setText(time_str);

        Glide.with(context).load(item.getImg()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(position, item);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    class MsgViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView sub_title;
        private TextView time;
        private ImageView img;

        public MsgViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            sub_title = (TextView) itemView.findViewById(R.id.sub_title);
            time = (TextView) itemView.findViewById(R.id.time);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public void setOnItemClickListener(MsgAdapter.OnItemClickListener li) {
        listener = li;
    }

    private MsgAdapter.OnItemClickListener listener;

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
