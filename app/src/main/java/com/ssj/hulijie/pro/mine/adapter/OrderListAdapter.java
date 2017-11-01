package com.ssj.hulijie.pro.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ItemOrderResp.DataBean.RowsBean dataBean = lists.get(position);
        holder.item_seller.setText(dataBean.getService_seller());
        long service_time = dataBean.getService_time();
        String time = DateUtil.longToString(service_time * 1000, "MM月dd日(E) HH:mm");
        holder.item_service_time.setText(time);
        holder.item_service_addr.setText(dataBean.getService_address());
        holder.title.setText(dataBean.getGoods_name());
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
        String left = "";
        String right = "";
        String status_str = "";
        switch (status) {
            case 0:
                status_str = "取消订单";
                right = "删除订单";
                break;
            case 11:
                status_str = "等待买家付款";
                right = "立即付款";
                break;
            case 20:
                status_str = "买家已付款";
                right = "退款";
                break;
            case 30:
                status_str = "待评价";
                right = "去评价";
                break;
            case 40:
                status_str = "交易成功";
                left = "退款";
                right = "确认完成";
                break;
            case 3:
                status_str = "退款中";
                break;
            case 4:
                status_str = "已退款";
                right = "删除订单";
                break;

            default:
                break;
        }
        holder.item_status.setText(status_str);
        //初始化两个按钮
        holder.btn_left.setVisibility(View.INVISIBLE);
        holder.btn_right.setVisibility(View.INVISIBLE);


        if (TextUtils.isEmpty(left)) {
            holder.btn_left.setVisibility(View.INVISIBLE);
        } else {
            holder.btn_left.setText(left);
            holder.btn_left.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(right)) {
            holder.btn_right.setVisibility(View.INVISIBLE);
        } else {
            holder.btn_right.setText(right);
            holder.btn_right.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(position, dataBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView item_seller;
        private TextView item_service_time;
        private TextView item_service_addr;
        private TextView item_status;
        private Button btn_left;
        private Button btn_right;
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            item_seller = (TextView) itemView.findViewById(R.id.item_seller);
            item_service_time = (TextView) itemView.findViewById(R.id.item_service_time);
            item_service_addr = (TextView) itemView.findViewById(R.id.item_service_addr);
            title = (TextView) itemView.findViewById(R.id.title);
            item_status = (TextView) itemView.findViewById(R.id.item_status);
            btn_left = (Button) itemView.findViewById(R.id.btn_left);
            btn_right = (Button) itemView.findViewById(R.id.btn_right);
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
