package com.ssj.hulijie.pro.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.TabHost;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.view.FirstPageFrament;
import com.ssj.hulijie.pro.found.view.FoundFragment;
import com.ssj.hulijie.pro.home.bean.TabItem;
import com.ssj.hulijie.pro.mine.view.MineFragment;
import com.ssj.hulijie.pro.msg.view.MsgFragment;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppManager;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.StatusBarColorUtils;
import com.ssj.hulijie.widget.fragmenttabhost.MyFragmentTabHost;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {

    private List<TabItem> tabItemList;
    public static final int CHANGE_TAB_RESULT = 1;
    private MyFragmentTabHost fragmentTabHost;
    private boolean isShowDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_main);
        StatusBarColorUtils.setWindowStatusBarColor(this,R.color.colorPrimary);
        initTabData();
        initTabHost();
    }


    /**
     * 切换tab
     *
     * @param tab
     */
    public void changeTab(int tab) {
        AppLog.Log("chageTab: " + tab);
        fragmentTabHost.setCurrentTab(tab);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
//                firstUserPresent();
            } else if (msg.what == 1) {
                int tab = (int) msg.obj;
                fragmentTabHost.setCurrentTab(tab);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (isShowDlg) {
            isShowDlg = false;
            mHandler.sendEmptyMessageDelayed(0, 20);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * check least version
     */
    private void checkVersionUpdate() {
    }

    /**
     * 初始化主页选项卡视图
     */
    private void initTabHost() {
        //获取FragmentTabHost
        fragmentTabHost = (MyFragmentTabHost) findViewById(android.R.id.tabhost);
        //绑定TabHost  （BODY）
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        //去掉分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < tabItemList.size(); i++) {
            TabItem tabItem = tabItemList.get(i);
            //绑定Fragment(将Fragment添加到FragmentTabHost组件上面)
            // newTabSpec  代表Tab名字
            //setIndicator   图片（今天我们采用布局文件--Tab到样式我们自己做）
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(tabItem.getTitleString()).setIndicator(tabItem.getView());
            AppLog.Log("fragment_tag:" + tabItem.getTitleString());
            AppLog.Log("fragment_tag:" + tabItem.getTitleString());
            //添加Fragment
            //tabSpec:选项卡
            //tabItem.getFragmentClass():具体的Fragment
            //tabItem.getBundle():给我们的具体的Fragment传参数
            fragmentTabHost.addTab(tabSpec, tabItem.getFragmentClass(), tabItem.getBundle());
            //给我们的Tab按钮设置背景
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.white));
            //监听点击Tab
            fragmentTabHost.setOnTabChangedListener(this);
            //默认选中第一个Tab
            if (i == 0) {
                tabItem.setChecked(true);
            }
        }

    }


//    //改变 底部消息数据
//    public void changeXiaoxiVisible(boolean b) {
//        TabItem tabItem = tabItemList.get(Constant.LIVE_FRAG);
//        if (b) {
//            tabItem.setXiaoxiVisible();
//        } else {
//            tabItem.setXiaoxiGone();
//        }
//    }


    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    private void initTabData() {
        tabItemList = new ArrayList<>();
        tabItemList.add(new TabItem(this, R.mipmap.first_page_off, R.mipmap.first_page_on, R.string.page_1, FirstPageFrament.class));
        //取消超级返功能
        tabItemList.add(new TabItem(this, R.mipmap.discover_off, R.mipmap.discover_on, R.string.page_2, FoundFragment.class));
        tabItemList.add(new TabItem(this, R.mipmap.msg_off, R.mipmap.msg_on, R.string.page_3, MsgFragment.class));
        tabItemList.add(new TabItem(this, R.mipmap.mine_off, R.mipmap.mine_on, R.string.page_4, MineFragment.class));
    }

    @Override
    public void onTabChanged(String tabId) {
        AppLog.Log("tabId:" + tabId);
        //重置Tab样式
        for (int i = 0; i < tabItemList.size(); i++) {
            TabItem tabItem = tabItemList.get(i);
            if (tabId.equals(tabItem.getTitleString())) {
                //选中设置为选中壮体啊
                tabItem.setChecked(true);
            } else {
                //没有选择Tab样式设置为正常
                tabItem.setChecked(false);
            }
        }
    }

    /**
     * 回调给首页， 用于计算首页floatView 上方View离底部的距离
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && listener != null) {
            listener.hasFoucusCallback();
        }
//        if (isFirst) {
//            isFirst = false;
//            FirstPageFragment firstPageFragment = (FirstPageFragment) getSupportFragmentManager().findFragmentByTag(tabItemList.get(0).getTitleString());
//            if (firstPageFragment != null) {
//                firstPageFragment.setiToSuperQuanTab(new FirstPageFragment.IToSuperQuanTab() {
//                    @Override
//                    public void onTabCallBack(int position) {
//                        fragmentTabHost.setCurrentTab(position);
//                    }
//                });
//            }
//        }

    }

    private HasFocusListener listener;

    public void setListener(HasFocusListener listener) {
        this.listener = listener;
    }

    //此接口调onWindowFocusChange函数给fragment
    public interface HasFocusListener {
        void hasFoucusCallback();
    }


    //press two times exit app
    private long mExitTime; // 记录登录按钮最后一次点击的时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                AppToast.ShowToast("再按一次退出" + getString(R.string.app_name));
            } else {
                AppManager.getAppManager().AppExit();
            }
            mExitTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppLog.Log("MainActivity_onActivityResult");
        if (resultCode == CHANGE_TAB_RESULT) {
            fragmentTabHost.setCurrentTab(0);
        }
    }


    public void startActivityForBack(Intent intent) {
        mSwipeBackHelper.forward(intent);
    }


}
