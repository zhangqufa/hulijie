package com.ssj.hulijie.pro.msg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.msg.bean.ItemMsgSystem;
import com.ssj.hulijie.utils.DateUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MsgSystemListAdapter extends RecyclerView.Adapter<MsgSystemListAdapter.MsgViewHolder> {
    private Context context;
    private List<ItemMsgSystem.RowsBean> lists;

    public void setLists(List<ItemMsgSystem.RowsBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public MsgSystemListAdapter(Context mContext) {
        this.context = mContext;
    }


    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgViewHolder(LayoutInflater.from(context).inflate(R.layout.item_system_msg_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, int position) {
        ItemMsgSystem.RowsBean item = lists.get(position);

        holder.title.setText(item.getContent());
        holder.time.setText(DateUtil.longToString(item.getAdd_time()*1000,"yyyy-MM-dd"));
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    class MsgViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView time;

        public MsgViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }

    public void setOnItemClickListener(MsgSystemListAdapter.OnItemClickListener li) {
        listener = li;
    }

    private MsgSystemListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
