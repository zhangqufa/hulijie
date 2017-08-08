package com.ssj.hulijie.pro.firstpage.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.AddressItem;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.pro.firstpage.view.widget.SelectPopWindow;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2017/5/15.
 */

public class OrderActivity extends BaseActivity implements View.OnClickListener ,SelectPopWindow.SelectCallBack {
    private TextView order_buy_count;  //购买数量
    private int count = 1;
    private Button order_sub;
    private boolean isVisible ;  //表示scrollview里面的view是否显示
    private View bottom_btn;
    private View scroll_btn;
    private TextView tv_mark;
    private TextView order_address;
    private String date;
    private String time;

    public final static int REQUEST_ADDRESS = 100;
    private SelectPopWindow menuWindow;
    private TextView select_time;
    private ItemFirstPageMainList item;


    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_progress);
        item = getIntent().getParcelableExtra("item");
        initView();
    }

    private void initView() {
        initToolbar();
        order_sub = (Button)findViewById(R.id.order_sub);
        findViewById(R.id.order_add).setOnClickListener(this);
        order_sub.setOnClickListener(this);
        order_sub.setClickable(false);
        order_buy_count = (TextView)findViewById(R.id.order_buy_count);

        EditText et_name=(EditText)findViewById(R.id.et_name);
        et_name.setOnFocusChangeListener(listener);
        EditText et_mobile=(EditText)findViewById(R.id.et_mobile);
        et_mobile.setOnFocusChangeListener(listener);
        EditText et_mark = (EditText) findViewById(R.id.et_mark);
        et_mark.setOnFocusChangeListener(listener);
        bottom_btn = findViewById(R.id.bottom_btn);
        scroll_btn = findViewById(R.id.scroll_btn);

        order_address=(TextView)findViewById(R.id.order_address);
        tv_mark=(TextView)findViewById(R.id.tv_mark);
        String html_str = "<font color='#FF246E'>请仔细核对您填写的手机号</font>，并保持电话畅通，商家会在服务开始前与此号码沟通服务具体事宜";
        Spanned spanned = Html.fromHtml(html_str);
        tv_mark.setText(spanned);


        findViewById(R.id.select_address).setOnClickListener(this);
        findViewById(R.id.select_service_time_base).setOnClickListener(this);
        findViewById(R.id.btn_pay).setOnClickListener(this);
        select_time = (TextView) findViewById(R.id.select_time);
    }


    private View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!isVisible) {
                if (b) {
                    isVisible = true;
                    bottom_btn.setVisibility(View.GONE);
                    scroll_btn.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private void initToolbar() {
        RelativeLayout title_bar_base=(RelativeLayout)findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "预约下单", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        },null);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_add:
                count++;
                changeBuyCount();
                break;
            case R.id.order_sub:
                if (count > 1) {
                    count--;
                    changeBuyCount();
                }
                break;

            case R.id.select_address:
                Intent intent = new Intent(this, SelectAddressActivity.class);
                startActivityForResult(intent, REQUEST_ADDRESS);
                break;
            case R.id.select_service_time_base:
                open(view);

                break;
            case R.id.btn_pay:
                Intent intent1 = new Intent(this, PayActivity.class);
                intent1.putExtra("item", item);
                startActivity(intent1);
                break;
        }
    }

    public void open(View view) {
        findViewById(R.id.ll).setVisibility(View.VISIBLE);
        menuWindow = new SelectPopWindow(this, itemsOnClick);
        menuWindow.setFirt_strs(createMainDatas());
        menuWindow.setMap(createSubDatas());
        menuWindow.initWheel1();
        menuWindow.setCallback(this);
        //显示窗口
        menuWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        menuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                findViewById(R.id.ll).setVisibility(View.GONE);
            }
        });
    }


    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_cancel:
                    break;
                case R.id.btn_done:
                    show();
                    break;
                default:
                    break;
            }
        }

    };

    private List<String> createMainDatas() {
        String[] strings = {"7月13日(周四)", "7月14日(周五)", "7月15日(周六)"};
        return Arrays.asList(strings);
    }

    private HashMap<String, List<String>> createSubDatas() {
        HashMap<String, List<String>> map = new HashMap<>();
        String[] strings = {"7月13日(周四)", "7月14日(周五)", "7月15日(周六)"};
        String[] s1 = {"8:30-9:00", "9:00-9:30", "9:30-10:00"};
        String[] s2 = {"10:00-10:30", "10:30-11:00","11:00-11:30","11:30-12:00"};
        String[] s3 = {"12:00-12:30", "12:30-13:00", "13:00-13:30", "13:30-14:00","14:00-14:30"};
        String[][] ss = {s1, s2, s3};
        for (int i = 0; i < strings.length; i++) {
            map.put(strings[i], Arrays.asList(ss[i]));
        }
        return map;
    }

    @Override
    public void selectDataCallBack(String data) {
        this.date = data;
    }

    @Override
    public void selectTimeCallBack(String time) {
        this.time = time;
    }

    private void show(){
        select_time.setText(date+" "+time);
    }


    private void changeBuyCount(){
        if (count > 1) {
           order_sub.setClickable(true);
        } else if (count == 1) {
            order_sub.setClickable(false);
        }
        order_buy_count.setText(count + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADDRESS && resultCode == RESULT_OK) {
            AddressItem addressItem = data.getParcelableExtra("addressItem");
            if (addressItem != null) {
                order_address.setText(addressItem.getAddress());

            }
        }
    }
}
