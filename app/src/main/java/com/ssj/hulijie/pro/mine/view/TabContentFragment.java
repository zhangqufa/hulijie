package com.ssj.hulijie.pro.mine.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.view.BaseFragment;

/**
 * Created by vic_zhang .
 * on 2017/7/19
 */

public class TabContentFragment extends BaseFragment {
    @Override
    public int getContentView() {
        return R.layout.temp_tv;
    }

    @Override
    public void initContentView(View viewContent) {
        TextView viewById = (TextView) viewContent.findViewById(R.id.temp_tv);
        viewById.setText(getArguments().getString(EXTRA_CONTENT));
    }


    private static final String EXTRA_CONTENT = "content";

    public static TabContentFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        TabContentFragment tabContentFragment = new TabContentFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }


}
