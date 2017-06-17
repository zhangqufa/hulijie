package com.ssj.hulijie.utils;

/**
 * Created by Administrator on 2017/6/17.
 */

public class ImageUrlFormat {

    public static String urlFormat(String pic) {
        if (!pic.contains(AppURL.URL_ROOT)) {
            pic = AppURL.URL_ROOT + pic;
        }
        return pic;

    }
}
