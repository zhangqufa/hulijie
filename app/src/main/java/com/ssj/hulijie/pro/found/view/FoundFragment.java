package com.ssj.hulijie.pro.found.view;

import android.view.View;

import com.sdu.didi.openapi.DIOpenSDK;
import com.sdu.didi.openapi.location.BaiduSdk;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.view.BaseFragment;
import com.ssj.hulijie.utils.AppLog;

import java.util.HashMap;
import java.util.Map;

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
                HashMap<String, String> map = null;
//                boolean login = DIOpenSDK.isLogin(getActivity());
//                if (!login) {
//                    map = new HashMap<>();
//                    map.put("finish", "home_page");
//                    DIOpenSDK.openPage(getActivity(), "login", map, null);
//                }
                map = new HashMap<>();
                DIOpenSDK.showDDPage(getActivity(), map);


                break;
        }
    }
}
