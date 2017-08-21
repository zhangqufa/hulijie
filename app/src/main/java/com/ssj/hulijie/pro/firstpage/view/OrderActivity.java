package com.ssj.hulijie.pro.firstpage.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.AddressItem;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceItem;
import com.ssj.hulijie.pro.firstpage.presenter.AddressManagerPresenter;
import com.ssj.hulijie.pro.firstpage.view.widget.SelectPopWindow;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.R.attr.firstDayOfWeek;
import static android.R.attr.format;


/**
 * Created by Administrator on 2017/5/15.
 */

public class OrderActivity extends BaseActivity implements View.OnClickListener, SelectPopWindow.SelectCallBack {
    private TextView order_buy_count;  //购买数量
    private int count = 1;
    private Button order_sub;
    private boolean isVisible;  //表示scrollview里面的view是否显示
    private View bottom_btn;
    private View scroll_btn;
    private TextView tv_mark;
    private TextView order_address;
    private String date;
    private String time;

    public final static int REQUEST_ADDRESS = 100;
    private SelectPopWindow menuWindow;
    private TextView select_time;
    private DetailServiceItem detail;

    private ImageView order_img;
    private TextView order_title;
    private TextView order_price;
    private TextView order_price_total;

    private int startHour;
    private int startMinute;


    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_progress);
        detail = getIntent().getParcelableExtra("detail");
        initView();

        updateUI();

        getAddressNet();
    }

    private void getAddressNet() {
        AddressManagerPresenter presenter = new AddressManagerPresenter(this);
        presenter.getAddressPresenter(this, SharedUtil.getPreferStr(SharedKey.USER_ID), "", new BasePresenter.OnUIThreadListener<List<AddressItem>>() {
            @Override
            public void onResult(List<AddressItem> result, int return_code) {
                if (result != null) {


                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getDefault_addr() == 1) {
                            order_address.setText(result.get(i).getAddress());
                        } else {
                            order_address.setText(result.get(0).getAddress());
                        }
                    }

                }
            }
        });
    }

    private void updateUI() {
        if (detail != null) {
            Glide.with(this).load(detail.getDefault_image()).into(order_img);
            order_title.setText(detail.getGoods_name());
            order_price.setText("￥" + detail.getPrice());
            order_price_total.setText("￥" + detail.getPrice());

            /**
             * <pre>
             *
             *  预约时间 从 9:00 ~ 18:00
             *  9:05预约10:00，9:06预约10:30
             *  9:35预约10:30，9:36预约11:00
             *  1.判断分钟如果  <5分钟              当日的起始时间   hour+1 :00
             *           如果  >5分钟 && < 35分钟                  hour+1 :30
             *  2.判断分钟如果  >35分钟                            hour+2 :00
             * </pre>
             */
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH_mm");
            String hh_mm = dateFormat.format(detail.getSystem_time() * 1000);
            String[] split = hh_mm.split("_");

            int hour = Integer.valueOf(split[0]);
            int minute = Integer.valueOf(split[1]);
            AppLog.Log("minute: " + minute);
            if (minute < 5) {
                startHour = hour + 1;
                startMinute = 0;
            } else if (minute > 5 && minute < 35) {
                startHour = hour + 1;
                startMinute = 30;
            } else if (minute > 35) {
                startHour = hour + 2;
                startMinute = 0;
            }
            setMainDatas();
            setSubDatas();

        }

    }


    private HashMap<String, List<String>> map = new HashMap<>(); //存放wheel第二列数据

    /**
     * 设置wheel 第二列数据
     */
    private void setSubDatas() {
        String[] strings = new String[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            strings[i] = lists.get(i);
        }
        String start_time = startHour + ":" + ((startMinute == 0) ? "00" : "30");
        AppLog.Log("start_time: " + start_time);
        List<String> first_list = new ArrayList<>();
        if (9 <= startHour && startHour < 18) {
            first_list.add(start_time);
            if (startMinute == 0) {
                while (startHour < 18) {
                    first_list.add(startHour + ":30");
                    first_list.add((startHour + 1) + ":00");
                    startHour++;
                }
            } else if (startMinute == 30) {
                while (startHour < 18) {
                    int i = startHour + 1;
                    first_list.add(i + ":00");
                    if (i != 18)
                        first_list.add(i + ":30");
                    startHour++;
                }
            }
            String[] s1 = new String[first_list.size()];
            for (int i = 0; i < first_list.size(); i++) {
                s1[i] = first_list.get(i);
            }
            String[] s2 = {"9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};
            String[][] ss = {s1, s2, s2, s2, s2, s2, s2};
            for (int i = 0; i < strings.length; i++) {
                map.put(strings[i], Arrays.asList(ss[i]));
            }
        } else if (startHour == 18 && startMinute == 0) {
            first_list.add(start_time);
            String[] s1 = new String[first_list.size()];
            for (int i = 0; i < first_list.size(); i++) {
                s1[i] = first_list.get(i);
            }
            String[] s2 = {"9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};
            String[][] ss = {s1, s2, s2, s2, s2, s2, s2};
            for (int i = 0; i < strings.length; i++) {
                map.put(strings[i], Arrays.asList(ss[i]));
            }
        } else {
            String[] s2 = {"9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};
            String[][] ss = {s2, s2, s2, s2, s2, s2, s2};
            for (int i = 0; i < strings.length; i++) {
                map.put(strings[i], Arrays.asList(ss[i]));
            }
        }
    }

    /**
     * 获取wheel 第二列数据
     */
    private HashMap<String, List<String>> createSubDatas() {
        return map;
    }

    private List<String> lists = new ArrayList<>(); //存放wheel 第一列数据

    /**
     * 设置wheel 第一列数据
     */
    private void setMainDatas() {
        long currentTime = detail.getSystem_time() * 1000;
        long day = 24 * 60 * 60 * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日(E)");
        lists.clear();
        if (startHour < 18) {
            for (int i = 0; i < 7; i++) {
                String format = dateFormat.format(currentTime + i * day);
                lists.add(format);
            }

        } else if (startHour == 18) {
            if (startMinute == 0) {
                for (int i = 0; i < 7; i++) {
                    String format = dateFormat.format(currentTime + i * day);
                    lists.add(format);
                }
            } else {
                for (int i = 0; i < 7; i++) {
                    String format = dateFormat.format(currentTime + i * day + day);
                    lists.add(format);
                }
            }
        } else if (startHour > 18) {
            for (int i = 0; i < 7; i++) {
                String format = dateFormat.format(currentTime + i * day + day);
                lists.add(format);
            }
        }
    }

    /**
     * 获取wheel 第一列数据
     */
    private List<String> createMainDatas() {
        return lists;
    }

    private void initView() {
        initToolbar();
        order_sub = (Button) findViewById(R.id.order_sub);
        findViewById(R.id.order_add).setOnClickListener(this);
        order_sub.setOnClickListener(this);
        order_sub.setClickable(false);
        order_buy_count = (TextView) findViewById(R.id.order_buy_count);

        EditText et_name = (EditText) findViewById(R.id.et_name);
        et_name.setOnFocusChangeListener(listener);
        EditText et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_mobile.setOnFocusChangeListener(listener);
        EditText et_mark = (EditText) findViewById(R.id.et_mark);
        et_mark.setOnFocusChangeListener(listener);
        bottom_btn = findViewById(R.id.bottom_btn);
        scroll_btn = findViewById(R.id.scroll_btn);

        order_address = (TextView) findViewById(R.id.order_address);
        tv_mark = (TextView) findViewById(R.id.tv_mark);
        String html_str = "<font color='#FF246E'>请仔细核对您填写的手机号</font>，并保持电话畅通，商家会在服务开始前与此号码沟通服务具体事宜";
        Spanned spanned = Html.fromHtml(html_str);
        tv_mark.setText(spanned);


        findViewById(R.id.select_address).setOnClickListener(this);
        findViewById(R.id.select_service_time_base).setOnClickListener(this);
        findViewById(R.id.btn_pay).setOnClickListener(this);
        select_time = (TextView) findViewById(R.id.select_time);
        order_price = (TextView) findViewById(R.id.order_price);
        order_title = (TextView) findViewById(R.id.order_title);
        order_price_total = (TextView) findViewById(R.id.order_price_total);

        order_img = (ImageView) findViewById(R.id.order_img);
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
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "预约下单", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
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
                intent1.putExtra("detail", detail);
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


    @Override
    public void selectDataCallBack(String data) {
        this.date = data;
    }

    @Override
    public void selectTimeCallBack(String time) {
        this.time = time;
    }

    private void show() {
        select_time.setText(date + " " + time);
    }


    private void changeBuyCount() {
        if (count > 1) {
            order_sub.setClickable(true);
        } else if (count == 1) {
            order_sub.setClickable(false);
        }
        order_buy_count.setText(count + "");

        float v = Float.valueOf(order_buy_count.getText().toString()) * Float.valueOf(detail.getPrice());
        order_price_total.setText("￥" + v);
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
