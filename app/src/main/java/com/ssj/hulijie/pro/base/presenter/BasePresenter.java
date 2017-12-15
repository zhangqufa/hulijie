package com.ssj.hulijie.pro.base.presenter;

import android.content.Context;

import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.model.BaseModel;


/**
 * @author qufa
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
        /**
         * @param result 返回数据
         */
        void onResult(T result);
    }
}
