package com.ssj.hulijie.album;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ssj.hulijie.R;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

/**
 * @author vic_zhang .
 *         on 2018/6/15
 */

public class MediaLoader implements AlbumLoader {

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.mipmap.loading_logo)
                .placeholder(R.mipmap.loading_logo)
                .crossFade()
                .into(imageView);
    }
}
