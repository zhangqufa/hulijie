package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.AddressItem;
import com.ssj.hulijie.utils.AppLog;

import java.util.List;

import static com.baidu.location.b.g.G;
import static com.baidu.location.b.g.V;
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
        holder.item_address_iv.setVisibility(View.GONE);
        holder.item_address_delete.setVisibility(View.GONE);

        if (addressItem.isDefault()) {
            holder.item_address_iv.setVisibility(View.VISIBLE);
        }
        if (addressItem.isEdit()) {
            holder.item_address_delete.setVisibility(View.VISIBLE);
        }

        holder.item_address_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteCallback != null) {
                   deleteCallback.deleteCallback(addressItem ,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists==null?0:lists.size();
    }

    class AddressManagerViewHolder extends RecyclerView.ViewHolder{
        private TextView item_select_address;
        private ImageView item_address_iv;
        private ImageView item_address_delete;
        public AddressManagerViewHolder(View itemView) {
            super(itemView);
            item_select_address=(TextView)itemView.findViewById(R.id.item_select_address);
            item_address_iv = (ImageView)itemView.findViewById(R.id.item_address_iv);
            item_address_delete = (ImageView)itemView.findViewById(R.id.item_address_delete);
        }
    }

    public interface AddressDeleteCallback<T>{
        void  deleteCallback(T t,int position);
    }

    private AddressDeleteCallback deleteCallback;

    public void setDeleteCallback(AddressDeleteCallback callback) {
        this.deleteCallback = callback;
    }


}
