package com.ssj.hulijie.pro.firstpage.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.utils.TitlebarUtil;
import com.ssj.hulijie.utils.WatcherAdapter;

import static com.ssj.hulijie.R.id.et_name;


/**
 * Created by Administrator on 2017/5/15.
 */

public class OrderActivity extends BaseActivity implements View.OnClickListener {
    private TextView order_buy_count;  //购买数量
    private int count = 1;
    private Button order_sub;
    private boolean isVisible ;  //表示scrollview里面的view是否显示
    private View bottom_btn;
    private View scroll_btn;
    private TextView tv_mark;

    public final static int REQUEST_ADDRESS = 100;


    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_progress);
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

        tv_mark=(TextView)findViewById(R.id.tv_mark);
        String html_str = "<font color='#FF246E'>请仔细核对您填写的手机号</font>，并保持电话畅通，商家会在服务开始前与此号码沟通服务具体事宜";
        Spanned spanned = Html.fromHtml(html_str);
        tv_mark.setText(spanned);


        findViewById(R.id.select_address).setOnClickListener(this);
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
        }
    }



    private void changeBuyCount(){
        if (count > 1) {
           order_sub.setClickable(true);
        } else if (count == 1) {
            order_sub.setClickable(false);
        }
        order_buy_count.setText(count + "");
    }
}
