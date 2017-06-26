package com.ssj.hulijie.pro.mine.view;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.view.BaseFragment;
import com.ssj.hulijie.pro.firstpage.view.FirstPageFrament;

/**
 * Created by Administrator on 2017/3/26.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ImageView img;

    @Override
    public int getContentView() {
        return R.layout.frag_mine;
    }

    @Override
    public void initContentView(View viewContent) {
        img = (ImageView) viewContent.findViewById(R.id.img);
        Glide.with(getActivity()).load(FirstPageFrament.img[0])
                .into(img);

        viewContent.findViewById(R.id.login).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.login:
                intent = new Intent(getContext(), LoginActivity.class);

                break;
        }

        if (intent != null) {
            startActivity(intent);

        }
    }

}
