package com.ssj.hulijie.pro.msg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.msg.bean.ItemMsgHuoDong;
import com.ssj.hulijie.utils.DateUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MsgHuodongListAdapter extends RecyclerView.Adapter<MsgHuodongListAdapter.MsgViewHolder> {
    private Context context;
    private List<ItemMsgHuoDong.RowsBean> lists;

    public void setLists(List<ItemMsgHuoDong.RowsBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public MsgHuodongListAdapter(Context mContext) {
        this.context = mContext;
    }


    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgViewHolder(LayoutInflater.from(context).inflate(R.layout.item_msg_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, final int position) {
        final ItemMsgHuoDong.RowsBean item = lists.get(position);

        holder.id_diver_time.setText(DateUtil.longToString(item.getAdd_time() * 1000, "yyyy-MM-dd"));

        holder.title.setText(item.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        private TextView id_diver_time;

        public MsgViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            id_diver_time = (TextView) itemView.findViewById(R.id.id_diver_time);
        }
    }

    public void setOnItemClickListener(MsgHuodongListAdapter.OnItemClickListener li) {
        listener = li;
    }

    private MsgHuodongListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
