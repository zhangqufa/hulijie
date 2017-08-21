package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ssj.hulijie.R;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.ImageUrlFormat;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class DetailImageAdapter extends RecyclerView.Adapter <DetailImageAdapter.DetailImageViewHolder>{
   private Context context;
    private List<String> lists;
    private int width ;

    public DetailImageAdapter(Context context,int width) {
        this.context = context;
        this.width = width;
    }

    public void setLists(List<String> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public DetailImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_detail_img, parent, false);
        return new DetailImageViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final DetailImageViewHolder holder, int position) {
        String url = lists.get(position);
        final String url_format = ImageUrlFormat.urlFormat(url);
        AppLog.Log("url_images: "+url_format);
        Glide.with(context)
                .load(url_format)
                .asBitmap()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading_error)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        showPic(resource, holder.detail_descript_img);
                    }
                });

    }

    /**
     * 图片 按照ImageView宽度 来动态设置ImageView的高度
     * @param resource 图片资源
     * @param iv        图片控件
     */
    private void showPic(Bitmap resource,ImageView iv) {
        int imageWidth = resource.getWidth();
        int imageHeight = resource.getHeight();
        LinearLayout.LayoutParams para = (LinearLayout.LayoutParams) iv.getLayoutParams();
        para.height = width * imageHeight / imageWidth;
        para.width = width;
        iv.setLayoutParams(para);
        iv.setImageBitmap(resource);

    }

    @Override
    public int getItemCount() {
        return lists==null?0:lists.size();
    }

    class DetailImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView detail_descript_img;

        public DetailImageViewHolder(View itemView) {
            super(itemView);
            detail_descript_img = (ImageView) itemView.findViewById(R.id.detail_descript_img);
        }
    }
}
