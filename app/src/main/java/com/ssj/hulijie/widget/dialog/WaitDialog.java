/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ssj.hulijie.widget.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.ssj.hulijie.R;
import com.ssj.hulijie.utils.DensityUtil;


/**
 * Created in Oct 23, 2015 1:19:04 PM.
 *
 * @author YOLANDA;
 */
public class WaitDialog extends BaseDialog {
    private AnimationDrawable drawable = null;


    public WaitDialog(Context context) {
        super(context, View.inflate(context, R.layout.wait_diog, null), new ViewGroup.LayoutParams(DensityUtil.dip2px(context, 100), DensityUtil.dip2px(
                context, 100)));
        initView();
    }


    private void initView() {
//        ImageView iv_wait_dialog = (ImageView) findViewById(R.id.iv_wait_dialog);
//        drawable = (AnimationDrawable) iv_wait_dialog.getBackground();
//        drawable.start();
    }

}

