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
import com.ssj.hulijie.pro.msg.bean.MsgListData;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.MsgViewHolder> {
    private Context context;
    private List<MsgListData> lists;
    private int imgRes[]  = {R.mipmap.temp1,R.mipmap.temp2,R.mipmap.temp3};

    public void setLists(List<MsgListData> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public MsgListAdapter(Context mContext) {
        this.context = mContext;
    }


    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgViewHolder(LayoutInflater.from(context).inflate(R.layout.item_msg_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, int position) {
        MsgListData item = lists.get(position);

        holder.title.setText(item.getTitle());
        holder.sub_title.setText(item.getSub_title());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time_str = sdf.format(item.getTime());
        holder.time.setText(time_str);
        holder.id_diver_time.setText(time_str);
        Glide.with(context).load(imgRes[position%3]).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    class MsgViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView sub_title;
        private TextView time;
        private TextView id_diver_time;
        private ImageView img;

        public MsgViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            sub_title = (TextView) itemView.findViewById(R.id.sub_title);
            time = (TextView) itemView.findViewById(R.id.time);
            id_diver_time = (TextView) itemView.findViewById(R.id.id_diver_time);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public void setOnItemClickListener(MsgListAdapter.OnItemClickListener li) {
        listener = li;
    }

    private MsgListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
