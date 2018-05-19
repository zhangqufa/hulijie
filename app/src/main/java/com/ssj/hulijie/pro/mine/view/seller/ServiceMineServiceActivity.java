package com.ssj.hulijie.pro.mine.view.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.CategoryListAdapter;
import com.ssj.hulijie.pro.firstpage.bean.ItemCategoryMain;
import com.ssj.hulijie.pro.firstpage.view.DetailInfoActivity;
import com.ssj.hulijie.pro.mine.presenter.ServicePresenter;
import com.ssj.hulijie.utils.RefreshStatues;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.util.ArrayList;
import java.util.List;

import static com.ssj.hulijie.base.HljAppliation.context;

/**
 * @author vic_zhang .
 *         on 2018/5/19
 */

public class ServiceMineServiceActivity extends BaseActivity<ServicePresenter> {
    private TextView nav_center_title;
    private CategoryListAdapter adapter;
    private int page = 1;

    private List<ItemCategoryMain.DataBean.RowsBean> lists = new ArrayList<>();
    private XRecyclerView mRecyclerView;
    private View text_empty;

    @Override
    public ServicePresenter bindPresenter() {
        return new ServicePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_service);
        initToolbar();
        initView();
        initData();
    }

    private void initData() {
        page = 1;
        lists.clear();
        adapter.notifyDataSetChanged();
        getData(RefreshStatues.REFRESH);

    }

    private void initToolbar() {
        String center = "我的服务";
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, center, android.R.color.white, 0, R.mipmap.back__btn_re, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);

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


    private void getData(final RefreshStatues statues) {
        presenter.getServiceMinePresenter(this, SharedUtil.getPreferStr(SharedKey.USER_ID), page, new BasePresenter.OnUIThreadListener<ItemCategoryMain>() {
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
                    if (lists.size() == 0) {
                        mRecyclerView.setEmptyView(text_empty);
                    }


                }

            }
        });

    }


}
