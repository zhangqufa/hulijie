package com.ssj.hulijie.pro.firstpage.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.DetailImageAdapter;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceAndEvaluateItem;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceItem;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.pro.firstpage.presenter.DetailPresenter;
import com.ssj.hulijie.pro.firstpage.view.location.PingYinUtil;
import com.ssj.hulijie.pro.firstpage.view.widget.ListViewInScrollView;
import com.ssj.hulijie.pro.firstpage.view.widget.MyScrollView;
import com.ssj.hulijie.pro.firstpage.view.widget.RecylerViewInScrollView;
import com.ssj.hulijie.pro.firstpage.view.widget.ScrollViewListener;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.PictureUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.description;
import static android.R.attr.resource;
import static com.ssj.hulijie.R.id.cancel_action;
import static com.ssj.hulijie.R.id.iv;


/**
 * Created by Administrator on 2017/6/13.
 */

public class DetailInfoActivity extends BaseActivity implements View.OnClickListener {
    private ItemFirstPageMainList item;
    private Toolbar toolbar;
    private MyScrollView sv;
    private int height;
    private TextView nav_center_title;
    private RelativeLayout toolbar_base;
    private boolean isFirst = true;
    private ImageView iv_navigation_back;
    private ImageView iv_navigation_right;
    private ListViewInScrollView lv;
    private DetailPresenter mPresenter;
    private ImageView detail_img; //图片
    private TextView detail_title;  //title
    private TextView detail_price;
    private ImageView detail_descript_img;  //详情图片
    private TextView detail_evaluate_count; //评论数
    private RecylerViewInScrollView detail_descript_img_rv;
    private DetailImageAdapter adapter;


    @Override
    public MvpBasePresenter bindPresenter() {
        mPresenter = new DetailPresenter(this);
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_service_detail);
        item = getIntent().getParcelableExtra("item");
        AppLog.Log("Item: " + item);

        initView();

        initData();
    }

    private void initData() {
        if (item == null) {
            return;
        }


        mPresenter.getDetailPresenter(this, "3173", new BasePresenter.OnUIThreadListener<DetailServiceAndEvaluateItem>() {
            @Override
            public void onResult(DetailServiceAndEvaluateItem result,int return_code) {
                if (result != null) {
                    AppLog.Log("detail: " + result.toString());
                    updateUI(result);
                }
            }
        });
    }

    private void updateUI(DetailServiceAndEvaluateItem result) {
        if (result != null) {
            DetailServiceItem detail = result.getDetail();
            //show pic
            String default_image_rul = detail.getDefault_image();
            AppLog.Log("default_image_rul:  " + default_image_rul);
            //show title
            String cate_name = detail.getGoods_name();
            detail_title.setText(cate_name);
            //show price
            String price = detail.getPrice();
            detail_price.setText("¥ " + price);

            //show detial pic
            List<String> img = detail.getImg();
            AppLog.Log("img: "+img);
            adapter.setLists(img);


        }

    }





    private void initView() {
        toolbar_base = (RelativeLayout) findViewById(R.id.title_bar_base);

        nav_center_title = (TextView) findViewById(R.id.tv_navigation_center);
        iv_navigation_back = (ImageView) findViewById(R.id.iv_navigation_back);
        iv_navigation_right = (ImageView) findViewById(R.id.iv_navigation_right);
        iv_navigation_back.setOnClickListener(this);
        iv_navigation_right.setOnClickListener(this);
        sv = (MyScrollView) getViewId(R.id.sv);
        sv.setScrollViewListener(listener);


        detail_img = (ImageView) findViewById(R.id.detail_img);
        detail_descript_img = (ImageView) findViewById(R.id.detail_descript_img);
        detail_title = (TextView) findViewById(R.id.detail_title);
        detail_price = (TextView) findViewById(R.id.detail_price);
        detail_evaluate_count = (TextView) findViewById(R.id.detail_evaluate_count);

        findViewById(R.id.order_btn).setOnClickListener(this);
        findViewById(R.id.check_all_evaluate).setOnClickListener(this);

        detail_descript_img_rv = (RecylerViewInScrollView) findViewById(R.id.detail_descript_img_rv);
        detail_descript_img_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailImageAdapter(this);
        detail_descript_img_rv.setAdapter(adapter);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst) {
            isFirst = false;
            height = toolbar_base.getHeight();
        }
    }

    private ScrollViewListener listener = new ScrollViewListener() {
        @Override
        public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
            if (y < height * 2 && y <= 255) {
                toolbar_base.setBackgroundColor(Color.argb(y, 255, 255, 255));
                nav_center_title.setTextColor(Color.argb(y, 0, 0, 0));
                if (y > 200) {
                    iv_navigation_back.setImageResource(R.mipmap.back__btn_re);
                    iv_navigation_right.setImageResource(R.mipmap.share_red);
                } else {
                    iv_navigation_back.setImageResource(R.mipmap.back_btn_huan);
                    iv_navigation_right.setImageResource(R.mipmap.share_btn_huan);
                }
            } else {
                toolbar_base.setBackgroundColor(Color.argb(255, 255, 255, 255));
                nav_center_title.setTextColor(Color.argb(255, 0, 0, 0));
            }


        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_navigation_back:
                finish();
                break;
            case R.id.iv_navigation_right:
                Bitmap bitmap = getBitmapByView(sv);
//                Bitmap bitmap1 = compressImage(bitmap);
                String s = savePic(bitmap);
                Bitmap smallBitmap = PictureUtil.getSmallBitmap(s, 640, 800);
                String s1 = savePic(smallBitmap);

                AppLog.Log("path_s:"+s+" , path_s1:"+ s1);
                break;

            case R.id.order_btn:

                startActivity(new Intent(this, OrderActivity.class));
                break;
            case R.id.check_all_evaluate:
                startActivity(new Intent(this,CheckAllEvaluateActivity.class));
                break;
        }
    }

    /**
     * 截取scrollview的屏幕
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++ ) {
            h  = scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(
                    Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 压缩图片
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法、这里100表示不压缩、把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 100) {
            // 重置baos
            baos.reset();
            // 这里压缩options%、把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 保存到sdcard
     * @param b
     * @return
     */
    public static String savePic(Bitmap b) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
                Locale.US);
        File outfile = new File("/sdcard/image");
        // 如果文件不存在、则创建一个新文件
        if (!outfile.isDirectory()) {
            try {
                outfile.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fname = outfile  + "/"+   sdf.format(new Date()) +  ".png";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fname);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fname;
    }

}
