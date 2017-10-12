package com.ssj.hulijie.pro.msg.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
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


    private MsgData item;
    private XRecyclerView mRecyclerView;
    private int refreshTime = 0;
    private int times = 0;


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
        mRecyclerView = (XRecyclerView)this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);


        MsgListAdapter adapter = new MsgListAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setLists(getData());
        adapter.setOnItemClickListener(onClickListener);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime ++;
                times = 0;
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        for(int i = 0; i < 15 ;i++){
                        }
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if(times < 2){
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            for(int i = 0; i < 15 ;i++){
                            }
                            mRecyclerView.loadMoreComplete();
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            for(int i = 0; i < 9 ;i++){
                            }
                            mRecyclerView.setNoMore(true);
                        }
                    }, 1000);
                }
                times ++;
            }
        });
        mRecyclerView.refresh();
    }



    private List<MsgListData> getData() {
        List<MsgListData> lists = new ArrayList<>();
        for (int i = 0; i < FirstPageFrament.img.length; i++) {
            MsgListData data = new MsgListData(FirstPageFrament.img[i], "【周末福利】2017年装修到底要多少钱，提前会不会被坑？",
                    "预算心中有数，装修掌中有术" , System.currentTimeMillis());
            lists.add(data);
        }


        return lists;

    }

    private MsgListAdapter.OnItemClickListener<MsgListData> onClickListener = new MsgListAdapter.OnItemClickListener<MsgListData>() {
        @Override
        public void onItemClick(int position, MsgListData data) {

        }
    };
}
