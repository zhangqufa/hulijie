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
import com.ssj.hulijie.utils.DensityUtil;
import com.ssj.hulijie.utils.DisplayUtils;
import com.ssj.hulijie.utils.ImageUrlFormat;
import com.ssj.hulijie.widget.recylerview.BaseRecyclerAdapter;

/**
 * @author vic_zhang .
 *         on 2018/5/29
 */

public class DetailImageHeadAdapter extends BaseRecyclerAdapter<String> {

    private Context context;
    private int width;

    public DetailImageHeadAdapter(Context context) {
        this.context = context;
        this.width = DisplayUtils.screenWidth - DensityUtil.dip2px(context, 20);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_detail_img, parent, false);
        return new DetailImageHeadViewHolder(item);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, String data) {
        final String url_format = ImageUrlFormat.urlFormat(data);
        AppLog.Log("url_images: " + url_format);
        if (viewHolder instanceof DetailImageHeadAdapter.DetailImageHeadViewHolder) {
            final DetailImageHeadViewHolder vh = (DetailImageHeadViewHolder) viewHolder;
            Glide.with(context)
                    .load(url_format)
                    .asBitmap()
                    .placeholder(R.mipmap.loading_detail)
                    .error(R.mipmap.loading_detail)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            showPic(resource, vh.detail_descript_img);
                        }
                    });
        }

    }

    /**
     * 图片 按照ImageView宽度 来动态设置ImageView的高度
     *
     * @param resource 图片资源
     * @param iv       图片控件
     */
    private void showPic(Bitmap resource, ImageView iv) {
        int imageWidth = resource.getWidth();
        int imageHeight = resource.getHeight();
        LinearLayout.LayoutParams para = (LinearLayout.LayoutParams) iv.getLayoutParams();
        para.height = width * imageHeight / imageWidth;
        para.width = width;
        iv.setLayoutParams(para);
        iv.setImageBitmap(resource);

    }

    class DetailImageHeadViewHolder extends RecyclerView.ViewHolder {
        private ImageView detail_descript_img;

        public DetailImageHeadViewHolder(View itemView) {
            super(itemView);
            detail_descript_img = (ImageView) itemView.findViewById(R.id.detail_descript_img);
        }
    }
}
