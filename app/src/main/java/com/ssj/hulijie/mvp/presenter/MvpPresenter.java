package com.ssj.hulijie.mvp.presenter;


import com.ssj.hulijie.mvp.view.MvpView;

/**
 * 中介
 *
 * @author qufa
 */
public interface MvpPresenter<V extends MvpView> {

    /**
     * 绑定找房子人(说白了就是我)
     *
     * @param view
     */
    void attachView(V view);

    /**
     * 解除绑定(说白了就是不和我联系了)
     */
    void detachView();
}
