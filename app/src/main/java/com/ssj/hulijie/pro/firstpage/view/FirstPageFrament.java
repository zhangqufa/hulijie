package com.ssj.hulijie.pro.firstpage.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.ssj.hulijie.R;
import com.ssj.hulijie.base.HljAppliation;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.base.view.BaseFragment;
import com.ssj.hulijie.pro.firstpage.adapter.FirstPageHeaderFourPartAdappter;
import com.ssj.hulijie.pro.firstpage.adapter.FirstPageMainListAdapter;
import com.ssj.hulijie.pro.firstpage.adapter.MyViewPagerAdapter;
import com.ssj.hulijie.pro.firstpage.adapter.MygridviewAdapter;
import com.ssj.hulijie.pro.firstpage.bean.FourpartData;
import com.ssj.hulijie.pro.firstpage.bean.ItemCategoryMain;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainHeaderList;
import com.ssj.hulijie.pro.firstpage.presenter.FirstPagePresenter;
import com.ssj.hulijie.pro.firstpage.view.location.LocationActivity;
import com.ssj.hulijie.pro.firstpage.view.widget.DividerGridItemDecoration;
import com.ssj.hulijie.pro.firstpage.view.widget.DividerItemDecoration;
import com.ssj.hulijie.pro.firstpage.view.widget.MyViewPaper;
import com.ssj.hulijie.pro.home.view.MainActivity;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.MyHandler;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.versioncontrol.UpdateManager;
import com.ssj.hulijie.widget.recylerview.BaseRecyclerAdapter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/3/26.
 */

public class FirstPageFrament extends BaseFragment implements View.OnClickListener, MyHandler.HandlerCallback {

    private MainActivity context;
    private PtrClassicFrameLayout ptr;  //ptr refresh widget
    private RecyclerView rv_first_page_main_list;
    private FirstPageMainListAdapter adapter;  //main list adapter
    private List<ItemCategoryMain.DataBean.RowsBean> data = new ArrayList<>();
    private int page = 1;

    /**
     * 只第一次跑转到登录界面
     */
    private boolean flagFirst;

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


    private TextView location_tv, location_tv_out, location_tv_right;  //location
    private LocationClient mLocationClient; //location
    private RecyclerView rv_header_four_part;
    private FirstPagePresenter mPresenter;
    private FirstPageHeaderFourPartAdappter adappter_four_part;
    private TextView et_search;
    private LinearLayout ll_out; //外层
    private ImageView iv_one, iv_two;
    private List<View> pagerView;
    private MyViewPaper category_vp;
    private GridView gv_one, gv_two;
    private LinearLayout ll_indicator; //indicator viewgroup
    private View footer;
    private TextView tv_footer;
    private AVLoadingIndicatorView footer_animater;
    // 要申请的权限

    private MyHandler mHandler;

    @Override
    public int getContentView() {
        return R.layout.frag_one;
    }

    @Override
    public void initContentView(View viewContent) {
        context = (MainActivity) getActivity();
        mHandler = new MyHandler(context);
        mHandler.setHandlerCallback(this);
        //外层header
        ll_out = (LinearLayout) viewContent.findViewById(R.id.ll_out);
        location_tv_out = (TextView) ll_out.findViewById(R.id.location_tv);
        location_tv_out.setOnClickListener(this);
        et_search = (TextView) viewContent.findViewById(R.id.et_search);
        et_search.setOnClickListener(this);

        //init ptr-lib
        ptr = (PtrClassicFrameLayout) viewContent.findViewById(R.id.ptr_refresh);
        ptr.setMode(PtrFrameLayout.Mode.REFRESH);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.setPtrHandler(ptrHandler);
        ptr.addPtrUIHandler(ptrUihandler);


        //init RecyclerView
        rv_first_page_main_list = (RecyclerView) viewContent.findViewById(R.id.rv_first_page_main_list);
        rv_first_page_main_list.setLayoutManager(new LinearLayoutManager(context));
        rv_first_page_main_list.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        adapter = new FirstPageMainListAdapter(context);
        rv_first_page_main_list.setAdapter(adapter);
        rv_first_page_main_list.addOnScrollListener(recyclerView_listener);
        adapter.setOnItemClickListener(item_click);


        //init RecylerView Header
        View header = LayoutInflater.from(context).inflate(R.layout.header_rv, rv_first_page_main_list, false);
        adapter.setHeaderView(header);
        location_tv = (TextView) header.findViewById(R.id.location_tv);

        //init RecylerView header fout part
        adappter_four_part = new FirstPageHeaderFourPartAdappter(context);
        rv_header_four_part = (RecyclerView) header.findViewById(R.id.rv_header_four_part);
        rv_header_four_part.setFocusable(false);////解决上滑回弹的问题
        rv_header_four_part.setAdapter(adappter_four_part);
        rv_header_four_part.setLayoutManager(new GridLayoutManager(context, 2));
        rv_header_four_part.addItemDecoration(new DividerGridItemDecoration(context));
        adappter_four_part.setOnItemClickListener(four_part_listener);

        //init footer
        footer = LayoutInflater.from(context).inflate(R.layout.footer, rv_first_page_main_list, false);
        tv_footer = (TextView) footer.findViewById(R.id.tv_footer);

        footer_animater = (AVLoadingIndicatorView) footer.findViewById(R.id.listview_header_progressbar);

        adapter.setFooterView(footer);


//        //init grid category
        iv_one = (ImageView) header.findViewById(R.id.iv_one);
        iv_two = (ImageView) header.findViewById(R.id.iv_two);

        ll_indicator = (LinearLayout) header.findViewById(R.id.ll_indicator);
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.viewpager_one, null);
        View view2 = LayoutInflater.from(getContext()).inflate(R.layout.viewpager_two, null);
        gv_one = (GridView) view1.findViewById(R.id.gv_one);
        gv_two = (GridView) view2.findViewById(R.id.gv_two);

        pagerView = new ArrayList<>();
        pagerView.add(view1);
        pagerView.add(view2);

        category_vp = (MyViewPaper) header.findViewById(R.id.category_vp);
        category_vp.setParentView(ptr);
        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(getActivity(), pagerView);
        category_vp.setAdapter(pagerAdapter);
        iv_one.setSelected(true);
        category_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 0) {
                    iv_one.setSelected(true);
                    iv_two.setSelected(false);
                } else {
                    iv_one.setSelected(false);
                    iv_two.setSelected(true);
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

                ptr.disableWhenHorizontalMove(true);
            }
        });

        //init location
        mLocationClient = new LocationClient(HljAppliation.getContext());
        MyLocationListener mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
        checkVersion();


    }

    private void checkVersion() {
        UpdateManager manager = new UpdateManager(getActivity());
        manager.checkUpdate();

    }


    private void checkKey() {
        String user_id = SharedUtil.getPreferStr(SharedKey.USER_ID);
        String key = SharedUtil.getPreferStr(SharedKey.USER_KEY);
        mPresenter.getAccessInfoPresenter((BaseActivity) getActivity(), user_id, key, new BasePresenter.OnUIThreadListener<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                if (result != null && result) {
                    if (SharedUtil.getPreferBool(SharedKey.USER_LOGINED, false)) {
                        AppToast.ShowToast("登录已过期，请重新登录");
                        SharedUtil.init(getActivity());
                    }
//                    Intent intent = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(intent);
                }
            }
        });
    }

    /**
     * ptr ui listener
     */
    private PtrUIHandler ptrUihandler = new PtrUIHandler() {
        @Override
        public void onUIReset(PtrFrameLayout frame) {
            ll_out.setVisibility(View.VISIBLE);
        }

        @Override
        public void onUIRefreshPrepare(PtrFrameLayout frame) {
            ll_out.setVisibility(View.GONE);

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
     * 监听recylerview 是否滑动到底部
     */
    private RecyclerView.OnScrollListener recyclerView_listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (isSlideToBottom(recyclerView) && data.size() > 2) {  //data.size()>2 为了控制刷新两次
                AppLog.Log("到底部");
                tv_footer.setText("正在加载中...");
                footer_animater.setVisibility(View.VISIBLE);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                loadMore();
            }
        }
    };

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }


    /**
     * 计算两坐标的距离
     *
     * @param s
     * @param e
     */
    private void textJuli(LatLng s, LatLng e) {
        double distance = DistanceUtil.getDistance(s, e);
//        AppLog.Log("长度:" + distance);
    }

    private BaseRecyclerAdapter.OnItemClickListener item_click = new BaseRecyclerAdapter.OnItemClickListener<ItemCategoryMain.DataBean.RowsBean>() {
        @Override
        public void onItemClick(int position, ItemCategoryMain.DataBean.RowsBean item) {
            Intent intent = new Intent(context, DetailInfoActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
           context.overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);

        }
    };


    @Override
    public MvpBasePresenter bindPresenter() {
        mPresenter = new FirstPagePresenter(context);
        return mPresenter;
    }

    private void getListFourData() {

        mPresenter.getMidFourPresenter(context, new BasePresenter.OnUIThreadListener<FourpartData>() {
            @Override
            public void onResult(FourpartData result) {
                if (result != null) {
                    List<FourpartData.DataBean.RowsBean> rows = result.getData().getRows();
                    if (rows != null && rows.size() > 0) {
                        adappter_four_part.setLists(rows);
                    }
                }
            }
        });

    }

    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= 23) {
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                    .send();
        } else {
            startLocation();
            if (!flagFirst) {
                flagFirst = true;
                checkKey();
            }
        }
    }

    private final int SDK_PERMISSION_REQUEST = 127;


    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == 100) {
                startLocation();
                if (!flagFirst) {
                    flagFirst = true;
                    checkKey();
                }
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


    @Override
    public void handleMessage(Message msg) {
        if (msg.what == 0) {
            String location = (String) msg.obj;
            location_tv.setText(location);
            location_tv_out.setText(location);
        }
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location != null) {
                double altitude = location.getAltitude();
                double latitude = location.getLatitude();

                LatLng start = new LatLng(altitude, latitude);
                LatLng end = new LatLng(altitude + 1, latitude + 1);
                textJuli(start, end);
            }

//            AppLog.Log("city = " + location.getCity());
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
            HljAppliation.currentCity = currentCity;
            if (!TextUtils.isEmpty(currentCity)) {
                mHandler.obtainMessage(0, currentCity).sendToTarget();
//                AppLog.Log("定位：" + currentCity);
            } else {
                mHandler.obtainMessage(0, "选择城市").sendToTarget();
            }

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
     * 头部four_part listener
     */
    private FirstPageHeaderFourPartAdappter.OnItemClickListener<FourpartData.DataBean.RowsBean> four_part_listener = new FirstPageHeaderFourPartAdappter.OnItemClickListener<FourpartData.DataBean.RowsBean>() {
        @Override
        public void onItemClick(int position, FourpartData.DataBean.RowsBean data) {
            Intent intent = new Intent(getActivity(), DetailInfoActivity.class);
            ItemCategoryMain.DataBean.RowsBean item = new ItemCategoryMain.DataBean.RowsBean();
            item.setGoods_id(data.getGoods_id());
            intent.putExtra("item", item);
            startActivity(intent);
            context.overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);


        }
    };

    /**
     * 测试数据  头部 分类选项
     *
     * @return
     */
    private void getHeaderList() {
        mPresenter.getCatetoryPresenter(context, new BasePresenter.OnUIThreadListener<List<ItemFirstPageMainHeaderList>>() {
            @Override
            public void onResult(List<ItemFirstPageMainHeaderList> result) {

                if (result != null) {
                    int size = result.size();
                    if (size <= 8) {
                        ll_indicator.setVisibility(View.GONE);
                        category_vp.setScollHoritial(false);
                    }
                    List<ItemFirstPageMainHeaderList> list1 = new ArrayList<>();
                    List<ItemFirstPageMainHeaderList> list2 = new ArrayList<>();

                    for (int i = 0; i < size; i++) {
                        if (i < 8) {
                            list1.add(result.get(i));
                        } else if (i < 16) {
                            list2.add(result.get(i));
                        }
                    }
                    MygridviewAdapter mgAdapter1 = new MygridviewAdapter(getActivity(), list1);
                    gv_one.setAdapter(mgAdapter1);
                    MygridviewAdapter mgAdapter2 = new MygridviewAdapter(getActivity(), list2);
                    gv_two.setAdapter(mgAdapter2);
                }
            }
        });

    }

    private void loadMore() {
        page++;
        getData();
    }

    /**
     * ptr data update
     */
    private PtrHandler ptrHandler = new PtrDefaultHandler2() {
        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {
            loadMore();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            initData();

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
     * 首页主数据
     *
     * @return
     */
    private void getData() {
        mPresenter.getFirstDataPresenter(context, page, new BasePresenter.OnUIThreadListener<List<ItemCategoryMain.DataBean.RowsBean>>() {
            @Override
            public void onResult(List<ItemCategoryMain.DataBean.RowsBean> result) {
                if (result != null) {
                    if (result.size() > 0) {

                        for (int i = 0; i < result.size(); i++) {
                            ItemCategoryMain.DataBean.RowsBean item = result.get(i);
                            data.add(item);
                        }
                        adapter.addDatas(data);
                    } else {
                        tv_footer.setText("已加载全部商品");
                        footer_animater.setVisibility(View.GONE);
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
        page = 1;
        data.clear();
        adapter.notifyDataSetChanged();
        getHeaderList();
        getListFourData();
        getData();
        getPersimmions();
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
                Intent intent1 = new Intent(context, SearchActivity.class);
                context.startActivity(intent1);
                break;
            default:
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
                location_tv_out.setText(currentCity);
                AppLog.Log("定位：" + currentCity);
            } else {
                location_tv.setText("选择城市");
                location_tv_out.setText("选择城市");
            }
        }
    }

}
