package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.ListViewAdapter;
import com.ssj.hulijie.pro.firstpage.bean.CatetoryItem;
import com.ssj.hulijie.pro.firstpage.presenter.AllCatetoryPresenter;

import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/4/1
 */

public class AllCatetoryActivity extends BaseActivity implements View.OnClickListener {

    private ListView mListView;
    private ListViewAdapter mListViewAdapter;
    //    private ArrayList<ArrayList<HashMap<String, Object>>> mArrayList;
    private AllCatetoryPresenter myPresenter;


    @Override
    public MvpBasePresenter bindPresenter() {
        myPresenter = new AllCatetoryPresenter(this);
        return myPresenter;
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
        mListViewAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mListViewAdapter);

    }

    private void initData() {

        myPresenter.getAllcatetoryPresenter(this, new BasePresenter.OnUIThreadListener<List<CatetoryItem>>() {
            @Override
            public void onResult(List<CatetoryItem> result) {
                if (result != null && result.size() > 0) {
                    mListViewAdapter.setLists(result);
                }
            }
        });
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
