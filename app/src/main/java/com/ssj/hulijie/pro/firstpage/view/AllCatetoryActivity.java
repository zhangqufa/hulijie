package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vic_zhang .
 * on 2017/4/1
 */

public class AllCatetoryActivity extends BaseActivity implements View.OnClickListener {

    private ListView mListView;
    private ListViewAdapter mListViewAdapter;
    private ArrayList<ArrayList<HashMap<String, Object>>> mArrayList;


    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_catetory);
        init();
    }


    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.listView);
        initData();
        mListViewAdapter = new ListViewAdapter(mArrayList, this);
        mListView.setAdapter(mListViewAdapter);
    }

    private void initData() {
        mArrayList = new ArrayList<>();
        HashMap<String, Object> hashMap = null;
        ArrayList<HashMap<String, Object>> arrayListForEveryGridView = null;

        for (int i = 0; i < 10; i++) {
            arrayListForEveryGridView = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                hashMap = new HashMap<>();
                hashMap.put("content", "i=" + i + " ,j=" + j);
                arrayListForEveryGridView.add(hashMap);
            }
            mArrayList.add(arrayListForEveryGridView);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back:
                finish();
                break;
        }
    }
}
