package com.ssj.hulijie.pro.firstpage.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ssj.hulijie.R;
import com.ssj.hulijie.alipay.Constants;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.adapter.DetailImageAdapter;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceAndEvaluateItem;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceEvaluate;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceItem;
import com.ssj.hulijie.pro.firstpage.bean.EvaluateItem;
import com.ssj.hulijie.pro.firstpage.bean.ItemCategory;
import com.ssj.hulijie.pro.firstpage.bean.ItemCategoryMain;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainList;
import com.ssj.hulijie.pro.firstpage.bean.ItemRemmendList;
import com.ssj.hulijie.pro.firstpage.presenter.DetailPresenter;
import com.ssj.hulijie.pro.firstpage.view.widget.LinearLayoutManagerInScrollView;
import com.ssj.hulijie.pro.firstpage.view.widget.ListViewInScrollView;
import com.ssj.hulijie.pro.firstpage.view.widget.MyScrollView;
import com.ssj.hulijie.pro.firstpage.view.widget.RecylerViewInScrollView;
import com.ssj.hulijie.pro.firstpage.view.widget.ScrollViewListener;
import com.ssj.hulijie.pro.firstpage.view.widget.SelectPopWindow;
import com.ssj.hulijie.pro.firstpage.view.widget.SharePopWindow;
import com.ssj.hulijie.pro.mine.view.LoginActivity;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.DensityUtil;
import com.ssj.hulijie.utils.DisplayUtils;
import com.ssj.hulijie.utils.PictureUtil;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.WxUtil;
import com.ssj.hulijie.utils.compresspic.CompressImageLister;
import com.ssj.hulijie.utils.compresspic.CompressImageTask;
import com.ssj.hulijie.wxapi.ConstantsWechat;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

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

import static android.R.attr.bitmap;
import static com.ssj.hulijie.pro.mine.view.MineFragment.REQUESTPERSIMMIONCODE;
import static com.ssj.hulijie.utils.WxUtil.buildTransaction;


/**
 * Created by Administrator on 2017/6/13.
 */

public class DetailInfoActivity extends BaseActivity implements View.OnClickListener {
    private ItemCategoryMain.DataBean.RowsBean item;
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

    private DetailServiceItem detail;

    private ShareStatues current_share_status = ShareStatues.Wechat;
    private IWXAPI api;

    enum ShareStatues {
        Wechat, Pengyouquan;
    }


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
        api = WXAPIFactory.createWXAPI(this, ConstantsWechat.APPID, false);
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
            public void onResult(DetailServiceAndEvaluateItem result) {
                if (result != null) {
                    AppLog.Log("detail: " + result.toString());
                    updateUI(result);
                }
            }
        });
    }

    private void updateUI(DetailServiceAndEvaluateItem result) {
        if (result != null) {
            detail = result.getDetail();
            DetailServiceEvaluate evaluate = result.getEvaluate();
            //show evaluate
            if (evaluate != null && evaluate.getRows() != null && evaluate.getRows().size() > 0) {
                evaluate_base.setVisibility(View.VISIBLE);
                //show evaluate count
                detail_evaluate_count.setText("(" + evaluate.getCount() + ")");
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
            AppLog.Log("img: " + img);
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
        findViewById(R.id.ll_call_seller).setOnClickListener(this);

        detail_descript_img_rv = (RecylerViewInScrollView) findViewById(R.id.detail_descript_img_rv);
        detail_descript_img_rv.setLayoutManager(new LinearLayoutManagerInScrollView(this));
        DisplayUtils.initScreen(this);
        int screenWidth = DisplayUtils.screenWidth - DensityUtil.dip2px(this, 20);
        AppLog.Log("screenWidth:" + screenWidth);
        adapter = new DetailImageAdapter(this, screenWidth);
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


                findViewById(R.id.ll).setVisibility(View.VISIBLE);
                SharePopWindow menuWindow = new SharePopWindow(this, new SharePopWindow.SelectCallBack() {
                    @Override
                    public void selectWechatCallBack() {
                        current_share_status = ShareStatues.Wechat;
                        shareToWx();
                    }

                    @Override
                    public void selectPengyouquanCallBack() {
                        current_share_status = ShareStatues.Pengyouquan;
                        shareToWx();
                    }
                });
                //显示窗口
                menuWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                menuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.ll).setVisibility(View.GONE);
                    }
                });

                break;

            case R.id.order_btn:
                Intent i = null;
                if (!SharedUtil.getPreferBool(SharedKey.USER_LOGINED, false)) {
                    i = new Intent(this, LoginActivity.class);
                    startActivity(i);
                    return;
                }
                i = new Intent(this, OrderActivity.class);
                i.putExtra("detail", detail);
                startActivityForResult(i, TOYUYUE);
                break;
            case R.id.check_all_evaluate:
                startActivity(new Intent(this, CheckAllEvaluateActivity.class));
                break;
            case R.id.ll_call_seller:
                callPhone();
                break;
        }
    }




    /**
     * 分享到微信
     */
    private void shareToWx() {
        CompressImageTask task = new CompressImageTask(this, sv, new CompressImageLister() {
            @Override
            public void onCompressSuccess(Bitmap bitmap) {
                sendToWx(bitmap);
            }
        });
        task.execute();
    }

    private void sendToWx(Bitmap smallBitmap) {
        if (smallBitmap == null) {
            AppToast.ShowToast("分享失败");
            return;
        }
        AppLog.Log("1111111111");

        //初始化对象
        WXImageObject imgObj = new WXImageObject(smallBitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        //设置 缩略图
        AppLog.Log("缩略压缩后图片的大小_ 变化前： " + (smallBitmap.getByteCount() / 1024)
                + "k宽度为" + smallBitmap.getWidth() + "高度为" + smallBitmap.getHeight());
        Bitmap thumbBmp = Bitmap.createScaledBitmap(smallBitmap, 100, 200, true);
        AppLog.Log("缩略压缩后图片的大小_ 变化后： " + (thumbBmp.getByteCount() / 1024)
                + "k宽度为" + thumbBmp.getWidth() + "高度为" + thumbBmp.getHeight());
        msg.thumbData = WxUtil.bmpToByteArray(thumbBmp, true);
        AppLog.Log("222222222");
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        if (current_share_status == ShareStatues.Wechat) {
            req.scene = SendMessageToWX.Req.WXSceneSession;  //WXSceneSession： 会话，  SendMessageToWX.Req.WXSceneTimeline 这个是朋友圈
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;  //WXSceneSession： 会话，  SendMessageToWX.Req.WXSceneTimeline 这个是朋友圈
        }
        api.sendReq(req);

        if (smallBitmap != null && !smallBitmap.isRecycled()) {
            smallBitmap.recycle();
            smallBitmap = null;
        }
        if (thumbBmp != null && !thumbBmp.isRecycled()) {
            thumbBmp.recycle();
            thumbBmp = null;
        }

        AppLog.Log("333333");
    }

    private static final int TOYUYUE = 10001;

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "13806583199"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        } else {
            if (Build.VERSION.SDK_INT > 23) {
                AndPermission.with(this)
                        .requestCode(REQUESTPERSIMMIONCODE)
                        .permission(Manifest.permission.CALL_PHONE)
                        .send();
            }
        }

    }


    private final int SDK_PERMISSION_REQUEST = 127;
    private PermissionListener listener_permissions = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == REQUESTPERSIMMIONCODE) {
                callPhone();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(DetailInfoActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(DetailInfoActivity.this, SDK_PERMISSION_REQUEST).show();
            }
        }
    };

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener_permissions);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TOYUYUE && resultCode == RESULT_OK) {
            AppLog.Log("Detailact");
            finish();
        }
    }

}
