package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainHeaderList;
import com.ssj.hulijie.widget.recylerview.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/3/31
 */

public class FirstPageMainHeaderAdapter extends RecyclerView.Adapter<FirstPageMainHeaderAdapter.FirstPageMainHeaderViewHolder> {
    private Context context;
    private List<ItemFirstPageMainHeaderList>  data;

    public FirstPageMainHeaderAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ItemFirstPageMainHeaderList> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public FirstPageMainHeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_first_page_header, parent, false);
        return new FirstPageMainHeaderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FirstPageMainHeaderViewHolder holder, final int position) {
        final ItemFirstPageMainHeaderList item = data.get(position);
        holder.rv_header_rv_tv.setText(item.getTitle());
        if (position == 7) {
            holder.rv_header_rv_iv.setImageResource(R.mipmap.more);
        } else {
            holder.rv_header_rv_iv.setImageURI(item.getImg());
        }

        if(listener != null) {
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
        return data==null?0:data.size();
    }

    public class FirstPageMainHeaderViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView rv_header_rv_iv;
        private TextView rv_header_rv_tv;


        public FirstPageMainHeaderViewHolder(View itemView) {
            super(itemView);
            rv_header_rv_iv = (SimpleDraweeView) itemView.findViewById(R.id.rv_header_rv_iv);
            rv_header_rv_tv = (TextView) itemView.findViewById(R.id.rv_header_rv_tv);
        }
    }


    public void setOnItemClickListener(FirstPageMainHeaderAdapter.OnItemClickListener li) {
        listener = li;
    }

    private OnItemClickListener listener;
    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
