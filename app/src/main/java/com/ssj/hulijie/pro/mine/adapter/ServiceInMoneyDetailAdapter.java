package com.ssj.hulijie.pro.mine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.mine.bean.ItemServiceInMoneyDetail;
import com.ssj.hulijie.utils.DateUtil;

import java.util.List;

/**
 * @author vic_zhang .
 *         on 2018/5/21
 */

public class ServiceInMoneyDetailAdapter extends RecyclerView.Adapter<ServiceInMoneyDetailAdapter.ServiceInMoneyViewHolder> {

    private List<ItemServiceInMoneyDetail.DataBean.RowsBean> lists;

    public void setLists(List<ItemServiceInMoneyDetail.DataBean.RowsBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public ServiceInMoneyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ServiceInMoneyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_money_in_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(ServiceInMoneyViewHolder holder, int position) {

        ItemServiceInMoneyDetail.DataBean.RowsBean item = lists.get(position);
        holder.item_time.setText(DateUtil.longToString(item.getAdd_time() * 1000));
        holder.item_title.setText(item.getMark());
        String moeny = "";
        int is_add = item.getIs_add();
        if (is_add == 1) {
            moeny = "+" + item.getAmount();
        } else {
            moeny = "-" + item.getAmount();
        }
        holder.item_money.setText(moeny);
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    class ServiceInMoneyViewHolder extends RecyclerView.ViewHolder {

        private TextView item_time;
        private TextView item_title;

        private TextView item_money;

        public ServiceInMoneyViewHolder(View itemView) {
            super(itemView);

            item_time = (TextView) itemView.findViewById(R.id.item_create_time);
            item_title = (TextView) itemView.findViewById(R.id.item_title);
            item_money = (TextView) itemView.findViewById(R.id.item_money);

        }
    }

}
