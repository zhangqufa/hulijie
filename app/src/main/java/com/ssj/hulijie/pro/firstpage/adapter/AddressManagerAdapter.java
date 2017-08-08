package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.AddressItem;
import com.ssj.hulijie.pro.firstpage.view.AddressActivity;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;

import java.util.List;

import static com.baidu.location.b.g.G;
import static com.baidu.location.b.g.V;
import static com.baidu.location.b.g.i;
import static com.baidu.location.b.g.p;

/**
 * Created by Administrator on 2017/7/11.
 */

public class AddressManagerAdapter extends RecyclerView.Adapter<AddressManagerAdapter.AddressManagerViewHolder> {

    private Context context;
    private List<AddressItem> lists;

    public AddressManagerAdapter(Context context) {
        this.context = context;
    }

    public void setLists(List<AddressItem> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public AddressManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_select_address, parent, false);
        return new AddressManagerViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(AddressManagerViewHolder holder, final int position) {
        final AddressItem addressItem = lists.get(position);
        AppLog.Log("addressItem: "+addressItem);
        holder.item_select_address.setText(addressItem.getRegion_name());
        holder.item_address_user_phone.setText(SharedUtil.getPreferStr(SharedKey.USER_MOBILE));
        holder.item_select_address_simple.setText(addressItem.getAddress());

        if (addressItem.isDefault()) {
            holder.item_address_iv.setImageResource(R.mipmap.address_sel_seleted);
            holder.item_address_default_tv.setText("默认地址");
        } else {
            holder.item_address_default_tv.setText("设为默认");
            holder.item_address_iv.setImageResource(R.mipmap.address_sel_unseleted);

        }

        holder.item_address_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteCallback != null) {
                   deleteCallback.deleteCallback(addressItem ,position);
                }
            }
        });

        holder.item_address_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddressActivity.class);
                intent.putExtra("addressItem", addressItem);
                context.startActivity(intent);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectCallBack != null) {
                    selectCallBack.onAddressSelectCallBack(addressItem, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists==null?0:lists.size();
    }

    class AddressManagerViewHolder extends RecyclerView.ViewHolder{
        private TextView item_address_edit;
        private TextView item_select_address_simple;
        private TextView item_select_address;
        private TextView item_address_default_tv;
        private TextView item_address_user_phone;
        private ImageView item_address_iv;
        private TextView item_address_delete;
        public AddressManagerViewHolder(View itemView) {
            super(itemView);
            item_address_edit=(TextView)itemView.findViewById(R.id.item_address_edit);
            item_select_address_simple=(TextView)itemView.findViewById(R.id.item_select_address_simple);
            item_address_default_tv=(TextView)itemView.findViewById(R.id.item_address_default_tv);
            item_select_address=(TextView)itemView.findViewById(R.id.item_select_address);
            item_address_user_phone=(TextView)itemView.findViewById(R.id.item_address_user_phone);
            item_address_iv = (ImageView)itemView.findViewById(R.id.item_address_iv);
            item_address_delete = (TextView)itemView.findViewById(R.id.item_address_delete);
        }
    }

    public interface AddressDeleteCallback<T>{
        void  deleteCallback(T t,int position);
    }

    private AddressDeleteCallback deleteCallback;

    public void setDeleteCallback(AddressDeleteCallback callback) {
        this.deleteCallback = callback;
    }

    public interface AddressSelectCallBack<T>{
        void onAddressSelectCallBack(T t, int position);
    }

    private AddressSelectCallBack selectCallBack;

    public void setSelectCallBack(AddressSelectCallBack selectCallBack) {
        this.selectCallBack = selectCallBack;
    }
}
