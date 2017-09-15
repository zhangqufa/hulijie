package com.ssj.hulijie.pro.firstpage.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.db.dao.SearchHistoryDao;
import com.ssj.hulijie.pro.db.helper.MyDatabaseHelper;
import com.ssj.hulijie.pro.db.model.ItemSearchHistory;
import com.ssj.hulijie.pro.firstpage.presenter.SearchPresenter;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.DensityUtil;
import com.ssj.hulijie.widget.editext.ClearEditText;
import com.ssj.hulijie.widget.flowlayout.FlowLayout;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by vic_zhang .
 * on 2017/4/7
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private FlowLayout fl_hot_search, fl_history_record;
    private List<TextView> tvs_hot_search, tvs_history_search;
    private ClearEditText act_search_et;
    private SearchHistoryDao dao;
    private SearchPresenter presenter;

    @Override
    public MvpBasePresenter bindPresenter() {
        presenter = new SearchPresenter(this);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);
        initView();
        initData();
    }

    private void initData() {
        presenter.getHotKeysPresenter(this, new BasePresenter.OnUIThreadListener<List<String>>() {
            @Override
            public void onResult(List<String> result ) {
                if (result != null && result.size() > 0) {

                    for (int i = 0; i < result.size(); i++) {
                        addHotsearchDataForFlowView(result.get(i), fl_hot_search, tvs_hot_search);
                    }
                }
            }
        });

    }

    private void initView() {
        act_search_et = (ClearEditText) findViewById(R.id.act_search_et);
        findViewById(R.id.tv_search).setOnClickListener(this);
        findViewById(R.id.iv_navigation_back).setOnClickListener(this);
        findViewById(R.id.qingchu).setOnClickListener(this);

        //init dao
        dao = new SearchHistoryDao(new MyDatabaseHelper(this));

        fl_hot_search = (FlowLayout) findViewById(R.id.hot_search);
        tvs_hot_search = new ArrayList<>();

        fl_history_record = (FlowLayout) findViewById(R.id.history_record);
        tvs_history_search = new ArrayList<>();
        initHistoryData();




    }

    private void initHistoryData() {
        List<ItemSearchHistory> histories = dao.getAll();
        for (ItemSearchHistory history : histories) {
            addHotsearchDataForFlowView(history.getName(), fl_history_record, tvs_history_search);
        }
    }

    private void addHotsearchDataForFlowView(String str, FlowLayout flowLayout, List<TextView> tvs_list) {
        int ranHeight = DensityUtil.dip2px(this, 30);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
        lp.setMargins(0, 0, DensityUtil.dip2px(this, 10), 0);
        TextView tv = new TextView(this);
        tv.setPadding(DensityUtil.dip2px(this, 13), 0, DensityUtil.dip2px(this, 13), 0);
        tv.setTextColor(Color.parseColor("#666666"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tv.setText(str);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setLines(1);
        tv.setBackgroundResource(R.drawable.shape_btn_grey_f5f5f5);
        tvs_list.add(tv);  //add onclick event

        //add 显示动画
        ObjectAnimator animator_x = ObjectAnimator.ofFloat(tv, "scaleX", 0.3f, 1f);
        ObjectAnimator animator_y = ObjectAnimator.ofFloat(tv, "scaleY", 0.3f, 1f);
        AnimatorSet set  = new AnimatorSet();
        set.playTogether(animator_x, animator_y);
        set.setDuration(300);
        set.start();

        flowLayout.addView(tv, lp);

        //监听点击
        for (TextView textView : tvs_list) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v instanceof TextView) {
                        TextView tv = (TextView) v;
                        goSearchResult(tv.getText().toString());
                    }
                }
            });
        }

    }

    /**
     * 搜索请求
     *
     * @param string
     */
    private void goSearchResult(String string) {
        savaDB(string);
        Intent intent = new Intent(this, SearchResultEditActivity.class);
        intent.putExtra("key", string);
        startActivity(intent);

    }

    private void savaDB(String string) {
        if (TextUtils.isEmpty(string)) {
            return;
        }
        List<ItemSearchHistory> all = dao.getAll();
        if (all.size() > 0) {
            for (ItemSearchHistory history : all) {
                String name = history.getName();
                if (string.equals(name)) {
                    return;
                }
            }
        }
        ItemSearchHistory history = new ItemSearchHistory();
        history.setName(string);
        history.setAddtime(System.currentTimeMillis());
        try {
            int count = dao.count();
            if (count >= 5) {
                dao.deleteLast();
            }
            long success = dao.insert(history);
            if (success > 0) {
//                AppToast.ShowToast("success:" + success);
                addHotsearchDataForFlowView(string, fl_history_record, tvs_history_search);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_navigation_back:
                finish();
                break;
            case R.id.tv_search:
                goSearchResult(act_search_et.getText().toString());
                break;
            case R.id.qingchu:
                dao.deleteAll();
                fl_history_record.removeAllViews();
                break;
        }
    }

    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
        AppLog.Log("slideOffset_search: "+slideOffset);

    }
}
