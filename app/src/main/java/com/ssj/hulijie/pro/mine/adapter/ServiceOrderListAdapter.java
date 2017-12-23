package com.ssj.hulijie.pro.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.mine.bean.ItemServiceOrderList;
import com.ssj.hulijie.utils.DateUtil;
import com.ssj.hulijie.utils.StringFormat;

import java.util.List;

/**
 * @author vic_zhang .
 *         on 2017/12/23
 */

public class ServiceOrderListAdapter extends RecyclerView.Adapter<ServiceOrderListAdapter.ViewHolder> {

    private Context context;

    private List<ItemServiceOrderList.DataBean.RowsBean> lists;

    public ServiceOrderListAdapter(Context context) {
        this.context = context;
    }

    public void setLists(List<ItemServiceOrderList.DataBean.RowsBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_accept_order, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final ItemServiceOrderList.DataBean.RowsBean rowsBean = lists.get(position);
        //加载图片
        Glide.with(context).load(rowsBean.getDefault_image()).crossFade().into(holder.item_img);

        //标题
        String goods_name = rowsBean.getGoods_name();
        if (goods_name != null && goods_name.length() > 23) {
            goods_name = goods_name.substring(0, 23)+"...";
        }
        holder.item_title.setText(goods_name);

        //用户
        holder.item_user.setText(rowsBean.getBuyer_name());
        //服务地址
        holder.item_service_addr.setText(rowsBean.getService_address());

        //服务时间
        holder.item_service_time.setText(DateUtil.longToString(rowsBean.getService_time()*1000,"yyyy-MM-dd HH:mm a"));

        //价格
        holder.item_moeny.setText(rowsBean.getGoods_amount());

        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position, rowsBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_img;
        private TextView item_title;
        private TextView item_service_time;
        private TextView item_service_addr;
        private TextView item_moeny;
        private TextView item_user;
        private Button btn_accept;

        public ViewHolder(View itemView) {
            super(itemView);
            item_img = (ImageView) itemView.findViewById(R.id.item_img);
            item_title = (TextView) itemView.findViewById(R.id.item_title);
            item_user = (TextView) itemView.findViewById(R.id.item_user);
            item_service_time = (TextView) itemView.findViewById(R.id.item_service_time);
            item_service_addr = (TextView) itemView.findViewById(R.id.item_service_addr);
            item_moeny = (TextView) itemView.findViewById(R.id.item_moeny);
            btn_accept = (Button) itemView.findViewById(R.id.btn_accept);

        }
    }

    public void setOnItemClickListener(ServiceOrderListAdapter.OnItemClickListener li) {
        listener = li;
    }

    private ServiceOrderListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
