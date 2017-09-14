package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.ItemCategory;
import com.ssj.hulijie.pro.firstpage.bean.ItemCategoryMain;

import java.util.List;

import static com.ssj.hulijie.base.HljAppliation.context;

/**
 * Created by Administrator on 2017/9/14.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private List<ItemCategoryMain.DataBean.RowsBean> lists;

    private Context context;

    public CategoryListAdapter(Context context) {
        this.context = context;
    }

    public void setLists(List<ItemCategoryMain.DataBean.RowsBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.item_first_page_main_list, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final ItemCategoryMain.DataBean.RowsBean data = lists.get(position);
        holder.title.setText(data.getGoods_name());
        holder.sub_title.setText(data.getTxt());
        holder.money.setText(data.getPrice());
        Glide.with(context).load(data.getDefault_image()).crossFade().into(holder.img); //加载图片
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicklistener != null) {
                    clicklistener.setOnItemClickListener(position,data);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists==null?0:lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView sub_title;
        private ImageView img;
        private TextView money;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            sub_title = (TextView) itemView.findViewById(R.id.sub_title);
            money = (TextView) itemView.findViewById(R.id.money);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public interface ItemOnClickListener<T>{
        void setOnItemClickListener( int position,T t);
    }

    private ItemOnClickListener clicklistener;

    public void setClicklistener(ItemOnClickListener clicklistener) {
        this.clicklistener = clicklistener;
    }
}
