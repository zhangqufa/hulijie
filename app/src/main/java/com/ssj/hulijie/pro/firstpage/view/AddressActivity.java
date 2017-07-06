package com.ssj.hulijie.pro.firstpage.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
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
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.AddressAdapter;
import com.ssj.hulijie.pro.firstpage.bean.PoiSearchResults;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
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
    private SuggestionSearch mSuggestionSearch;
    private List<PoiSearchResults> lists = new ArrayList<>();
    private boolean isTextWatcher = true;

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_address);
        initToolbar();
        initView();
        initSearchPoi();
    }

    private void initSearchPoi() {
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        mSuggestionSearch = SuggestionSearch.newInstance();
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
        adatper = new AddressAdapter(this);
        adatper.setOnItemClickListener(itemClickListener);
        adatper.setHeaderView(header);
        rv.setAdapter(adatper);

        uiShow();

        AppLog.Log("location:" + HljAppliation.currentCity);
    }

    private AddressAdapter.OnItemClickListener<PoiSearchResults> itemClickListener = new BaseRecyclerAdapter.OnItemClickListener<PoiSearchResults>() {
        @Override
        public void onItemClick(int position, PoiSearchResults data) {
            et_address.removeTextChangedListener(listener);
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

    public void btn_commit(View view) {

    }

    private TextWatcher listener = new WatcherAdapter() {
        @Override
        public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                  int arg3) {
            if (cs.length() <= 0) {
                return;
            }

            /**
             * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
             */

            mSuggestionSearch
                    .requestSuggestion((new SuggestionSearchOption())
                            .keyword(cs.toString()).city(HljAppliation.currentCity));
            PoiCitySearchOption poiCitySearchOption = new PoiCitySearchOption().city(HljAppliation.currentCity)
                    .keyword(et_address.getText().toString());
            mPoiSearch.searchInCity(poiCitySearchOption);

        }

        @Override
        public void afterTextChanged(Editable editable) {
            super.afterTextChanged(editable);
            if (editable.toString().length() > 0) {
                uiHide();

            } else {
                uiShow();
                List<PoiSearchResults> data = new ArrayList<>();
                adatper.addDatas(data);
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
}
