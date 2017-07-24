package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.AllEvaluateAdapter;
import com.ssj.hulijie.pro.firstpage.bean.EvaluateItem;
import com.ssj.hulijie.pro.firstpage.view.widget.DividerGridItemDecoration;
import com.ssj.hulijie.pro.msg.adapter.MsgListAdapter;
import com.ssj.hulijie.pro.msg.bean.MsgData;
import com.ssj.hulijie.pro.msg.bean.MsgListData;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * Created by Administrator on 2017/7/23.
 */

public class CheckAllEvaluateActivity extends BaseActivity {

    private RecyclerView msg_rv;
    private PtrClassicFrameLayout ptr;
    private EvaluateItem evaluateItem;

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_evaluate);
        initView();
        initToolbar();
    }

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "评价", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }

    private void initView() {
        ptr = (PtrClassicFrameLayout) findViewById(R.id.ptr_all_evaluate_list);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.addPtrUIHandler(handler);
        ptr.setPtrHandler(ptrHandler);


        msg_rv = (RecyclerView) findViewById(R.id.msg_rv);
        msg_rv.setLayoutManager(new LinearLayoutManager(this));
        msg_rv.addItemDecoration(new DividerGridItemDecoration(this));
        AllEvaluateAdapter adapter = new AllEvaluateAdapter(this);
        msg_rv.setAdapter(adapter);
        adapter.setLists(getData());
        adapter.setOnItemClickListener(onClickListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ptr != null) {
            ptr.refreshComplete();
        }
    }

    /**
     * ptr ui listener
     */
    private PtrUIHandler handler = new PtrUIHandler() {
        @Override
        public void onUIReset(PtrFrameLayout frame) {


        }

        @Override
        public void onUIRefreshPrepare(PtrFrameLayout frame) {

        }

        @Override
        public void onUIRefreshBegin(PtrFrameLayout frame) {

        }

        @Override
        public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {

        }

        @Override
        public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        }
    };

    /**
     * ptr data update
     */
    private PtrHandler ptrHandler = new PtrDefaultHandler2() {
        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {
            frame.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptr.refreshComplete();
                }
            }, 1800);
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            frame.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptr.refreshComplete();
                }
            }, 1800);
        }
    };

    private List<EvaluateItem> getData() {
        List<EvaluateItem> lists = new ArrayList<>();
        for (int i = 0; i < FirstPageFrament.img.length; i++) {
            EvaluateItem data = new EvaluateItem();
            //// TODO: 2017/7/23  
            lists.add(data);
        }


        return lists;

    }

    private AllEvaluateAdapter.OnItemClickListener<EvaluateItem> onClickListener = new AllEvaluateAdapter.OnItemClickListener<EvaluateItem>() {
        @Override
        public void onItemClick(int position, EvaluateItem data) {

        }
    };
}
