package com.ssj.hulijie.pro.firstpage.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ssj.hulijie.R;
import com.ssj.hulijie.base.HljAppliation;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseFragment;
import com.ssj.hulijie.pro.firstpage.adapter.FirstPageHeaderFourPartAdappter;
import com.ssj.hulijie.pro.firstpage.adapter.FirstPageMainHeaderAdapter;
import com.ssj.hulijie.pro.firstpage.adapter.FirstPageMainListAdapter;
import com.ssj.hulijie.pro.firstpage.bean.FourpartData;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainHeaderList;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.pro.firstpage.presenter.FirstPagePresenter;
import com.ssj.hulijie.pro.firstpage.view.location.LocationActivity;
import com.ssj.hulijie.pro.firstpage.view.widget.DividerGridItemDecoration;
import com.ssj.hulijie.pro.home.view.MainActivity;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.widget.recylerview.BaseRecyclerAdapter;
import com.ssj.hulijie.widget.recylerview.RecyclerViewHeader;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

import static android.app.Activity.RESULT_OK;
import static com.baidu.location.h.j.s;
import static com.baidu.location.h.j.t;
import static com.ssj.hulijie.R.id.rv_first_page_main_list;
import static com.ssj.hulijie.R.id.rv_header_rv;

/**
 * Created by Administrator on 2017/3/26.
 */

public class FirstPageFrament extends BaseFragment implements View.OnClickListener {

    private static final String url = "http://192.168.221.179:8080/DataServer/QueryNameServlet";
    private MainActivity context;
    private PtrClassicFrameLayout ptr;  //ptr refresh widget
    private RecyclerView rv_first_page_main_list;
    private FirstPageMainListAdapter adapter;  //main list adapter
    private List<ItemFirstPageMainList> data = new ArrayList<>();
    private RecyclerViewHeader rv_header;  //recylerview header
    private int page = 1;

    public static String img[] = {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490931601761&di=d3d10e1a89b90ed3483816404519311a&imgtype=0&src=http%3A%2F%2Fimg9.3lian.com%2Fc1%2Fvector%2F10%2F06%2F018.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490931601761&di=d3d10e1a89b90ed3483816404519311a&imgtype=0&src=http%3A%2F%2Fimg9.3lian.com%2Fc1%2Fvector%2F10%2F06%2F018.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490931701294&di=a18097f8873a16386e013931a022b6e8&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F10%2F81%2F46%2F88bOOOPICca.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490921656&di=345f70a4888c4e6523ac62f6169efc16&src=http://pic1.cxtuku.com/00/07/42/b7823851b6ae.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490921721&di=b278781570174fb400f412072ae60e99&src=http://img9.3lian.com/c1/vector/10/01/152.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490921721&di=132d29f3d93d9e56d05af3ce3c135996&src=http://pic.58pic.com/10/81/48/79bOOOPICbb.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490921721&di=812773e1643cf17257b1e7fd480c794b&src=http://pic.58pic.com/10/81/55/49bOOOPICe5.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490921721&di=b030ccd52d0ae3b6271a613db7f36d8e&src=http://pic2.cxtuku.com/00/07/42/b767a93fe5a7.jpg"
    };


    private String mid_img[] = {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490934544694&di=86c3dad3b5d43b047b4af5744fc0d76c&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fblog%2F201512%2F16%2F20151216140942_2JXYr.thumb.700_0.jpeg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490934544695&di=9395c6b67a1a66a089998a4cc013e54a&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1110%2F18%2Fc6%2F9309619_9309619_1318915453187.jpg"
    };
    private TextView location_tv;  //location
    private LocationClient mLocationClient; //location
    private RecyclerView rv_header_four_part;
    private FirstPagePresenter mPresenter;
    private FirstPageMainHeaderAdapter rv_header_rv_adapter; //catetory adapter
    private FirstPageHeaderFourPartAdappter adappter_four_part;

    // 要申请的权限

    @Override
    public int getContentView() {
        return R.layout.frag_one;
    }

    @Override
    public void initContentView(View viewContent) {
        context = (MainActivity) getActivity();
        //init ptr-lib
        ptr = (PtrClassicFrameLayout) viewContent.findViewById(R.id.ptr_refresh);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.addPtrUIHandler(handler);
        ptr.setPtrHandler(ptrHandler);


        //init RecyclerView
        rv_first_page_main_list = (RecyclerView) viewContent.findViewById(R.id.rv_first_page_main_list);
        rv_first_page_main_list.setLayoutManager(new LinearLayoutManager(context));
        adapter = new FirstPageMainListAdapter(context);
        rv_first_page_main_list.setAdapter(adapter);
        adapter.setOnItemClickListener(item_click);


        //init RecylerView Header
        View header = LayoutInflater.from(context).inflate(R.layout.header_rv, rv_first_page_main_list, false);
        adapter.setHeaderView(header);
        location_tv = (TextView) header.findViewById(R.id.location_tv);
        location_tv.setOnClickListener(this);
        header.findViewById(R.id.et_search).setOnClickListener(this);

        //init RecylerView header fout part
        adappter_four_part = new FirstPageHeaderFourPartAdappter(context);
        rv_header_four_part = (RecyclerView) header.findViewById(R.id.rv_header_four_part);
        rv_header_four_part.setAdapter(adappter_four_part);
        rv_header_four_part.setLayoutManager(new GridLayoutManager(context, 2));
        rv_header_four_part.addItemDecoration(new DividerGridItemDecoration(context));
        adappter_four_part.setOnItemClickListener(four_part_listener);


        //init grid category
        RecyclerView rv_header_rv = (RecyclerView) header.findViewById(R.id.rv_header_rv);
        rv_header_rv_adapter = new FirstPageMainHeaderAdapter(context);
        rv_header_rv.setAdapter(rv_header_rv_adapter);
        rv_header_rv.setLayoutManager(new GridLayoutManager(context, 4));
        rv_header_rv_adapter.setOnItemClickListener(headrCatetoryListener);


        //init location
        mLocationClient = new LocationClient(HljAppliation.getContext());
        MyLocationListener mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();


        // after andrioid m,must request Permiision on runtime
        getPersimmions();


    }

    private BaseRecyclerAdapter.OnItemClickListener item_click = new BaseRecyclerAdapter.OnItemClickListener<ItemFirstPageMainList>() {
        @Override
        public void onItemClick(int position, ItemFirstPageMainList item) {
            Intent intent = new Intent(context, DetailInfoActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
        }
    };


    @Override
    public MvpBasePresenter bindPresenter() {
        mPresenter = new FirstPagePresenter(context);
        return mPresenter;
    }

    private void getListFourData() {

        mPresenter.getMidFourPresenter(context, new BasePresenter.OnUIThreadListener<List<FourpartData>>() {
            @Override
            public void onResult(List<FourpartData> result) {
                if (result != null) {
                    for (int i = 0; i < result.size(); i++) {
                        result.get(i).setPic(FirstPageFrament.img[i]);
                    }
                    adappter_four_part.setLists(result);
                }
            }
        });

    }

    private void getPersimmions() {
        if (Build.VERSION.SDK_INT > 23) {
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                    .send();
        } else {
            startLocation();
        }
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
            if (AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(context, SDK_PERMISSION_REQUEST).show();

                // 第二种：用自定义的提示语。
                // AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                // .setTitle("权限申请失败")
                // .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                // .setPositiveButton("好，去设置")
                // .show();

                // 第三种：自定义dialog样式。
                // SettingService settingService =
                //    AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
                // 你的dialog点击了确定调用：
                // settingService.execute();
                // 你的dialog点击了取消调用：
                // settingService.cancel();
            }
        }
    };

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    /**
     * 开始定位
     */
    private void startLocation() {
        if (mLocationClient != null && !mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }


    private boolean isNeedFresh = true;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String location = (String) msg.obj;
                location_tv.setText(location);

            }
        }
    };

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            AppLog.Log("city = " + location.getCity());
            if (!isNeedFresh) {
                return;
            }
            isNeedFresh = false;
            if (location.getCity() == null) {
                AppToast.ShowToast("定位失败");
                return;
            }
            String currentCity = location.getCity().substring(0,
                    location.getCity().length() - 1);
            if (!TextUtils.isEmpty(currentCity)) {
                mHandler.obtainMessage(0, currentCity).sendToTarget();
                AppLog.Log("定位：" + currentCity);
            } else {
                mHandler.obtainMessage(0, "选择城市").sendToTarget();
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

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

    /**
     * 头部分类点击listener
     */
    private FirstPageMainHeaderAdapter.OnItemClickListener<ItemFirstPageMainHeaderList> headrCatetoryListener =
            new FirstPageMainHeaderAdapter.OnItemClickListener<ItemFirstPageMainHeaderList>() {
                @Override
                public void onItemClick(int position, ItemFirstPageMainHeaderList data) {
                    AppToast.ShowToast(position + "");
                    if (position == 7) {
                        Intent intent = new Intent(context, AllCatetoryActivity.class);
                        startActivity(intent);
                    }
                }
            };


    /**
     * 头部four_part listener
     */
    private FirstPageHeaderFourPartAdappter.OnItemClickListener<FourpartData> four_part_listener = new FirstPageHeaderFourPartAdappter.OnItemClickListener<FourpartData>() {
        @Override
        public void onItemClick(int position, FourpartData data) {
            AppToast.ShowToast(position + "");
        }
    };

    /**
     * 测试数据  头部 分类选项
     *
     * @return
     */
    private void getHeaderList() {
        final List<ItemFirstPageMainHeaderList> lists = new ArrayList<>();
        mPresenter.getCatetoryPresenter(context, new BasePresenter.OnUIThreadListener<List<ItemFirstPageMainHeaderList>>() {
            @Override
            public void onResult(List<ItemFirstPageMainHeaderList> result) {
                for (int i = 0; i < result.size(); i++) {
                    ItemFirstPageMainHeaderList item = result.get(i);
//                    item.setPic(img[i]);
                    lists.add(item);
                    rv_header_rv_adapter.setData(lists);
                }
            }
        });

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
            page++;
            getData();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            page = 1;
            data.clear();
            adapter.notifyDataSetChanged();
            getData();

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (ptr != null) {
            ptr.refreshComplete();
        }
    }

    /**
     * 测试数据  首页主数据
     *
     * @return
     */
    private void getData() {
        mPresenter.getFirstDataPresenter(context, page, new BasePresenter.OnUIThreadListener<List<ItemFirstPageMainList>>() {
            @Override
            public void onResult(List<ItemFirstPageMainList> result) {
                if (result != null) {
                    if (result.size() > 0) {

                        for (int i = 0; i < result.size(); i++) {
                            ItemFirstPageMainList item = result.get(i);
                            item.setPic(img[i]);
                            data.add(item);
                        }
                        adapter.addDatas(data);
                    }
                }
                ptr.refreshComplete();
            }
        });
    }

    @Override
    public void initData() {
        /**
         * 加载分类图标和类型
         *
         */
        getHeaderList();
        getListFourData();
        getData();
    }

    public static final int LOCATION_REQUEST_CODE = 1000;

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.location_tv:
                intent = new Intent(context, LocationActivity.class);
                startActivityForResult(intent, LOCATION_REQUEST_CODE);
                break;
            case R.id.et_search:
                intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_REQUEST_CODE && resultCode == RESULT_OK) {
            String currentCity = data.getStringExtra("currentCity");
            if (!TextUtils.isEmpty(currentCity)) {
                location_tv.setText(currentCity);
                AppLog.Log("定位：" + currentCity);
            } else {
                location_tv.setText("选择城市");
            }
        }
    }

}
