package com.ssj.hulijie.pro.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.adapter.CityAdatper;
import com.ssj.hulijie.pro.mine.adapter.ProvinceAdapter;
import com.ssj.hulijie.pro.mine.bean.AddrJsonBean;
import com.ssj.hulijie.pro.mine.bean.CityGridItem;
import com.ssj.hulijie.pro.mine.bean.ProvinceItem;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.TitlebarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/7/16.
 */

public class ProvincesCityAddressActivity extends BaseActivity {
    private AddrJsonBean addrJsonBean;
    private ProvinceAdapter adapter;
    private ArrayList<ProvinceItem> lists;
    private CityAdatper adapter_city;
    private  ListView lv_city;

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_province_city);
        String json_addr = parseAddressJsonFile();
        addrJsonBean = JSON.parseObject(json_addr, AddrJsonBean.class);
        initToolbar();
        initView();

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(CityGridItem event) {
//        AppToast.ShowToast(event.getProvince()+"_"+event.getCity());

        Intent intent = new Intent();
        intent.putExtra("event", event);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void initView() {
        RecyclerView rv_province = (RecyclerView) findViewById(R.id.rv_province);
        adapter = new ProvinceAdapter(this, getData());
        rv_province.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv_province.setLayoutManager(new LinearLayoutManager(this));
        rv_province.setAdapter(adapter);
        adapter.setOnItemClickLisener(clickListener);


        lv_city = (ListView) findViewById(R.id.lv_city);
        adapter_city = new CityAdatper(this, addrJsonBean.getProvinces());
        lv_city.setAdapter(adapter_city);

    }


    private ProvinceAdapter.OnItemClickLisener clickListener = new ProvinceAdapter.OnItemClickLisener() {
        @Override
        public void onItemClickLisener(int position) {
            lv_city.setSelection(position);
            for (ProvinceItem item : lists) {
                item.setSelect(false);
            }
            lists.get(position).setSelect(true);
            adapter.setLists(lists);
        }
    };




    /**
     * 分离出省份
     *
     * @return
     */
    private List<ProvinceItem> getData() {
        lists = new ArrayList<>();
        List<AddrJsonBean.ProvincesBean> provinces = addrJsonBean.getProvinces();
        for (AddrJsonBean.ProvincesBean provincesBean : provinces) {
            ProvinceItem item = new ProvinceItem();
            String name = provincesBean.getName();
            item.setProvince(name);
            lists.add(item);

        }
        lists.get(0).setSelect(true);
        return lists;
    }

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "选择地址", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }


    /**
     * 读取assets文件 下的 地址数据
     *
     * @return
     */
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
