package com.ssj.hulijie.pro.firstpage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.ssj.hulijie.R;
import com.ssj.hulijie.base.HljAppliation;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.AddressAdapter;
import com.ssj.hulijie.pro.firstpage.bean.AddressItem;
import com.ssj.hulijie.pro.firstpage.bean.PoiSearchResults;
import com.ssj.hulijie.pro.firstpage.presenter.AddressManagerPresenter;
import com.ssj.hulijie.pro.mine.view.LoginActivity;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.Constant;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;
import com.ssj.hulijie.utils.WatcherAdapter;
import com.ssj.hulijie.widget.recylerview.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/7/6
 */

public class AddressActivity extends BaseActivity {
    private RecyclerView rv;
    private AddressAdapter adatper;
    private EditText et_address, et_meng;
    private RelativeLayout base_et_meng;
    private TextView tv_near;
    private PoiSearch mPoiSearch;
    private List<PoiSearchResults> lists = new ArrayList<>();
    private boolean isTextWatcher = true;  //是否监听 Text变化， 主要解决， removeTextWatcher，不及时，导致remove后还会去执行监听
    private  AddressItem addressItem;
    private PoiSearchResults poiSearchResults;
    @Override
    public MvpBasePresenter bindPresenter() {
        return new AddressManagerPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_address);
         addressItem  = getIntent().getParcelableExtra("addressItem");
        initToolbar();
        initView();
        initSearchPoi();
    }

    private void initSearchPoi() {
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
    }

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "添加地址", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }

    private void initView() {

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        View header = LayoutInflater.from(this).inflate(R.layout.address_header, rv, false);

        base_et_meng = (RelativeLayout) header.findViewById(R.id.base_et_meng);
        et_address = (EditText) header.findViewById(R.id.et_address);
        et_meng = (EditText) header.findViewById(R.id.et_meng);
        tv_near = (TextView) header.findViewById(R.id.tv_near);
        et_address.addTextChangedListener(listener);
        et_address.setOnFocusChangeListener(listener_focus);
        adatper = new AddressAdapter(this);
        adatper.setOnItemClickListener(itemClickListener);
        adatper.setHeaderView(header);
        rv.setAdapter(adatper);

        uiShow();

        AppLog.Log("location:" + HljAppliation.currentCity);
    }

    private View.OnFocusChangeListener listener_focus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                et_address.addTextChangedListener(listener);
                isTextWatcher = true;
            } else {
                et_address.removeTextChangedListener(listener);
                isTextWatcher = false;
            }

        }
    };

    private AddressAdapter.OnItemClickListener<PoiSearchResults> itemClickListener = new BaseRecyclerAdapter.OnItemClickListener<PoiSearchResults>() {
        @Override
        public void onItemClick(int position, PoiSearchResults data) {
            AppLog.Log("data:"+data.toString());
            if (data == null) {
                return;
            }
            poiSearchResults = data;
            et_address.removeTextChangedListener(listener);
            isTextWatcher = false;
            et_address.setText(data.getMname());
            et_meng.setFocusable(true);
            et_meng.setFocusableInTouchMode(true);
            et_meng.requestFocus();
            et_meng.requestFocusFromTouch();
            uiShow();
            lists.clear();
            adatper.addDatas(lists);
        }
    };


    /**
     * 针对 base_et_ment显示的状态
     */
    private void uiHide() {
        base_et_meng.setVisibility(View.GONE);
        tv_near.setVisibility(View.VISIBLE);

    }

    private void uiShow() {
        base_et_meng.setVisibility(View.VISIBLE);
        tv_near.setVisibility(View.GONE);

    }

    /**
     * "addr_id": "3",
     * "region_name": "中国	浙江省	台州	路桥区	十里长街",
     * "address": "椒江",
     * "phone_mob": "18868813294"
     *
     * @param view
     */
    public void btn_commit(View view) {
        if (TextUtils.isEmpty(et_address.getText().toString()) || TextUtils.isEmpty(et_meng.getText().toString())) {
            AppToast.ShowToast("内容不可为空");
            return;
        }

        ((AddressManagerPresenter) presenter).addAddressPresenter(this
                , poiSearchResults==null?"":poiSearchResults.getMaddress()
                , et_address.getText().toString() + " " + et_meng.getText().toString()
                , SharedUtil.getPreferStr(SharedKey.USER_MOBILE)
                , SharedUtil.getPreferStr(SharedKey.USER_ID)
                , addressItem==null? "" : addressItem.getAddr_id()
                ,1
                , new BasePresenter.OnUIThreadListener<Boolean>() {
                    @Override
                    public void onResult(Boolean result, int return_code) {
                        if (result) {
                            finish();
                        } else {
                            AppToast.ShowToast("提交失败！");
                        }
                    }
                });
    }

    private TextWatcher listener = new WatcherAdapter() {
        @Override
        public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                  int arg3) {
            if (!isTextWatcher || cs.length() <= 0) {
                return;
            }
            AppLog.Log("remove after");
            /**
             * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
             */
            PoiCitySearchOption poiCitySearchOption = new PoiCitySearchOption().city(HljAppliation.currentCity)
                    .keyword(cs.toString());
            AppLog.Log("搜索城市：" + HljAppliation.currentCity);
            mPoiSearch.searchInCity(poiCitySearchOption);

        }

        @Override
        public void afterTextChanged(Editable editable) {
            super.afterTextChanged(editable);
            if (editable.toString().length() > 0) {
                uiHide();

            } else {
                uiShow();
                lists.clear();
                adatper.addDatas(lists);
            }

        }
    };


    private OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
        private String poiname;
        private String poiadd;

        @Override
        public void onGetPoiResult(PoiResult result) {

            // 获取POI检索结果
            if (result == null
                    || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                AppLog.Log("未找到结果");
                return;
            }
            lists.clear();
            if (result.getAllPoi() == null) {
                AppLog.Log("请重新输入");
                return;
            } else {
                for (int i = 0; i < result.getAllPoi().size(); i++) {
                    poiname = result.getAllPoi().get(i).name;
                    poiadd = result.getAllPoi().get(i).address;
                    LatLng poilocation = result.getAllPoi().get(i).location;

                    if (poilocation != null) {
                        Double latitude = poilocation.latitude;
                        Double longitude = poilocation.longitude;

                        // 实例化一个地理编码查询对象
                        GeoCoder geoCoder = GeoCoder.newInstance();
                        // 设置反地理编码位置坐标
                        ReverseGeoCodeOption op = new ReverseGeoCodeOption();
                        op.location(poilocation);
                        // 发起反地理编码请求(经纬度->地址信息)
                        geoCoder.reverseGeoCode(op);
                        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

                            @Override
                            public void onGetReverseGeoCodeResult(
                                    ReverseGeoCodeResult arg0) {
                                poiadd = arg0.getAddress();
                            }

                            @Override
                            public void onGetGeoCodeResult(GeoCodeResult arg0) {
                            }
                        });
                        PoiSearchResults results = new PoiSearchResults(
                                poiname, poiadd, latitude, longitude);
                        lists.add(results);
                        AppLog.Log("lists:" + lists.toString());
                    } else {
                        AppLog.Log("未找到结果,请重新输入");
                    }
                }

            }
            adatper.addDatas(lists);
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult result) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
    }
}
