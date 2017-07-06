package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.PoiSearchResults;
import com.ssj.hulijie.widget.recylerview.BaseRecyclerAdapter;

/**
 * Created by vic_zhang .
 * on 2017/3/30
 */

public class AddressAdapter extends BaseRecyclerAdapter<PoiSearchResults> {

    private Context context;


    public AddressAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
        return new AdressViewHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, PoiSearchResults data) {


        if (viewHolder instanceof AdressViewHolder) {
            AdressViewHolder vh = (AdressViewHolder) viewHolder;
            vh.address_name.setText(data.getMname());
            vh.address_describe.setText(data.getMaddress());

        }
    }

    private class AdressViewHolder extends RecyclerView.ViewHolder {
        private TextView address_name;
        private TextView address_describe;

        public AdressViewHolder(View itemView) {
            super(itemView);
            address_name= (TextView) itemView.findViewById(R.id.address_name);
            address_describe= (TextView) itemView.findViewById(R.id.address_describe);
        }
    }
}
