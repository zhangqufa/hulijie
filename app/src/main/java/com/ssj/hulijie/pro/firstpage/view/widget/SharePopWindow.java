package com.ssj.hulijie.pro.firstpage.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.utils.DensityUtil;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.HashMap;
import java.util.List;

import static com.ssj.hulijie.R.id.btn_cancel;
import static com.ssj.hulijie.R.id.btn_done;
import static com.ssj.hulijie.R.id.share_cancel;


/**
 * Created by vic on 2016/4/8.
 */
public class SharePopWindow extends PopupWindow {

    private View mMenuView;
    private TextView share_cancel;
    private LinearLayout share_wechat, share_pengyouquan;
    private WheelView mainWheelView;
    private Activity cotext;

    public SharePopWindow(Activity context, final SelectCallBack callback) {
        super(context);
        this.cotext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.item_share_pop, null);
        share_wechat = (LinearLayout) mMenuView.findViewById(R.id.share_wechat);
        share_pengyouquan = (LinearLayout) mMenuView.findViewById(R.id.share_pengyouquan);
        share_cancel = (TextView) mMenuView.findViewById(R.id.share_cancel);

        //取消按钮
        share_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //设置按钮监听
        share_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.selectWechatCallBack();
                    dismiss();
                }
            }
        });
        share_pengyouquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.selectPengyouquanCallBack();
                    dismiss();
                }
            }
        });
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = newimg ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(new ColorDrawable());
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    }


    private SelectCallBack callback;

    public interface SelectCallBack {
        void selectWechatCallBack();

        void selectPengyouquanCallBack();
    }



}
