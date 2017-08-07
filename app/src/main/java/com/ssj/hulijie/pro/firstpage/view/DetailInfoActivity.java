package com.ssj.hulijie.pro.firstpage.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.DetailImageAdapter;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceAndEvaluateItem;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceEvaluate;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceItem;
import com.ssj.hulijie.pro.firstpage.bean.EvaluateItem;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.pro.firstpage.presenter.DetailPresenter;
import com.ssj.hulijie.pro.firstpage.view.widget.LinearLayoutManagerInScrollView;
import com.ssj.hulijie.pro.firstpage.view.widget.ListViewInScrollView;
import com.ssj.hulijie.pro.firstpage.view.widget.MyScrollView;
import com.ssj.hulijie.pro.firstpage.view.widget.RecylerViewInScrollView;
import com.ssj.hulijie.pro.firstpage.view.widget.ScrollViewListener;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.DensityUtil;
import com.ssj.hulijie.utils.DisplayUtils;
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


/**
 * Created by Administrator on 2017/6/13.
 */

public class DetailInfoActivity extends BaseActivity implements View.OnClickListener {
    private ItemFirstPageMainList item;
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
    private LinearLayout evaluate_base;

    private TextView evaluate_user;
    private TextView evaluate_txt;
    private AppCompatRatingBar evaluate_level;

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


        mPresenter.getDetailPresenter(this, item.getGoods_id(), new BasePresenter.OnUIThreadListener<DetailServiceAndEvaluateItem>() {
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
            DetailServiceEvaluate evaluate = result.getEvaluate();
            //show evaluate
            if (evaluate != null && evaluate.getRows()!=null&&evaluate.getRows().size() > 0) {
                evaluate_base.setVisibility(View.VISIBLE);
                //show evaluate count
                detail_evaluate_count.setText("("+evaluate.getCount()+")");
                //show first evaluate title
                EvaluateItem evaluateItem = evaluate.getRows().get(0);
                if (evaluate != null) {

                    evaluate_user.setText(""); //// TODO: 2017/8/7 评论没有传用户
                    //show evaluate level
                    evaluate_level.setRating(evaluateItem.getEvaluation());

                    //show first evaluate describe
                    evaluate_txt.setText(evaluateItem.getComment());
                }
            } else {
                evaluate_base.setVisibility(View.GONE);
            }

            //show pic
            String default_image_rul = detail.getDefault_image();
            AppLog.Log("default_image_rul:  " + default_image_rul);

            Glide.with(this)
                    .load(default_image_rul)
                    .asBitmap()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading_error)
                    .thumbnail(0.1f)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int imageWidth = resource.getWidth();
                            int imageHeight = resource.getHeight();
                            LinearLayout.LayoutParams para = (LinearLayout.LayoutParams) detail_img.getLayoutParams();
                            para.height = DisplayUtils.screenWidth * imageHeight / imageWidth;
                            para.width = DisplayUtils.screenWidth;
                            detail_img.setLayoutParams(para);
                            detail_img.setImageBitmap(resource);
                        }
                    });

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
        evaluate_level = (AppCompatRatingBar) findViewById(R.id.evaluate_level);
        toolbar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        evaluate_base = (LinearLayout) findViewById(R.id.evaluate_base);

        evaluate_user = (TextView) findViewById(R.id.evaluate_user);
        evaluate_txt = (TextView) findViewById(R.id.evaluate_txt);
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
        detail_descript_img_rv.setLayoutManager(new LinearLayoutManagerInScrollView(this));
        DisplayUtils.initScreen(this);
        int screenWidth = DisplayUtils.screenWidth- DensityUtil.dip2px(this,20);
        AppLog.Log("screenWidth:" + screenWidth);
        adapter = new DetailImageAdapter(this,screenWidth);
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

                Intent i = new Intent(this, OrderActivity.class);
                i.putExtra("item", item);
                startActivity(i);
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
