package com.ssj.hulijie.pro.mine.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.view.widget.MyItemDecoration;
import com.ssj.hulijie.pro.mine.adapter.ServiceOrderListAdapter;
import com.ssj.hulijie.pro.mine.bean.ItemServiceOrderList;
import com.ssj.hulijie.pro.mine.presenter.ServicePresenter;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.RefreshStatues;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vic_zhang .
 *         on 2017/12/23
 */

public class AcceptOrderListActivity extends BaseActivity {
    private ServiceOrderListAdapter adapter;
    private ServicePresenter presenter;
    private int page = 1;

    private List<ItemServiceOrderList.DataBean.RowsBean> lists = new ArrayList<>();
    private XRecyclerView mRecyclerView;
    private View text_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_accept_order_list);
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
        String center = "接单列表";
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

        adapter = new ServiceOrderListAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(item_click);

    }

    private ServiceOrderListAdapter.OnItemClickListener<ItemServiceOrderList.DataBean.RowsBean> item_click = new ServiceOrderListAdapter.OnItemClickListener<ItemServiceOrderList.DataBean.RowsBean>() {
        @Override
        public void onItemClick(int position, ItemServiceOrderList.DataBean.RowsBean data) {
            AppToast.ShowToast("position: " + position);
        }
    };


    private void getData(final RefreshStatues statues) {
        presenter.getAcceptOrderListModel(this, page, 10, new BasePresenter.OnUIThreadListener<ItemServiceOrderList>() {
            @Override
            public void onResult(ItemServiceOrderList result) {
                if (result != null) {

                    ItemServiceOrderList.DataBean data = result.getData();
                    List<ItemServiceOrderList.DataBean.RowsBean> rows = data.getRows();
                    int totalcount = data.getCount();
                    if (rows.size() > 0) {

                        for (int i = 0; i < rows.size(); i++) {
                            ItemServiceOrderList.DataBean.RowsBean item = rows.get(i);
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


    @Override
    public MvpBasePresenter bindPresenter() {
        presenter = new ServicePresenter(this);
        return presenter;
    }

}
