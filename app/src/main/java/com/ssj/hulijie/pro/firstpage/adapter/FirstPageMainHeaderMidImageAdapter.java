package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainHeaderList;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainHeaderMidImageList;

import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/3/31
 */

public class FirstPageMainHeaderMidImageAdapter extends RecyclerView.Adapter<FirstPageMainHeaderMidImageAdapter.FirstPageMainHeaderMidImageViewHolder> {
    private Context context;
    private List<ItemFirstPageMainHeaderMidImageList>  data;

    public FirstPageMainHeaderMidImageAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ItemFirstPageMainHeaderMidImageList> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public FirstPageMainHeaderMidImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_first_page_header_mid_img, parent, false);
        return new FirstPageMainHeaderMidImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FirstPageMainHeaderMidImageViewHolder holder, int position) {
        ItemFirstPageMainHeaderMidImageList item = data.get(position);
        holder.img.setImageURI(item.getImg());

    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public class FirstPageMainHeaderMidImageViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView img;


        public FirstPageMainHeaderMidImageViewHolder(View itemView) {
            super(itemView);
            img = (SimpleDraweeView) itemView.findViewById(R.id.item_first_page_header_mid_img);
        }
    }
}
