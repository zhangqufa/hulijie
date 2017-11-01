package com.ssj.hulijie.pro.mine.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.bean.ItemOrderResp;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/11/1.
 */

public class OrderItemDetailActivity extends BaseActivity {
    private ItemOrderResp.DataBean.RowsBean orderItem;
    private TextView tv_status; //状态
    private TextView price; //服务价格
    private TextView count; //服务数量
    private TextView address; //服务地址
    private TextView service_time; //服务时间
    private TextView name; //联系人
    private TextView mobile; //联系人电话
    private TextView order_id; //订单号
    private TextView add_time; //下单 时间
    private TextView total_price; //总的价格
    private TextView title; //服务名
    private ImageView img; //商品图片
    private Button btn_left;
    private Button btn_right;

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_item_detail);
        orderItem = getIntent().getParcelableExtra("orderItem");
        initToolbar();
        initView();
        initData();
    }

    private void initView() {
        tv_status = (TextView) findViewById(R.id.status);
        price = (TextView) findViewById(R.id.price);
        count = (TextView) findViewById(R.id.count);
        address = (TextView) findViewById(R.id.address);
        service_time = (TextView) findViewById(R.id.service_time);
        name = (TextView) findViewById(R.id.name);
        mobile = (TextView) findViewById(R.id.mobile);
        order_id = (TextView) findViewById(R.id.order_id);
        add_time = (TextView) findViewById(R.id.add_time);
        total_price = (TextView) findViewById(R.id.total_price);
        title = (TextView) findViewById(R.id.title);
        img = (ImageView) findViewById(R.id.img);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_right = (Button) findViewById(R.id.btn_right);
    }

    private void initData() {
        if (orderItem != null) {
            String status_str = "";
            String left = "";
            String right = "";
            /**
             * 订单状态：
             0 取消订单  --未完成
             11 等待买家付款 --未完成
             20 买家已付款 --未完成
             30 商家已服务  --待评价
             40 交易成功 --已完成
             3 退款中 --未完成
             4 已退款 --未完成
             */
            int status = orderItem.getStatus();
            switch (status) {
                case 0:
                    status_str = "取消订单";
                    right = "删除订单";
                    break;
                case 11:
                    status_str = "等待买家付款";
                    left = "取消订单";
                    right = "立即付款";
                    break;
                case 20:
                    status_str = "买家已付款";
                    right = "退款";
                    break;
                case 30:
                    status_str = "待评价";
                    right = "去评价";
                    break;
                case 40:
                    status_str = "交易成功";
                    left = "退款";
                    right = "确认完成";
                    break;
                case 3:
                    status_str = "退款中";
                    break;
                case 4:
                    status_str = "已退款";
                    right = "删除订单";
                    break;

                default:
                    break;
            }
            tv_status.setText(status_str);
            //初始化两个按钮
            if (TextUtils.isEmpty(left)) {
                btn_left.setVisibility(View.INVISIBLE);
            } else {
                btn_left.setText(left);
                btn_left.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(right)) {
                btn_right.setVisibility(View.INVISIBLE);
            } else {
                btn_right.setText(right);
                btn_right.setVisibility(View.VISIBLE);
            }


            price.setText(orderItem.getGoods_amount());


            Glide.with(this).load(orderItem.getDefault_image()).into(img);
            count.setText("x"+orderItem.getQuantity());
            address.setText(orderItem.getService_address());
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日(E) HH:mm");
            service_time.setText(format.format(orderItem.getService_time() * 1000));
            name.setText(orderItem.getBuyer_name());
            mobile.setText(orderItem.getMobile());
            order_id.setText(orderItem.getOrder_id());
            add_time.setText(format.format(orderItem.getAdd_time()*1000));
            total_price.setText(orderItem.getOrder_amount());
            title.setText(orderItem.getGoods_name());
        }

    }

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, "订单详情", android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }
}
