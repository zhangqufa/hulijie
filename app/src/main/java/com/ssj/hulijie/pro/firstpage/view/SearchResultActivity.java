package com.ssj.hulijie.pro.firstpage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.FirstPageMainListAdapter;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainHeaderList;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.pro.firstpage.presenter.FirstPagePresenter;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.TitlebarUtil;
import com.ssj.hulijie.widget.recylerview.BaseRecyclerAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrHandler2;

import static android.R.attr.data;
import static android.R.id.list;
import static com.ssj.hulijie.R.id.rv_first_page_main_list;
import static com.ssj.hulijie.base.HljAppliation.context;


/**
 * Created by Administrator on 2017/5/15.
 */

public class SearchResultActivity extends BaseActivity implements View.OnClickListener {
    private TextView nav_center_title;
    private FirstPageMainListAdapter adapter;
    private ItemFirstPageMainHeaderList item;
    private PtrClassicFrameLayout ptr;
    private FirstPagePresenter presenter;
    private int page=1;

    private List<ItemFirstPageMainList> lists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_result);
        item = getIntent().getParcelableExtra("item");
        initToolbar();
        initView();
        initData();
    }

    private void initData() {
        lists = new ArrayList<>();
        getData();

    }

    private void initToolbar() {
        String center = "";
        if (item != null && !TextUtils.isEmpty(item.getName())) {
            center = item.getName();
        }
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, center, android.R.color.white, 0, R.mipmap.back__btn_re, false,0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);

    }

    private void initView() {
        //init RecyclerView
        RecyclerView search_result_rv = (RecyclerView) findViewById(R.id.search_result_rv);
        search_result_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FirstPageMainListAdapter(context);
        search_result_rv.setAdapter(adapter);
        search_result_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        search_result_rv.addOnScrollListener(recyclerView_listener);
        adapter.setOnItemClickListener(item_click);
        ptr = (PtrClassicFrameLayout) findViewById(R.id.ptr_search_result);
        ptr.setMode(PtrFrameLayout.Mode.LOAD_MORE);
        ptr.setPtrHandler(ptrHandler);

    }

    private BaseRecyclerAdapter.OnItemClickListener item_click = new BaseRecyclerAdapter.OnItemClickListener<ItemFirstPageMainList>() {
        @Override
        public void onItemClick(int position, ItemFirstPageMainList item) {
            Intent intent = new Intent(context, DetailInfoActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
        }
    };

    /**
     * 监听recylerview 是否滑动到底部
     */
    private RecyclerView.OnScrollListener recyclerView_listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (isSlideToBottom(recyclerView)) {
                loadMore();
            }
        }
    };

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    /**
     * ptr data update
     */
    private PtrHandler ptrHandler = new PtrDefaultHandler2() {
        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {

            loadMore();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {

        }
    };
    private void loadMore(){
        page++;
        getData();
    }

    private void getData() {
        if (item == null) {
            return;
        }
        presenter.getFirstForCategoryPresenter(this, item.getId(), page, new BasePresenter.OnUIThreadListener<List<ItemFirstPageMainList>>() {
            @Override
            public void onResult(List<ItemFirstPageMainList> result, int return_code) {
                if (result != null) {
                    if (result.size() > 0) {

                        for (int i = 0; i < result.size(); i++) {
                            ItemFirstPageMainList item = result.get(i);
                            lists.add(item);
                        }
                        adapter.addDatas(lists);
                    } else {
                        AppToast.ShowToast("已加载全部商品");
                    }
                }
                ptr.refreshComplete();

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
        }
    }
}
