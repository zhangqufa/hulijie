package com.ssj.hulijie.pro.base.view.navigation;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dream on 16/5/27.
 * 构建ToolBar---Builder设计模式
 */
public interface NavigationBuilder {

    NavigationBuilder setTitle(String title);

    NavigationBuilder setTitle(int title);

    NavigationBuilder setTitleIcon(int iconRes);

    NavigationBuilder setLeftIcon(int iconRes);

    NavigationBuilder setRightIcon(int iconRes);

    NavigationBuilder setLeftIconOnClickListener(View.OnClickListener onClickListener);

    NavigationBuilder setRightIconOnClickListener(View.OnClickListener onClickListener);

    void createAndBind(ViewGroup parent);

}
