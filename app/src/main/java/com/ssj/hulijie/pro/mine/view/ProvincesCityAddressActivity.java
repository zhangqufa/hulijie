package com.ssj.hulijie.pro.mine.view;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.bean.AddrJsonBean;
import com.ssj.hulijie.utils.AppLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/7/16.
 */

public class ProvincesCityAddressActivity extends BaseActivity {
    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_province_city);
        TextView show_text = (TextView) findViewById(R.id.show_text);
        String json_addr = parseAddressJsonFile();
        AddrJsonBean addrJsonBean = JSON.parseObject(json_addr, AddrJsonBean.class);
        show_text.setText("addrJsonBean:" + addrJsonBean);


    }

    private String parseAddressJsonFile() {
        StringBuilder builder = new StringBuilder();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(getAssets().open("address.json"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

}
