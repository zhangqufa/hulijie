package com.ssj.hulijie.pro.mine.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.view.widget.TabPageIndicator;
import com.ssj.hulijie.utils.DensityUtil;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vic_zhang .
 * on 2017/7/19
 */

public class MineOrderListActivity extends BaseActivity {

    public static final String DEFAULT_PAGE = "default_page";
    private TabPageIndicator mTabTl;
    private ViewPager mContentVp;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private int defaultPage = 0;

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_order_list);
        defaultPage = getIntent().getIntExtra(DEFAULT_PAGE, 0);
        initToolBar();
        initView();
    }

    private void initView() {
        mTabTl = (TabPageIndicator) findViewById(R.id.tl_tab);
        mTabTl.setBackgroundColor(getResources().getColor(android.R.color.white));
        mContentVp = (ViewPager) findViewById(R.id.vp_content);

        initContent();
        initTab();
        mContentVp.setCurrentItem(defaultPage);
    }

    private void initTab() {

        mTabTl.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_SAME);// 设置模式，一定要先设置模式
//        mTabTl.setDividerColor(R.color.colorPrimary);// 设置分割线的颜色
//        mTabTl.setDividerPadding(DensityUtil.dip2px(this, 10));
        mTabTl.setIndicatorPaddingLeft(DensityUtil.dip2px(this, 10));
        mTabTl.setIndicatorPaddingRight(DensityUtil.dip2px(this, 10));
        mTabTl.setIndicatorHeight(DensityUtil.dip2px(this, 2));
        mTabTl.setIndicatorColorResource(R.color.colorPrimary);// 设置底部导航线的颜色
        mTabTl.setTextColorSelectedResource(R.color.colorPrimary);// 设置tab标题选中的颜色
        mTabTl.setTextColorResource(R.color.comm_grey_666666);// 设置tab标题未被选中的颜色
        mTabTl.setTextSize(DensityUtil.sp2px(this, 14));// 设置字体大小
        mTabTl.setUnderlineHeight(1);
    }


    private void initContent() {
        tabIndicators = new ArrayList<>();
        tabIndicators.add("未完成");
        tabIndicators.add("已完成");
        tabIndicators.add("待评价");
        tabIndicators.add("全部订单");
        tabFragments = new ArrayList<>();
        for (String s : tabIndicators) {
            tabFragments.add(OrderListFragment.newInstance(s));
        }
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);

        mContentVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int i) {
                // 测试只有ViewPager在第0页时才开启滑动返回
                setSwipeBackEnable(i == 0);
            }
        });
        mTabTl.setViewPager(mContentVp);
    }

    public void changeTab() {
        mContentVp.setCurrentItem(1);

    }

    private void initToolBar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "我的订单", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }


    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }


}
