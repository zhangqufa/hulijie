package com.ssj.hulijie.pro.mine.view.seller;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.adapter.ServiceAcceptOrderListAdapter;
import com.ssj.hulijie.pro.mine.bean.ItemServiceOrderList;
import com.ssj.hulijie.pro.mine.presenter.ServicePresenter;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.RefreshStatues;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vic_zhang .
 *         on 2017/12/23
 */

public class ServiceAcceptOrderListActivity extends BaseActivity {
    private ServiceAcceptOrderListAdapter adapter;
    private ServicePresenter presenter;
    private int page = 1;

    private List<ItemServiceOrderList.DataBean.RowsBean> lists = new ArrayList<>();
    private XRecyclerView mRecyclerView;
    private View text_empty;

    //location
    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener;
    private LatLng sellerPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_accept_order_list);
        initToolbar();
        initView();
        initData();

        locationInit();
    }

    /**
     * 开始定位
     */
    private void startLocation() {
        if (mLocationClient != null && !mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= 23) {
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                    .send();
        } else {
            startLocation();
        }
    }

    private void locationInit() {
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
        mLocationClient.start();
    }


    /**
     * 计算两坐标的距离
     *
     * @param s
     * @param e
     */
    private void textJuli(LatLng s, LatLng e) {
       //todo

        double distance = DistanceUtil.getDistance(s, e);
//        AppLog.Log("长度:" + distance);
    }
    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation arg0) {
            AppLog.Log("商家地址 " + arg0.getCity() + " 经度： " + arg0.getLongitude() + " 纬度：" + arg0.getLatitude());
            if (sellerPoint == null) {
                sellerPoint = new LatLng(arg0.getLatitude(),arg0.getLongitude());
            }
            adapter.setsellerPoint(sellerPoint);
            mLocationClient.unRegisterLocationListener(mMyLocationListener);
        }


    }

    private void InitLocation() {
        // 设置定位参数
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(10000); // 10分钟扫描1次
        // 需要地址信息，设置为其他任何值（string类型，且不能为null）时，都表示无地址信息。
        option.setAddrType("all");
        // 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。
//        option.setPoiExtraInfo(true);
        // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setProdName("通过GPS定位我当前的位置");
        // 禁用启用缓存定位数据
        option.disableCache(true);
        // 设置最多可返回的POI个数，默认值为3。由于POI查询比较耗费流量，设置最多返回的POI个数，以便节省流量。
//        option.setPoiNumber(3);
        // 设置定位方式的优先级。
        // 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
        option.setPriority(LocationClientOption.GpsFirst);
        mLocationClient.setLocOption(option);
    }

    private void initData() {
        page = 1;
        lists.clear();
        adapter.notifyDataSetChanged();
        getData(RefreshStatues.REFRESH);
        getPersimmions();
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

        adapter = new ServiceAcceptOrderListAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(item_click);

    }

    private ServiceAcceptOrderListAdapter.OnItemClickListener<ItemServiceOrderList.DataBean.RowsBean> item_click = new ServiceAcceptOrderListAdapter.OnItemClickListener<ItemServiceOrderList.DataBean.RowsBean>() {
        @Override
        public void onItemClick(int position, ItemServiceOrderList.DataBean.RowsBean data) {
            //接单
            getOrder(position,data);
            AppToast.ShowToast("position: " + position);
        }
    };

    /**
     * 商家抢单
     * @param position
     * @param data
     */
    private void getOrder(final int position, ItemServiceOrderList.DataBean.RowsBean data) {
        presenter.serviceGetOrderPresenter(this, data.getOrder_id(), SharedUtil.getPreferStr(SharedKey.USER_ID), new BasePresenter.OnUIThreadListener<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                if (result) {
                    lists.remove(position);
                    adapter.notifyDataSetChanged();
                    startActivity(new Intent(ServiceAcceptOrderListActivity.this, ServiceOrderListActivity.class));
                }
            }
        });
    }


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

    private final int SDK_PERMISSION_REQUEST = 127;
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == 100) {
                startLocation();
            } else if (requestCode == 101) {
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(ServiceAcceptOrderListActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(ServiceAcceptOrderListActivity.this, SDK_PERMISSION_REQUEST).show();
            }
        }
    };
    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }
}
