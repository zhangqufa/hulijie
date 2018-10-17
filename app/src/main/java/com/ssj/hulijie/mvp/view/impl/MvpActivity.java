package com.ssj.hulijie.mvp.view.impl;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.mvp.view.MvpView;
import com.ssj.hulijie.utils.AppManager;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * 将我们的MVP架构集成到我们的Activity
 * MvpActivity---是MVP框架的
 *
 * @author qufa
 */
public abstract class MvpActivity<P extends MvpBasePresenter> extends SwipeBackActivity implements MvpView {

    //MVP架构P是动态的
    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAnim();
        AppManager.getAppManager().addActivity(this);
        presenter = bindPresenter();
        //我的presenter中介我是不是管理View---关联
        if (presenter != null) {
            presenter.attachView(this);
        }


    }


    /**
     * 具体的实现我不知道,我给别人实现
     *
     * @return
     */
    public abstract P bindPresenter();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁的时候---解除绑定
        if (presenter != null) {
            presenter.detachView();
        }
        AppManager.getAppManager().finishActivity(this);
    }
    protected int activityCloseEnterAnimation;
    protected int activityCloseExitAnimation;
    private void initAnim() {
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});

        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);

        activityStyle.recycle();

        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});

        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);

        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);

        activityStyle.recycle();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }
}
