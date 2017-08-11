package com.ssj.hulijie.pro.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssj.hulijie.R;

import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/8/3
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {

    private Context context;
    private List<String> lists;


    public ServiceAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public ServiceHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ServiceHolder(LayoutInflater.from(context).inflate(R.layout.item_service, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ServiceHolder serviceHolder, final int i) {
        serviceHolder.tv.setText(lists.get(i));
        serviceHolder.level_bg.setVisibility(View.GONE);
        if (i % 2 != 0) {
            serviceHolder.level_bg.setVisibility(View.VISIBLE);

        }
        serviceHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClickLisener(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists ==null?0:lists.size();
    }

    class ServiceHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        private View level_bg;
        public ServiceHolder(View itemView) {
            super(itemView);
            tv=(TextView)itemView.findViewById(R.id.item_service_tv);
            level_bg=itemView.findViewById(R.id.level_bg);
        }
    }

    public interface ItemOnclickListener{
        void onItemClickLisener(int position);
    }

    private ItemOnclickListener listener;

    public void setListener(ItemOnclickListener listener) {
        this.listener = listener;
    }
}
