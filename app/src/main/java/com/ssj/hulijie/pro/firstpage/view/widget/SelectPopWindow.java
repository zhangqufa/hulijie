package com.ssj.hulijie.pro.firstpage.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.HashMap;
import java.util.List;


/**
 * Created by vic on 2016/4/8.
 */
public class SelectPopWindow extends PopupWindow {

    private View mMenuView;
    private TextView btn_done,btn_cancel;
    private WheelView mainWheelView;
    private Activity cotext;

    public SelectPopWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        this.cotext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.item_pop_select_date_time, null);
        btn_done = (TextView) mMenuView.findViewById(R.id.btn_done);
        btn_cancel = (TextView) mMenuView.findViewById(R.id.btn_cancel);
        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //设置按钮监听
        btn_done.setOnClickListener(itemsOnClick);
        btn_cancel.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(dip2px(context, 280));
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

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private List<String> firt_strs;
    private HashMap<String, List<String>> map;
    private SelectCallBack callback;

    public interface SelectCallBack{
        void selectDataCallBack(String data);
        void selectTimeCallBack(String time);
    }

    public void setCallback(SelectCallBack callback) {
        this.callback = callback;
    }

    public List<String> getFirt_strs() {
        return firt_strs;
    }

    public void setFirt_strs(List<String> firt_strs) {
        this.firt_strs = firt_strs;
    }

    public HashMap<String, List<String>> getMap() {
        return map;
    }

    public void setMap(HashMap<String, List<String>> map) {
        this.map = map;
    }

    /**
     * 联动WheelView
     */
    public void initWheel1() {
        mainWheelView = (WheelView) mMenuView.findViewById(R.id.main_wheelview);
        mainWheelView.setWheelAdapter(new ArrayWheelAdapter(cotext));
        mainWheelView.setSkin(WheelView.Skin.Holo);
        mainWheelView.setWheelData(firt_strs);
        mainWheelView.setWheelSize(5);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;
        style.holoBorderColor = cotext.getResources().getColor(R.color.comm_grey_C8C4C2);
        style.selectedTextColor = cotext.getResources().getColor(R.color.colorPrimary);
        mainWheelView.setStyle(style);
        mainWheelView.setOnWheelItemSelectedListener(listener);

        WheelView subWheelView = (WheelView) mMenuView.findViewById(R.id.sub_wheelview);
        subWheelView.setWheelSize(5);
        subWheelView.setWheelAdapter(new ArrayWheelAdapter(cotext));
        subWheelView.setSkin(WheelView.Skin.Holo);
        subWheelView.setWheelData(map.get(firt_strs.get(mainWheelView.getSelection())));
        subWheelView.setStyle(style);
        mainWheelView.join(subWheelView);
        mainWheelView.joinDatas(map);
        subWheelView.setOnWheelItemSelectedListener(listener1);

    }



    private WheelView.OnWheelItemSelectedListener listener = new WheelView.OnWheelItemSelectedListener() {
        @Override
        public void onItemSelected(int position, Object o) {
            if (callback != null) {
                callback.selectDataCallBack(firt_strs.get(position));
            }
        }
    };

    private WheelView.OnWheelItemSelectedListener listener1 = new WheelView.OnWheelItemSelectedListener() {
        @Override
        public void onItemSelected(int position, Object o) {
            if (callback != null) {
                callback.selectTimeCallBack(map.get(firt_strs.get(mainWheelView.getCurrentPosition())).get(position));
            }
        }
    };


}
