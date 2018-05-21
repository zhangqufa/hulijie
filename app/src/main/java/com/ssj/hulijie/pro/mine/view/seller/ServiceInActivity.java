package com.ssj.hulijie.pro.mine.view.seller;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.adapter.ServiceInMoneyDetailAdapter;
import com.ssj.hulijie.pro.mine.bean.ItemServiceInMoneyDetail;
import com.ssj.hulijie.pro.mine.bean.ItemServiceMoneyInfo;
import com.ssj.hulijie.pro.mine.presenter.ServicePresenter;
import com.ssj.hulijie.utils.RefreshStatues;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vic_zhang .
 *         on 2018/5/18
 */

public class ServiceInActivity extends BaseActivity<ServicePresenter> {

    private TextView tv_yinli;
    private TextView tv_frozen;
    private TextView tv_draw;
    private TextView tv_can_draw;
    private View text_empty;
    private XRecyclerView mRecyclerView;
    private int page = 1;
    private ServiceInMoneyDetailAdapter adapter;
    private List<ItemServiceInMoneyDetail.DataBean.RowsBean> lists = new ArrayList<>();

    @Override
    public ServicePresenter bindPresenter() {
        return new ServicePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_server_in);

        initToolbar();
        initView();
        initData();
    }

    private void initData() {
        initDataUI();
        page = 1;
        lists.clear();
        adapter.notifyDataSetChanged();
        getData(RefreshStatues.REFRESH);

    }

    private void initView() {
        tv_yinli = (TextView) findViewById(R.id.tv_yinli);
        tv_frozen = (TextView) findViewById(R.id.tv_frozen);
        tv_draw = (TextView) findViewById(R.id.tv_draw);
        tv_can_draw = (TextView) findViewById(R.id.tv_can_draw);


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

        adapter = new ServiceInMoneyDetailAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    private void initDataUI() {
        presenter.getServiceMoneyInfoPresenter(this, SharedUtil.getPreferStr(SharedKey.USER_ID), new BasePresenter.OnUIThreadListener<ItemServiceMoneyInfo>() {
            @Override
            public void onResult(ItemServiceMoneyInfo result) {
                if (result != null) {
                    updateUi(result);
                }
            }
        });
    }

    /**
     * 更新界面
     *
     * @param result
     */
    private void updateUi(ItemServiceMoneyInfo result) {
        if (!TextUtils.isEmpty(result.getAdd())) {
            tv_yinli.setText(result.getAdd());
        }
        tv_frozen.setText(result.getFrozen() + "元");
        tv_draw.setText(result.getDraw() + "元");
        tv_can_draw.setText(result.getCan_draw() + "元");
    }

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "我的收入", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);

    }

    private void getData(final RefreshStatues statues) {
        presenter.getServiceInMoneyDetailPresenter(this, SharedUtil.getPreferStr(SharedKey.USER_ID), page, new BasePresenter.OnUIThreadListener<ItemServiceInMoneyDetail>() {
            @Override
            public void onResult(ItemServiceInMoneyDetail result) {
                if (result != null) {
                    ItemServiceInMoneyDetail.DataBean data = result.getData();
                    List<ItemServiceInMoneyDetail.DataBean.RowsBean> rows = data.getRows();
                    int totalcount = data.getCount();
                    if (rows.size() > 0) {
                        for (int i = 0; i < rows.size(); i++) {
                            ItemServiceInMoneyDetail.DataBean.RowsBean item = rows.get(i);
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
