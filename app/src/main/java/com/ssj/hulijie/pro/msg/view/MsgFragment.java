package com.ssj.hulijie.pro.msg.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.view.BaseFragment;
import com.ssj.hulijie.pro.firstpage.view.widget.DividerGridItemDecoration;
import com.ssj.hulijie.pro.home.view.MainActivity;
import com.ssj.hulijie.pro.msg.adapter.MsgAdapter;
import com.ssj.hulijie.pro.msg.bean.MsgData;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

import static com.ssj.hulijie.R.id.ptr_msg;

/**
 * Created by Administrator on 2017/3/26.
 */

public class MsgFragment extends BaseFragment {

    private RecyclerView msg_rv;
    private MainActivity mContext;
    private PtrClassicFrameLayout ptr;

    @Override
    public int getContentView() {
        return R.layout.frag_msg;
    }

    @Override
    public void initContentView(View viewContent) {

        ptr = (PtrClassicFrameLayout) viewContent.findViewById(ptr_msg);
        ptr.setMode(PtrFrameLayout.Mode.NONE);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.addPtrUIHandler(handler);
        ptr.setPtrHandler(ptrHandler);


        mContext = (MainActivity) getActivity();
        msg_rv = (RecyclerView) viewContent.findViewById(R.id.msg_rv);
        msg_rv.setLayoutManager(new LinearLayoutManager(mContext));
        msg_rv.addItemDecoration(new DividerGridItemDecoration(mContext));
        MsgAdapter adapter = new MsgAdapter(mContext);
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

    private List<MsgData> getData() {
        MsgData msgData = null;
        List<MsgData> lists = new ArrayList<>();
        msgData = new MsgData(R.mipmap.icon_msg_huodong, "活动消息", "墙面开裂、漏水？", System.currentTimeMillis());
        lists.add(msgData);
        msgData = new MsgData(R.mipmap.icon_msg_system, "系统消息", "系统消息子标题", System.currentTimeMillis());
        lists.add(msgData);
        return lists;

    }

    private MsgAdapter.OnItemClickListener<MsgData> onClickListener = new MsgAdapter.OnItemClickListener<MsgData>() {
        @Override
        public void onItemClick(int position, MsgData data) {
            Intent intent = null;
            if (position == 0) {
                intent = new Intent(mContext, MsgHuoDongListActivity.class);
                intent.putExtra("item", data);
            } else if (position == 1) {
                intent = new Intent(mContext, MsgSystemListActivity.class);
                intent.putExtra("item", data);
            }
            if (intent != null) {
                startActivity(intent);
            }
        }
    };


}
