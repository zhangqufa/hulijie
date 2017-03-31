package com.ssj.hulijie.utils;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssj.hulijie.R;


/**
 * Created by Administrator on 2016/3/14.
 */
public class TitlebarUtil {

    public static void initTitilBar(Context context, RelativeLayout title_bar_base, boolean leftShow, String center, View.OnClickListener listener) {
        if (Build.VERSION.SDK_INT >= 19) {
            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) title_bar_base.getLayoutParams();
            layoutParams1.topMargin = layoutParams1.topMargin + StatusBarUtil.getStatusBarHeight(context);
            title_bar_base.setLayoutParams(layoutParams1);
        }
        ((TextView) title_bar_base.findViewById(R.id.tv_navigation_center)).setText(center);

        if (!leftShow) {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setVisibility(View.GONE);
        } else {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setOnClickListener(listener);
        }
    }

    public static void inittoolBar(Context context, RelativeLayout title_bar_base, boolean leftShow, String center, View.OnClickListener listener) {
        ((TextView) title_bar_base.findViewById(R.id.tv_navigation_center)).setText(center);

        if (!leftShow) {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setVisibility(View.GONE);
        } else {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setOnClickListener(listener);
        }
    }

    public static void inittoolBar(Context context, RelativeLayout title_bar_base, boolean leftShow, String center, boolean rightShow, String right, View.OnClickListener listener, View.OnClickListener listener_rigth) {
        ((TextView) title_bar_base.findViewById(R.id.tv_navigation_center)).setText(center);

        if (!leftShow) {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setVisibility(View.GONE);
        } else {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setOnClickListener(listener);
        }
        if (rightShow) {
            TextView tv_right = (TextView) title_bar_base.findViewById(R.id.tv_navigation_right);
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText(right);
            tv_right.setOnClickListener(listener_rigth);
        }
    }

    public static void inittoolBar(Context context, RelativeLayout title_bar_base, boolean leftShow, String center, boolean rightShow, int imgRes, View.OnClickListener listener, View.OnClickListener listener_rigth) {
        ((TextView) title_bar_base.findViewById(R.id.tv_navigation_center)).setText(center);

        if (!leftShow) {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setVisibility(View.GONE);
        } else {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setOnClickListener(listener);
        }
        if (rightShow) {
            ImageView iv_right = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_right);
            iv_right.setVisibility(View.VISIBLE);
            iv_right.setImageResource(imgRes);
            iv_right.setOnClickListener(listener_rigth);
        }
    }

    public static void inittoolBar(Context context, RelativeLayout title_bar_base, boolean leftShow, String center, int bgRes, int contentRes, int leftImgRes, boolean rightShow, int rightRes, View.OnClickListener listener, View.OnClickListener listener2) {
        ((TextView) title_bar_base.findViewById(R.id.tv_navigation_center)).setText(center);
        if (contentRes != 0) {
            ((TextView) title_bar_base.findViewById(R.id.tv_navigation_center)).setTextColor(context.getResources().getColor(contentRes));
        }
        if (bgRes != 0) {
            title_bar_base.setBackgroundResource(bgRes);
        }

        if (!leftShow) {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);

            imageView.setVisibility(View.GONE);
        } else {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            if (leftImgRes != 0) {

                imageView.setImageResource(leftImgRes);
            }
            imageView.setBackground(null);
            imageView.setOnClickListener(listener);
        }
        if (rightShow) {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_right);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(rightRes);
            imageView.setBackground(null);
            imageView.setOnClickListener(listener2);
        }
    }



    public static void initTitilBar(Context context, RelativeLayout title_bar_base, boolean leftShow, String center, String rightText, View.OnClickListener listener1, View.OnClickListener listener2) {
        if (Build.VERSION.SDK_INT >= 19) {
            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) title_bar_base.getLayoutParams();
            layoutParams1.topMargin = layoutParams1.topMargin + StatusBarUtil.getStatusBarHeight(context);
            title_bar_base.setLayoutParams(layoutParams1);
        }
        ((TextView) title_bar_base.findViewById(R.id.tv_navigation_center)).setText(center);

        if (!leftShow) {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setVisibility(View.GONE);
        } else {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setOnClickListener(listener1);
        }
        TextView tv_navigation_right = (TextView) title_bar_base.findViewById(R.id.tv_navigation_right);
        tv_navigation_right.setVisibility(View.VISIBLE);
        tv_navigation_right.setText(rightText);
        tv_navigation_right.setOnClickListener(listener2);

    }

    public static void initTitilBar(Context context, RelativeLayout title_bar_base, boolean leftShow, String center, boolean rightImgShow, int rightSrc, View.OnClickListener listener1, View.OnClickListener listener2) {
        if (Build.VERSION.SDK_INT >= 19) {
            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) title_bar_base.getLayoutParams();
            layoutParams1.topMargin = layoutParams1.topMargin + StatusBarUtil.getStatusBarHeight(context);
            title_bar_base.setLayoutParams(layoutParams1);
        }
        ((TextView) title_bar_base.findViewById(R.id.tv_navigation_center)).setText(center);

        if (!leftShow) {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setVisibility(View.GONE);
        } else {
            ImageView imageView = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_back);
            imageView.setOnClickListener(listener1);
        }
        if (rightImgShow) {
            ImageView iv_navigation_right = (ImageView) title_bar_base.findViewById(R.id.iv_navigation_right);
            iv_navigation_right.setVisibility(View.VISIBLE);
            iv_navigation_right.setImageResource(rightSrc);
            iv_navigation_right.setOnClickListener(listener2);
        }

    }


    public static void setToolBarHeight(Context context, RelativeLayout title_bar_base) {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) title_bar_base.getLayoutParams();
        layoutParams1.topMargin = layoutParams1.topMargin + StatusBarUtil.getStatusBarHeight(context);
        title_bar_base.setLayoutParams(layoutParams1);
    }


    /**
     * 还原toolbar高度设置  toolbar.height（之前加过，还原减掉）-status.height
     *
     * @param toolbar
     * @param flags   0:LinearLayout  1: RelativeLayout
     */
    public static void resetToolbarHeight(Context context, Toolbar toolbar, int flags) {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        if (flags != 0 && flags != 1) {
            return;
        }
        switch (flags) {
            case 0: //linearlayout
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
                layoutParams.height = layoutParams.height - StatusBarUtil.getStatusBarHeight(context);
                toolbar.setLayoutParams(layoutParams);
                toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getTop() - StatusBarUtil.getStatusBarHeight(context), toolbar.getPaddingRight(), toolbar.getPaddingBottom());
                break;

            case 1: //RelativeLayout
                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
                layoutParams1.height = layoutParams1.height - StatusBarUtil.getStatusBarHeight(context);
                toolbar.setLayoutParams(layoutParams1);
                toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getTop() - StatusBarUtil.getStatusBarHeight(context), toolbar.getPaddingRight(), toolbar.getPaddingBottom());
                break;
        }
    }


    /**
     * 个人中心，使父窗口padding statusBar高度
     *
     * @param layout
     */
    public static void setLayoutHeight(Context context, RelativeLayout layout) {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        int paddingTop = layout.getPaddingTop() + StatusBarUtil.getStatusBarHeight(context);
        layout.setPadding(layout.getPaddingLeft(), paddingTop, layout.getPaddingRight(), layout.getPaddingBottom());
    }

    public static void setLayoutHeight(Context context, LinearLayout layout) {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.topMargin = layoutParams.topMargin + StatusBarUtil.getStatusBarHeight(context);
        layout.setLayoutParams(layoutParams);


    }

    public static void setLayoutHeight(Context context, WebView webView) {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) webView.getLayoutParams();
        layoutParams.topMargin = layoutParams.topMargin + StatusBarUtil.getStatusBarHeight(context);
        webView.setLayoutParams(layoutParams);


    }

//    public static void initToolbar(final Activity activity, String centerText) {
//
//        Toolbar toolbar_base = (Toolbar) activity.findViewById(R.id.toolbar_base);
//        ((TextView) toolbar_base.findViewById(R.id.tv_navigation_center)).setText(centerText);
//        ((ImageView) toolbar_base.findViewById(R.id.iv_navigation_back)).setOnClickListener(newimg View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.finish();
//            }
//        });
//        initTitilBar(activity, toolbar_base, 0);
//
//    }

//    public static void initToolbar(final Activity activity, String centerText, String rightText, View.OnClickListener onClickListener) {
//
//        Toolbar toolbar_base = (Toolbar) activity.findViewById(R.id.toolbar_base);
//        ((TextView) toolbar_base.findViewById(R.id.tv_navigation_center)).setText(centerText);
//
//        TextView tv_right = (TextView) toolbar_base.findViewById(R.id.tv_navigation_right);
//        tv_right.setVisibility(View.VISIBLE);
//        tv_right.setText(rightText);
//        tv_right.setOnClickListener(onClickListener);
//
//        ((ImageView) toolbar_base.findViewById(R.id.iv_navigation_back)).setOnClickListener(newimg View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.finish();
//            }
//        });
//
//        initTitilBar(activity, toolbar_base, 0);
//
//    }


}
