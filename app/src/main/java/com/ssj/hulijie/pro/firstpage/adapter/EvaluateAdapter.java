package com.ssj.hulijie.pro.firstpage.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.ItemEvaluate;
import com.ssj.hulijie.pro.mine.adapter.Adapter;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppURL;
import com.ssj.hulijie.utils.DateUtil;
import com.ssj.hulijie.utils.DisplayUtils;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.widget.DividerGridItemDecoration;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.impl.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.EvaluateViewHolder> {

    private Context context;

    private List<ItemEvaluate.DataBean.RowsBean> lists;

    public EvaluateAdapter(Context context) {
        this.context = context;
    }

    public void setLists(List<ItemEvaluate.DataBean.RowsBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public EvaluateViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new EvaluateViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_evaluate, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(EvaluateViewHolder holder, final int i) {
        final ItemEvaluate.DataBean.RowsBean item = lists.get(i);
        holder.ll_seller_reply.setEnabled(true);
        holder.ll_seller_reply.setVisibility(View.VISIBLE);
        holder.nickname.setText(item.getBuyer_name());
        holder.time.setText(DateUtil.longToString(item.getCommentdate() * 1000));
        holder.evaluate_level.setRating(item.getEvaluation());
        holder.content.setText(item.getComment());
        if (!TextUtils.isEmpty(item.getReply())) {
            holder.seller_re.setText(item.getReply());
            holder.ll_seller_reply.setEnabled(false);
        } else {
            if (1 == SharedUtil.getPreferInt(SharedKey.IS_SELLER, 0)) {
            } else {
                holder.ll_seller_reply.setVisibility(View.GONE);
            }

        }
        String img_list_str = item.getImages();
        if (!TextUtils.isEmpty(img_list_str)) {
            List<String> images = new ArrayList<>(JSON.parseArray(img_list_str, String.class));
            final ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
            for (int j = 0; j < images.size(); j++) {
                AlbumFile albumFile = new AlbumFile();
                albumFile.setMediaType(AlbumFile.TYPE_IMAGE);
                String s = images.get(j);
                String img = s.substring(1).replace("\\", "");
                albumFile.setPath(AppURL.URL_IMAGE_REF + img);
                AppLog.Log("评价图片： " + AppURL.URL_IMAGE_REF + img);
                mAlbumFiles.add(albumFile);
            }
            holder.rv_img.setLayoutManager(new GridLayoutManager(context, 3));
            holder.rv_img.addItemDecoration(new DividerGridItemDecoration(DisplayUtils.dip2px(6), context.getResources().getColor(android.R.color.white)));
            int itemSize = (DisplayUtils.screenWidth - (DisplayUtils.dip2px(6) * 2)) / 3;
            Adapter mAdapter = new Adapter(context, itemSize, new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    previewImage(position, mAlbumFiles);
                }
            });
            holder.rv_img.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged(mAlbumFiles);
        }

        holder.ll_seller_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lintener != null) {
                    lintener.onSellReplyOnClickLintener(item, i);
                }
            }
        });
    }

    /**
     * Preview image, to album.
     */
    private void previewImage(int position, ArrayList<AlbumFile> mAlbumFiles) {
        if (mAlbumFiles == null || mAlbumFiles.size() == 0) {
        } else {
            Album.galleryAlbum(context)
                    .checkable(false)
                    .checkedList(mAlbumFiles)
                    .currentPosition(position)
                    .widget(
                            Widget.newDarkBuilder(context)
                                    .title("查看图片")
                                    .build()
                    )
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    class EvaluateViewHolder extends RecyclerView.ViewHolder {
        private ImageView pic;
        private TextView nickname;
        private TextView content;
        private AppCompatRatingBar evaluate_level;
        private RecyclerView rv_img;
        private TextView seller_re;
        private TextView time;
        private LinearLayout ll_seller_reply;

        public EvaluateViewHolder(View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
            nickname = itemView.findViewById(R.id.nickname);
            evaluate_level = itemView.findViewById(R.id.evaluate_level);
            rv_img = itemView.findViewById(R.id.rv_img);
            seller_re = itemView.findViewById(R.id.seller_re);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.content);
            ll_seller_reply = itemView.findViewById(R.id.ll_seller_reply);
        }
    }


    public interface SellReplyOnClickLintener<T> {
        void onSellReplyOnClickLintener(T t, int position);
    }

    private SellReplyOnClickLintener lintener;

    public void setLintener(SellReplyOnClickLintener lintener) {
        this.lintener = lintener;
    }
}
