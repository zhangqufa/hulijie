package com.ssj.hulijie.mvp.presenter.impl;


import com.ssj.hulijie.mvp.presenter.MvpPresenter;
import com.ssj.hulijie.mvp.view.MvpView;

/**
 * @author qufa
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
