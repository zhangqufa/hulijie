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
import com.ssj.hulijie.pro.firstpage.bean.ItemCategoryMain;
import com.ssj.hulijie.utils.StringFormat;

import java.util.List;

/**
 * @author qufa
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private List<ItemCategoryMain.DataBean.RowsBean> lists;

    private Context context;
    private static final String END_SUFFIX = "...";
    private static final int FONT_COUNT = 23;

    public CategoryListAdapter(Context context) {
        this.context = context;
    }

    public void setLists(List<ItemCategoryMain.DataBean.RowsBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.item_first_page_main_list_notxt, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        final ItemCategoryMain.DataBean.RowsBean data = lists.get(position);
        String goods_name = data.getGoods_name();
        holder.title.setText(StringFormat.toMore(goods_name,FONT_COUNT));
        holder.money.setText(data.getPrice());
        //加载图片
        Glide.with(context).load(data.getDefault_image()).crossFade().into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicklistener != null) {
                    clicklistener.setOnItemClickListener(position, data);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView img;
        private TextView money;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            money = (TextView) itemView.findViewById(R.id.money);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public interface ItemOnClickListener<T> {
        void setOnItemClickListener(int position, T t);
    }

    private ItemOnClickListener clicklistener;

    public void setClicklistener(ItemOnClickListener clicklistener) {
        this.clicklistener = clicklistener;
    }
}
