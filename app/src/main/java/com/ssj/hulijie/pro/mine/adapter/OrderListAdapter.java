package com.ssj.hulijie.pro.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.mine.bean.ItemOrderResp;
import com.ssj.hulijie.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private List<ItemOrderResp.DataBean.RowsBean> lists;
    private Context context;


    public OrderListAdapter(Context context) {
        this.context = context;
    }

    public void setLists(List<ItemOrderResp.DataBean.RowsBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemOrderResp.DataBean.RowsBean dataBean = lists.get(position);
        holder.item_seller.setText(dataBean.getService_seller());
        long service_time = dataBean.getService_time();
        String time = DateUtil.longToString(service_time*1000,"MM月dd日(E) HH:mm");
        holder.item_service_time.setText(time);
        holder.item_service_addr.setText(dataBean.getService_address());
        /**
         * 订单状态：
         0 取消订单  --未完成
         11 等待买家付款 --未完成
         20 买家已付款 --未完成
         30 商家已服务  --待评价
         40 交易成功 --已完成
         3 退款中 --未完成
         4 已退款 --未完成
         */
        int status = dataBean.getStatus();
        String status_str = "";
        switch (status) {
            case 0:
                status_str = "取消订单";
                break;
            case 11:
                status_str = "等待买家付款";
                break;
            case 20:
                status_str = "买家已付款";
                break;
            case 30:
                status_str = "商家已服务";
                break;
            case 40:
                status_str = "交易成功";
                break;
            case 3:
                status_str = "退款中";
                break;
            case 4:
                status_str = "已退款";
                break;

            default:break;
        }
        holder.item_status.setText(status_str);
    }

    @Override
    public int getItemCount() {
        return lists==null?0:lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView item_seller;
        private TextView item_service_time;
        private TextView item_service_addr;
        private TextView item_status;

        public ViewHolder(View itemView) {
            super(itemView);
            item_seller = (TextView)itemView.findViewById(R.id.item_seller);
            item_service_time = (TextView)itemView.findViewById(R.id.item_service_time);
            item_service_addr = (TextView)itemView.findViewById(R.id.item_service_addr);
            item_status = (TextView)itemView.findViewById(R.id.item_status);
        }
    }

    public void setOnItemClickListener(OnItemClickListener li) {
        listener = li;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
