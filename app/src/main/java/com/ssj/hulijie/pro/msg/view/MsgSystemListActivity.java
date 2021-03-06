package com.ssj.hulijie.pro.msg.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.msg.adapter.MsgHuodongListAdapter;
import com.ssj.hulijie.pro.msg.adapter.MsgSystemListAdapter;
import com.ssj.hulijie.pro.msg.bean.ItemMsgHuoDong;
import com.ssj.hulijie.pro.msg.bean.ItemMsgSystem;
import com.ssj.hulijie.pro.msg.bean.MsgData;
import com.ssj.hulijie.pro.msg.presenter.MsgPresenter;
import com.ssj.hulijie.utils.RefreshStatues;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/7/23.
 */

public class MsgSystemListActivity extends BaseActivity<MsgPresenter> {

    private MsgData item;
    private XRecyclerView mRecyclerView;

    private int page =1;
    private List<ItemMsgSystem.RowsBean> lists = new ArrayList<>();
    private MsgSystemListAdapter adapter;
    private View text_empty;


    @Override
    public MsgPresenter bindPresenter() {
        return new MsgPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg_list);
        item =getIntent().getParcelableExtra("item");
        initView();
        initToolbar();
        initData();
    }

    private void initData() {
        lists.clear();
        adapter.notifyDataSetChanged();
        page = 1;
        getData(RefreshStatues.REFRESH);
    }

    private void getData(final RefreshStatues statues) {
        presenter.getMsgSystemPresenter(this, SharedUtil.getPreferStr(SharedKey.USER_ID), page, new BasePresenter.OnUIThreadListener<ItemMsgSystem>() {
            @Override
            public void onResult(ItemMsgSystem result) {
                if (result != null) {


                    List<ItemMsgSystem.RowsBean> rows = result.getRows();
                    int totalcount = result.getCount();
                    if (rows.size() > 0) {

                        for (int i = 0; i < rows.size(); i++) {
                            ItemMsgSystem.RowsBean item = rows.get(i);
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

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, item==null?"消息":item.getTitle(), android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }

    private void initView() {
        text_empty = findViewById(R.id.text_empty);
        mRecyclerView = (XRecyclerView)this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);


        adapter = new MsgSystemListAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onClickListener);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh(){
                initData();
            }

            @Override
            public void onLoadMore() {
                page++;
                getData(RefreshStatues.LOADMORE);
            }
        });
    }




    private MsgSystemListAdapter.OnItemClickListener<ItemMsgHuoDong.RowsBean> onClickListener = new MsgSystemListAdapter.OnItemClickListener<ItemMsgHuoDong.RowsBean>() {
        @Override
        public void onItemClick(int position, ItemMsgHuoDong.RowsBean data) {

        }
    };
}
