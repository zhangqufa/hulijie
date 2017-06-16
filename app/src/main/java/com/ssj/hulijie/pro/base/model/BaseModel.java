package com.ssj.hulijie.pro.base.model;

import android.content.Context;

import com.ssj.hulijie.mvp.model.MvpModel;


/**
 * Created by Dream on 16/5/28.
 */
public abstract class BaseModel implements MvpModel {

    private Context context;

    public BaseModel(Context context) {
        this.context = context;
    }


}
