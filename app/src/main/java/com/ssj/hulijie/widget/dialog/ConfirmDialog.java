/*
 * Copyright (c) 2015, ParkTech Corporation, All Rights Reserved
 */
package com.ssj.hulijie.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.utils.DensityUtil;


public class ConfirmDialog extends BaseDialog implements View.OnClickListener {
    private TextView tv_dlg_title, tv_dlg_content;
    private GoOther goOther;
    private String title;
    private String content;


    // 退出dialog窗口
    public ConfirmDialog(Context context, GoOther goOther, String content) {
        super(context, View.inflate(context, R.layout.dlg_confirm_cancle_single_button, null), new LayoutParams(DensityUtil.dip2px(context, 300), DensityUtil.dip2px(
                context, 121)));
        this.goOther = goOther;
        this.content = content;
        initExit();
    }

    private void initExit() {
        tv_dlg_content = (TextView) view.findViewById(R.id.tv_dlg_content);
        tv_dlg_content.setText(content);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                goOther.go();
                dismiss();
                break;
        }
    }

    public interface GoOther {
        void go();
    }

}
