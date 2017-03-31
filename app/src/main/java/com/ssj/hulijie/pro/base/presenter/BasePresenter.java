package com.ssj.hulijie.pro.base.presenter;

import android.content.Context;

import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.model.BaseModel;


/**
 * Created by Dream on 16/5/28.
 */
public abstract class BasePresenter<M extends BaseModel> extends MvpBasePresenter {

    private Context context;
    private M model;

    public BasePresenter(Context context) {
        this.context = context;
        this.model = bindModel();
    }

    public Context getContext() {
        return context;
    }


    public M getModel() {
        return model;
    }

    public abstract M bindModel();

    public interface OnUIThreadListener<T> {
        void onResult(T result);
    }
}
