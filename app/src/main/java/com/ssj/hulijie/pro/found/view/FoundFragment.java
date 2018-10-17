package com.ssj.hulijie.pro.found.view;

import android.view.View;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.view.BaseFragment;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/26.
 */

public class FoundFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public int getContentView() {
        return R.layout.frag_found;
    }

    @Override
    public void initContentView(View viewContent) {
        viewContent.findViewById(R.id.toDidi).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toDidi:
                break;
        }
    }
}
