package com.ssj.hulijie.pro.firstpage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.CategoryListAdapter;
import com.ssj.hulijie.pro.firstpage.adapter.FirstPageMainListAdapter;
import com.ssj.hulijie.pro.firstpage.bean.ItemCategoryMain;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.pro.firstpage.bean.ItemRemmendList;
import com.ssj.hulijie.pro.firstpage.presenter.FirstPagePresenter;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.RefreshStatues;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static android.R.attr.data;
import static com.ssj.hulijie.base.HljAppliation.context;


/**
 * Created by Administrator on 2017/5/15.
 */

public class SearchResultEditActivity extends BaseActivity implements View.OnClickListener {
    private TextView nav_center_title;
    private CategoryListAdapter adapter;
    private String key;
    private TextView iv_navigation_center;
    private FirstPagePresenter presenter;
    private PtrClassicFrameLayout ptr;
    private int page = 1;
    private List<ItemCategoryMain.DataBean.RowsBean> lists = new ArrayList<>();
    private XRecyclerView mRecyclerView;
    private View text_empty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_result_edit);

        key = getIntent().getStringExtra("key");
        initToolbar();
        initView();
        initData();

    }


    private void initToolbar() {
        findViewById(R.id.iv_navigation_back).setOnClickListener(this);
        iv_navigation_center = (TextView) findViewById(R.id.iv_navigation_center);
        iv_navigation_center.setOnClickListener(this);
        iv_navigation_center.setText(key);

    }


    private void initView() {

        //empty view
        text_empty = (View) findViewById(R.id.text_empty);

        //init RecyclerView
        mRecyclerView = (XRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

//        mRecyclerView.addItemDecoration(new SimplePaddingDecoration(this));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {
                page++;
                getData(RefreshStatues.LOADMORE);
            }
        });

        adapter = new CategoryListAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setClicklistener(item_click);

    }

    private CategoryListAdapter.ItemOnClickListener item_click = new CategoryListAdapter.ItemOnClickListener<ItemCategoryMain.DataBean.RowsBean>() {
        @Override
        public void setOnItemClickListener(int position, ItemCategoryMain.DataBean.RowsBean item) {
            Intent intent = new Intent(context, DetailInfoActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
        }
    };

    private void initData() {
        page = 1;
        lists.clear();
        adapter.notifyDataSetChanged();
        getData(RefreshStatues.REFRESH);
    }


    /**
     * 测试数据  首页主数据
     *
     * @return
     */
    private void getData(final RefreshStatues statues) {
        presenter.getFirstForCategoryPresenter(this, "", key, page, new BasePresenter.OnUIThreadListener<ItemCategoryMain>() {
            @Override
            public void onResult(ItemCategoryMain result) {
                if (result != null) {
                    ItemCategoryMain.DataBean data = result.getData();
                    List<ItemCategoryMain.DataBean.RowsBean> rows = data.getRows();
                    int totalcount = data.getCount();
                    if (rows.size() > 0) {

                        for (int i = 0; i < rows.size(); i++) {
                            ItemCategoryMain.DataBean.RowsBean item = rows.get(i);
                            lists.add(item);
                        }
                        adapter.setLists(lists);

                        if (statues == RefreshStatues.REFRESH) {
                            mRecyclerView.refreshComplete();

                        } else if (statues == RefreshStatues.LOADMORE) {
                            mRecyclerView.loadMoreComplete();
                        }
                        if (rows.size() < 10 && lists.size() > 5 || lists.size() == totalcount && lists.size() > 5) {
                            mRecyclerView.setNoMore(true);
                        }
                    }
                    if (lists.size()==0){
                        mRecyclerView.setEmptyView(text_empty);
                    }
                }
            }
        });
    }

    @Override
    public MvpBasePresenter bindPresenter() {
        presenter = new FirstPagePresenter(this);
        return presenter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_navigation_back:
            case R.id.iv_navigation_center:
                finish();
                break;
        }
    }
}
