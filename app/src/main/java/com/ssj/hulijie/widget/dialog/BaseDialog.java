/*
 * Copyright (c) 2016, HuiZhe Corporation, All Rights Reserved
 */
package com.ssj.hulijie.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.ssj.hulijie.R;


public class BaseDialog extends Dialog {
    Context context;
    View view;
    private LayoutParams layoutParams;

    public BaseDialog(Context context, View view, LayoutParams layoutParams) {
        super(context, R.style.BaseDialog);
        this.context = context;
        this.view = view;
        this.layoutParams = layoutParams;
        initBase();
    }

    private void initBase() {
        setContentView(view, layoutParams);
        setCanceledOnTouchOutside(false);
    }


}
