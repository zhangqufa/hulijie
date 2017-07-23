package com.ssj.hulijie.pro.msg.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.view.FirstPageFrament;
import com.ssj.hulijie.pro.firstpage.view.widget.DividerGridItemDecoration;
import com.ssj.hulijie.pro.home.view.MainActivity;
import com.ssj.hulijie.pro.msg.adapter.MsgAdapter;
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

import static com.ssj.hulijie.R.id.msg_rv;


/**
 * Created by Administrator on 2017/7/23.
 */

public class MsgListActivity extends BaseActivity {


    private RecyclerView msg_rv;
    private PtrClassicFrameLayout ptr;
    private MsgData item;


    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg_list);
        item =getIntent().getParcelableExtra("item");
        initView();
        initToolbar();
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
        ptr = (PtrClassicFrameLayout) findViewById(R.id.ptr_msg_list);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.addPtrUIHandler(handler);
        ptr.setPtrHandler(ptrHandler);


        msg_rv = (RecyclerView) findViewById(R.id.msg_rv);
        msg_rv.setLayoutManager(new LinearLayoutManager(this));
        msg_rv.addItemDecoration(new DividerGridItemDecoration(this));
        MsgListAdapter adapter = new MsgListAdapter(this);
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

    private List<MsgListData> getData() {
        List<MsgListData> lists = new ArrayList<>();
        for (int i = 0; i < FirstPageFrament.img.length; i++) {
            MsgListData data = new MsgListData(FirstPageFrament.img[i], "title_" + i,
                    "sub_title_" + i, System.currentTimeMillis());
            lists.add(data);
        }


        return lists;

    }

    private MsgListAdapter.OnItemClickListener<MsgData> onClickListener = new MsgListAdapter.OnItemClickListener<MsgData>() {
        @Override
        public void onItemClick(int position, MsgData data) {

        }
    };
}
