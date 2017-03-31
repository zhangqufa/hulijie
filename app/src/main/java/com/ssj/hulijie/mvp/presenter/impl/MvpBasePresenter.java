package com.ssj.hulijie.mvp.presenter.impl;


import com.ssj.hulijie.mvp.presenter.MvpPresenter;
import com.ssj.hulijie.mvp.view.MvpView;

/**
 * Created by vic on 16/5/26.
 */
public abstract class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (view != null) {
            view = null;
        }
    }
}
