package com.ssj.hulijie.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.base.view.BaseFragment;
import com.ssj.hulijie.pro.mine.adapter.OrderListAdapter;
import com.ssj.hulijie.pro.mine.bean.ItemOrderResp;
import com.ssj.hulijie.pro.mine.presenter.OrderListPresenter;
import com.ssj.hulijie.utils.RefreshStatues;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/7/19
 */

public class OrderListFragment extends BaseFragment implements View.OnClickListener {
    private XRecyclerView mRecyclerView;
    private int refreshTime;
    private int times;
    private List<ItemOrderResp.DataBean.RowsBean> lists = new ArrayList<>();
    private int page = 1;
    private OrderListPresenter presenter;
    private OrderType currentType = OrderType.NOFINISH;
    private OrderListAdapter adapter;
    private View text_empty;

    @Override
    public int getContentView() {
        return R.layout.frag_order_list;
    }

    /**
     * 1：未完成 2：已完成 3：待评价 0:全部订单
     */
    enum OrderType {
        NOFINISH(1), FINISH(2), EVALUATE(3), ALL(0);

        private int index;

        OrderType(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    @Override
    public MvpBasePresenter bindPresenter() {
        presenter = new OrderListPresenter(getActivity());
        return presenter;
    }

    @Override
    public void initContentView(View viewContent) {
        //empty view
        text_empty = (View) viewContent.findViewById(R.id.text_empty);
        String title = (String) getArguments().get(EXTRA_CONTENT);
        if ("未完成".equals(title)) {
            currentType = OrderType.NOFINISH;
        } else if ("已完成".equals(title)) {
            currentType = OrderType.FINISH;
        } else if ("待评价".equals(title)) {
            currentType = OrderType.EVALUATE;
        } else {
            currentType = OrderType.ALL;
        }

        viewContent.findViewById(R.id.toOrder).setOnClickListener(this);

        mRecyclerView = (XRecyclerView) viewContent.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);


        adapter = new OrderListAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onClickListener);

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

    }

    @Override
    public void initData() {
        page = 1;
        lists.clear();
        adapter.notifyDataSetChanged();
        getData(RefreshStatues.REFRESH);
    }


    private OrderListAdapter.OnItemClickListener<ItemOrderResp.DataBean.RowsBean> onClickListener = new OrderListAdapter.OnItemClickListener<ItemOrderResp.DataBean.RowsBean>() {

        /**
         * 列表点击
         * @param position
         * @param data
         */
        @Override
        public void onItemClick(int position, ItemOrderResp.DataBean.RowsBean data) {

            Intent intent = new Intent(getActivity(), OrderItemDetailActivity.class);
            intent.putExtra("orderItem", data);
            startActivity(intent);

        }

        /**
         * 左点击
         * @param position
         * @param status
         * @param data
         */
        @Override
        public void onItemLeftClick(int position, String status, ItemOrderResp.DataBean.RowsBean data) {

        }

        /**
         * 右点击
         * @param position
         * @param status
         * @param data
         */
        @Override
        public void onItemRightClick(int position, String status, ItemOrderResp.DataBean.RowsBean data) {
            //去付款
            if (getString(R.string.order_immediately_pay).equals(status)) {
                toPay(position, status, data);
            }
        }

    };


    private static final String EXTRA_CONTENT = "content";

    public static OrderListFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        OrderListFragment tabContentFragment = new OrderListFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }


    /**
     * 获取订单签名 并支付
     *
     * @param position
     * @param status
     * @param data
     */
    private void toPay(int position, String status, ItemOrderResp.DataBean.RowsBean data) {
        presenter.toPayPresenter((BaseActivity) getActivity(), data.getOrder_id(), new BasePresenter.OnUIThreadListener<ItemOrderResp>() {
            @Override
            public void onResult(ItemOrderResp result) {

            }
        });
    }


    /**
     * 1：未完成 2：已完成 3：待评价 0:全部订单
     *
     * @param statues
     */
    private void getData(final RefreshStatues statues) {
        presenter.getOrderListPresenter((BaseActivity) getActivity(), SharedUtil.getPreferStr(SharedKey.USER_ID), page, currentType.getIndex(), new BasePresenter.OnUIThreadListener<ItemOrderResp>() {
            @Override
            public void onResult(ItemOrderResp result) {
                if (result != null) {

                    ItemOrderResp.DataBean data = result.getData();
                    List<ItemOrderResp.DataBean.RowsBean> rows = data.getRows();
                    int totalcount = data.getCount();
                    if (rows.size() > 0) {

                        for (int i = 0; i < rows.size(); i++) {
                            ItemOrderResp.DataBean.RowsBean item = rows.get(i);
                            lists.add(item);
                        }
                        adapter.setLists(lists);

                        if (statues == RefreshStatues.REFRESH) {
                            mRecyclerView.refreshComplete();

                        } else if (statues == RefreshStatues.LOADMORE) {
                            mRecyclerView.loadMoreComplete();
                        }
                        if (rows.size() < 10 && lists.size() >= 5 || lists.size() == totalcount && lists.size() >= 5) {
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toOrder:
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
                break;
            default:
                break;
        }
    }
}
