package com.ssj.hulijie.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.alipay.PayResult;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.base.view.BaseFragment;
import com.ssj.hulijie.pro.firstpage.view.PayActivity;
import com.ssj.hulijie.pro.mine.adapter.OrderListAdapter;
import com.ssj.hulijie.pro.mine.bean.ItemOrderResp;
import com.ssj.hulijie.pro.mine.presenter.OrderListPresenter;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.RefreshStatues;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.widget.dialog.ConfirmCancelDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private LinearLayoutManager layoutManager;

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
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);


        adapter = new OrderListAdapter(getActivity(),false);
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
        public void onItemLeftClick(final int position, String status, final ItemOrderResp.DataBean.RowsBean data) {

            //取消订单
            if (getString(R.string.order_cancel).equals(status)) {
                ConfirmCancelDialog dlg = new ConfirmCancelDialog(getActivity(), new ConfirmCancelDialog.GoOther() {
                    @Override
                    public void go() {
                        toCancelOrder(position,data);
                    }

                    @Override
                    public void cancel() {

                    }
                },"是否确定取消订单?");
                dlg.show();
            }else if (getString(R.string.order_refund).equals(status)) {
                ConfirmCancelDialog dlg = new ConfirmCancelDialog(getActivity(), new ConfirmCancelDialog.GoOther() {
                    @Override
                    public void go() {
                        toRefund(position,data);
                    }

                    @Override
                    public void cancel() {

                    }
                },"是否确定退款?");
                dlg.show();

            }
        }

        /**
         * 右点击
         * @param position
         * @param status
         * @param data
         */
        @Override
        public void onItemRightClick(final int position, String status, final ItemOrderResp.DataBean.RowsBean data) {
            //去付款
            if (getString(R.string.order_immediately_pay).equals(status)) {
                toPay(data);
            } else if (getString(R.string.order_refund).equals(status)) {
                ConfirmCancelDialog dlg = new ConfirmCancelDialog(getActivity(), new ConfirmCancelDialog.GoOther() {
                    @Override
                    public void go() {
                        toRefund(position,data);
                    }

                    @Override
                    public void cancel() {

                    }
                },"是否确定退款?");
                dlg.show();

            } else if (getString(R.string.order_confirm_complete).equals(status)) {
                ConfirmCancelDialog dlg = new ConfirmCancelDialog(getActivity(), new ConfirmCancelDialog.GoOther() {
                    @Override
                    public void go() {
                        toFinish(position, data);
                    }

                    @Override
                    public void cancel() {

                    }
                },"是否确定完成?");
                dlg.show();
            }
        }

    };

    /**
     * 确定完成订单
     * @param position
     * @param data
     */
    private void toFinish(final int position, ItemOrderResp.DataBean.RowsBean data) {

        presenter.getFinishOrderPresenter((BaseActivity) getActivity(), SharedUtil.getPreferStr(SharedKey.USER_ID), data.getOrder_id(), new BasePresenter.OnUIThreadListener<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                lists.get(position).setStatus(40);
                adapter.notifyDataSetChanged();
            }
        });


    }

    /**
     * 退款
     * @param position
     * @param data
     */
    private void toRefund(final int position, ItemOrderResp.DataBean.RowsBean data) {

        presenter.getOrderRefundPresenter((BaseActivity) getActivity(),SharedUtil.getPreferStr(SharedKey.USER_ID), data.getOrder_id(), new BasePresenter.OnUIThreadListener<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                if (result) {
                    lists.get(position).setStatus(3);
                    adapter.notifyDataSetChanged();
                }

            }
        });

    }

    /**
     * 取消订单
     * @param data
     */
    private void toCancelOrder(final int position, ItemOrderResp.DataBean.RowsBean data) {
        presenter.getCancelOrderPresenter((BaseActivity) getActivity(),SharedUtil.getPreferStr(SharedKey.USER_ID), data.getOrder_id(), new BasePresenter.OnUIThreadListener<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                if (result) {


                    AppLog.Log("当前取消Position: " + position);
                    ItemOrderResp.DataBean.RowsBean rowsBean = lists.get(position);
                    rowsBean.setStatus(0);
//                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//                    boolean b = position <= lastVisibleItemPosition && position >= firstVisibleItemPosition;
//                    if (b) {
//                        adapter.notifyItemChanged(position);
//                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }


    private static final String EXTRA_CONTENT = "content";

    public static OrderListFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        OrderListFragment tabContentFragment = new OrderListFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }

    private void finishClose() {
        AppLog.Log("payact");
        initData();
    }

    private static final int SDK_PAY_FLAG = 100;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                        finishClose();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                    }

                    break;

                default:
                    break;
            }
        }
    };

    private void callApliy(String orderInfo){
        if (orderInfo.contains("amp;")) {
            orderInfo = orderInfo.replace("amp;", "");

        }
        final String finalyStr = orderInfo;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(finalyStr, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 获取订单签名 并支付
     *
     * @param data
     */
    private void toPay(ItemOrderResp.DataBean.RowsBean data) {
        presenter.getOrderSingPresenter((BaseActivity) getActivity(), data.getOrder_id(),SharedUtil.getPreferStr(SharedKey.USER_ID), new BasePresenter.OnUIThreadListener<String>() {
            @Override
            public void onResult(String result) {
                if (!TextUtils.isEmpty(result)) {
                    callApliy(result);
                }

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
