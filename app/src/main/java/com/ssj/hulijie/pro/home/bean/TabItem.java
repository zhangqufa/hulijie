package com.ssj.hulijie.pro.home.bean;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssj.hulijie.R;


/**
 * Created by vic on 2016/7/8.
 */
public class TabItem {
    private Context context;

    //正常情况下显示的图片
    private int imageNormal;
    //选中情况下显示的图片
    private int imagePress;
    //tab的名字 ,  String resource id
    private int title;
    private String titleString;

    private Class<? extends Fragment> fragmentClass;


    private View view;

    private ImageView xiaoxi;

    private ImageView imageView;
    private TextView textView;
    private Bundle bundle;

    public TabItem(Context context, int imageNormal, int imagePress, int title, Class<? extends Fragment> fragmentClass) {
        this.context = context;
        this.imageNormal = imageNormal;
        this.imagePress = imagePress;
        this.title = title;
        this.fragmentClass = fragmentClass;
    }

    public Class<? extends Fragment> getFragmentClass() {
        return fragmentClass;
    }

    public int getImageNormal() {
        return imageNormal;
    }

    public int getImagePress() {
        return imagePress;
    }

    public int getTitle() {
        return title;
    }

    public String getTitleString() {
        if (title == 0) {
            return "";
        }
        if (TextUtils.isEmpty(titleString)) {
            titleString = context.getString(title);
        }
        return titleString;
    }

    public Bundle getBundle() {

        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("title", getTitleString());
        return bundle;
    }

    //还需要提供一个切换Tab方法---改变Tab样式
    public void setChecked(boolean isChecked) {
        if (imageView != null) {
            if (isChecked) {
                imageView.setImageResource(imagePress);
            } else {
                imageView.setImageResource(imageNormal);
            }
        }
        if (textView != null && title != 0) {
            if (isChecked) {
                textView.setTextColor(context.getResources().getColor(R.color.comm_grey_555555));
            } else {
                textView.setTextColor(context.getResources().getColor(R.color.comm_grey_555555));
            }
        }
    }

    public void setXiaoxiGone() {
        xiaoxi.setVisibility(View.GONE);
    }

    public void setXiaoxiVisible() {
        xiaoxi.setVisibility(View.VISIBLE);
    }

    public View getView() {
        if (this.view == null) {
            this.view = LayoutInflater.from(context).inflate(R.layout.view_tab_indicator, null);
            this.imageView = (ImageView) this.view.findViewById(R.id.iv_tab);
            this.textView = (TextView) this.view.findViewById(R.id.tv_tab);
            this.xiaoxi = (ImageView) this.view.findViewById(R.id.iv);
            //判断资源是否存在,不在就隐藏
            if (this.title == 0) {
                this.textView.setVisibility(View.GONE);
            } else {
                this.textView.setVisibility(View.VISIBLE);
                this.textView.setText(getTitleString());
            }
            //绑定图片默认资源
            this.imageView.setImageResource(imageNormal);
        }
        return this.view;
    }
}
