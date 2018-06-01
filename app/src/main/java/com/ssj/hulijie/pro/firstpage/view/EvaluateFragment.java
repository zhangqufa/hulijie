package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.base.view.BaseFragment;
import com.ssj.hulijie.pro.firstpage.adapter.EvaluateAdapter;
import com.ssj.hulijie.pro.firstpage.bean.ItemEvaluate;
import com.ssj.hulijie.pro.firstpage.presenter.EvaluatePresenter;
import com.ssj.hulijie.pro.mine.adapter.OrderListAdapter;
import com.ssj.hulijie.pro.mine.bean.ItemOrderResp;
import com.ssj.hulijie.pro.mine.presenter.OrderListPresenter;
import com.ssj.hulijie.utils.RefreshStatues;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public class EvaluateFragment extends BaseFragment<EvaluatePresenter> {

    private XRecyclerView mRecyclerView;
    private int refreshTime;
    private int times;
    private List<ItemEvaluate.DataBean.RowsBean> lists = new ArrayList<>();
    private int page = 1;
    private EvaluateType currentType = EvaluateType.ALL;
    private EvaluateAdapter adapter;
    private View text_empty;
    private LinearLayoutManager layoutManager;

    private String goods_id;

    public static final String EXTRA_CONTENT = "content";

    @Override
    public int getContentView() {
        return R.layout.frag_valuate;
    }
    @Override
    public void initData() {
        page = 1;
        lists.clear();
        adapter.notifyDataSetChanged();
        getData(RefreshStatues.REFRESH);
    }


    /**
     * 1：未完成 2：已完成 3：待评价 0:全部订单
     *
     * @param statues
     */
    private void getData(final RefreshStatues statues) {
        presenter.getEvaluatePresenter((BaseActivity) getActivity(), page,goods_id ,currentType.getValue(), new BasePresenter.OnUIThreadListener<ItemEvaluate>() {
            @Override
            public void onResult(ItemEvaluate result) {
                if (result != null) {

                    ItemEvaluate.DataBean data = result.getData();
                    List<ItemEvaluate.DataBean.RowsBean> rows = data.getRows();
                    int totalcount = data.getCount();
                    if (rows.size() > 0) {

                        for (int i = 0; i < rows.size(); i++) {
                            ItemEvaluate.DataBean.RowsBean item = rows.get(i);
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
                } else {
                    mRecyclerView.setNoMore(true);

                    mRecyclerView.setEmptyView(text_empty);
                }
            }
        });


    }
//1.全部 2.晒图 3.低分 4.最新
    @Override
    public void initContentView(View viewContent) {
        //empty view
        text_empty = (View) viewContent.findViewById(R.id.text_empty);
        String title = (String) getArguments().get(EXTRA_CONTENT);
        goods_id =getArguments().getString("goods_id");
        if ("最新".equals(title)) {
            currentType = EvaluateType.NEW;
        } else if ("晒图".equals(title)) {
            currentType = EvaluateType.SHOWPIC;
        } else if ("低分".equals(title)) {
            currentType = EvaluateType.LOW;
        } else {
            currentType = EvaluateType.ALL;
        }


        mRecyclerView = (XRecyclerView) viewContent.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);


        adapter = new EvaluateAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);

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

    /**
     * 1.全部 2.晒图 3.低分 4.最新
     */
    enum EvaluateType{
        ALL(1),SHOWPIC(2),LOW(3),NEW(4);

        EvaluateType(int value) {
            this.value = value;
        }

        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static EvaluateFragment newInstance() {

        EvaluateFragment tabContentFragment = new EvaluateFragment();
        return tabContentFragment;
    }

    @Override
    public EvaluatePresenter bindPresenter() {
        return new EvaluatePresenter(getActivity());
    }

}
