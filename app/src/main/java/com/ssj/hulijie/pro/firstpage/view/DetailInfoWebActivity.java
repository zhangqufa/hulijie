package com.ssj.hulijie.pro.firstpage.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.firstpage.bean.DetailServiceItem;
import com.ssj.hulijie.pro.firstpage.bean.ItemCategoryMain;
import com.ssj.hulijie.pro.firstpage.presenter.DetailPresenter;
import com.ssj.hulijie.pro.firstpage.view.widget.SharePopWindow;
import com.ssj.hulijie.pro.mine.view.LoginActivity;
import com.ssj.hulijie.pro.mine.view.MineOrderListActivity;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.AppURL;
import com.ssj.hulijie.utils.Constant;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.WxUtil;
import com.ssj.hulijie.wxapi.ConstantsWechat;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.ssj.hulijie.pro.mine.view.MineFragment.REQUESTPERSIMMIONCODE;
import static com.ssj.hulijie.utils.WxUtil.buildTransaction;


/**
 * Created by Administrator on 2017/6/13.
 */

public class DetailInfoWebActivity extends BaseActivity implements View.OnClickListener {
    private ItemCategoryMain.DataBean.RowsBean item;
    private TextView nav_center_title;
    private RelativeLayout toolbar_base;
    private ImageView iv_navigation_back;
    private ImageView iv_navigation_right;
    private DetailPresenter mPresenter;
    private DetailServiceItem detail;
    private ShareStatues current_share_status = ShareStatues.Wechat;
    private IWXAPI api;

    enum ShareStatues {
        Wechat, Pengyouquan;
    }


    /**
     * 去除small 之后变成清晰度高一点的图片
     */
    private static final String SMALL_ = "small_";

    @Override
    public MvpBasePresenter bindPresenter() {
        mPresenter = new DetailPresenter(this);
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_service_web_detail);

        item = getIntent().getParcelableExtra("item");
        api = WXAPIFactory.createWXAPI(this, ConstantsWechat.APPID, false);
        AppLog.Log("Item: " + item);
        initView();
    }


    private void initView() {
        toolbar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        nav_center_title = (TextView) findViewById(R.id.tv_navigation_center);
        iv_navigation_back = (ImageView) findViewById(R.id.iv_navigation_back);
        iv_navigation_right = (ImageView) findViewById(R.id.iv_navigation_right);
        iv_navigation_back.setOnClickListener(this);
        iv_navigation_right.setOnClickListener(this);


        WebView wv = (WebView) findViewById(R.id.wv);

        wv.getSettings().setJavaScriptEnabled(true);
        String url = AppURL.URL_SERVICE_DETAIL_WEB + item.getGoods_id();
        AppLog.Log("wv_url:" + url);
        wv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                AppLog.Log("com_html: " + url);
                if (url.startsWith("mailto:")) {
                    Intent intent = null;
                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    view.getContext().startActivity(intent);
                } else if (url.endsWith(".mp3")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "audio/*");
                    startActivity(intent);

                } else if (url.endsWith(".mp4") || url.endsWith(".3gp")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "video/*");
                    startActivity(intent);
                } else {
                    return false;
                }
                view.reload();
                return true;
            }
        });

        wv.loadUrl(url);
    }


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
            default:
                break;
        }
    }


    /**
     * 分享到微信
     */
    private void shareToWx() {
//todo 图片分享暂时不用
//        CompressImageTask task = new CompressImageTask(this, rv, new CompressImageLister() {
//            @Override
//            public void onCompressSuccess(Bitmap bitmap) {
//                sendToWx(bitmap);
//            }
//        });
//        task.execute();
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
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        smallBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        float total_count = bos.toByteArray().length / 1024f;
        //设置 缩略图
        AppLog.Log("缩略压缩后图片的大小_ 变化前： " + total_count
                + "k宽度为" + smallBitmap.getWidth() + "高度为" + smallBitmap.getHeight());
//        Bitmap thumbBmp = Bitmap.createScaledBitmap(smallBitmap, 100, 200, true);
        Bitmap thumbBmp = createBitmapThumbnail(smallBitmap);
        bos.reset();
        thumbBmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        total_count = bos.toByteArray().length / 1024f;
        bos.reset();
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AppLog.Log("缩略压缩后图片的大小_ 变化后： " + total_count
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

    public Bitmap createBitmapThumbnail(Bitmap bitMap) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = 100;
        int newHeight = 120;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,
                matrix, true);
        return newBitMap;
    }

    private static final int TOYUYUE = 10001;

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Constant.tle_service));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        } else {
            if (Build.VERSION.SDK_INT >= 23) {
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
            if (AndPermission.hasAlwaysDeniedPermission(DetailInfoWebActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(DetailInfoWebActivity.this, SDK_PERMISSION_REQUEST).show();
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
            Intent intent = new Intent(this, MineOrderListActivity.class);
            startActivity(intent);
            finish();

        }
    }

}
