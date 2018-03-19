package com.ssj.hulijie.pro.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.mine.bean.ItemOrderResp;
import com.ssj.hulijie.utils.DateUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private List<ItemOrderResp.DataBean.RowsBean> lists;
    private Context context;

    /**
     * 商家列表标志
     */
    private boolean sellerFlag;

    public OrderListAdapter(Context context, boolean sellerFlag) {
        this.context = context;
        this.sellerFlag = sellerFlag;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ItemOrderResp.DataBean.RowsBean dataBean = lists.get(position);
        holder.item_seller.setText(dataBean.getService_seller());
        long service_time = dataBean.getService_time();
        String time = DateUtil.longToString(service_time * 1000, "MM月dd日(E) HH:mm");
        holder.item_service_time.setText(time);
        holder.item_service_addr.setText(dataBean.getService_address());
        holder.title.setText(dataBean.getGoods_name());

        //init
        holder.ll_btn.setVisibility(View.VISIBLE);

        /**
         * 订单状态：
         0 取消订单  --未完成
         11 等待买家付款 --未完成
         20 买家已付款 --未完成
         30 已抢单
         40  待评价
         50 交易成功 --已完成
         55 用户取消
         56 SELLER取消订单
         3 退款中 --未完成
         4 已退款 --未完成
         */
        int status = dataBean.getStatus();
        String left = "";
        String right = "";
        String status_str = "";
        switch (status) {
            // 0  临时改变状态，重新获取数据 会变成55或56
            case 0:
            case 56:
            case 55:
                status_str = context.getString(R.string.order_already_cancel);
                holder.ll_btn.setVisibility(View.GONE);
                break;
            case 11:
                status_str = context.getString(R.string.order_wait_to_pay);
                left = context.getString(R.string.order_cancel);
                right = context.getString(R.string.order_immediately_pay);
                break;
            case 20:
                status_str = context.getString(R.string.order_already_pay);
                right = context.getString(R.string.order_refund);
                break;
            case 30:
                if (sellerFlag) {
                    status_str = context.getString(R.string.order_servicing);
                    right = context.getString(R.string.order_cancel);
                } else {
                    status_str = context.getString(R.string.order_accepted_order);
                    right = context.getString(R.string.order_confirm_complete);
                }
                break;
            case 40:
                status_str = context.getString(R.string.order_wait_evaluate);
                right = context.getString(R.string.order_to_evaluate);
                left = context.getString(R.string.order_refund);
                break;
            case 50:
                status_str = context.getString(R.string.order_business_success);
                break;
            case 3:
                status_str = context.getString(R.string.order_refunding);
                break;
            case 4:
                status_str = context.getString(R.string.order_already_refund);
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

        //设置点击事件
        holder.btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn_str = holder.btn_left.getText().toString();
                if (context.getString(R.string.order_cancel).equals(btn_str)
                        || context.getString(R.string.order_refund).equals(btn_str)) {
                    if (listener != null) {
                        listener.onItemLeftClick(position, btn_str, dataBean);
                    }
                }
            }
        });

        holder.btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn_str = holder.btn_right.getText().toString();
                //待付款
                if (context.getString(R.string.order_immediately_pay).equals(btn_str)
                        //退款
                        || context.getString(R.string.order_refund).equals(btn_str)
                        || context.getString(R.string.order_cancel).equals(btn_str)
                        || context.getString(R.string.order_confirm_complete).equals(btn_str)) {
                    if (listener != null) {
                        listener.onItemRightClick(position, btn_str, dataBean);
                    }
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
        private RelativeLayout ll_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_btn = (RelativeLayout) itemView.findViewById(R.id.ll_btn);
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

        void onItemLeftClick(int position, String status, T data);

        void onItemRightClick(int position, String status, T data);
    }


}
