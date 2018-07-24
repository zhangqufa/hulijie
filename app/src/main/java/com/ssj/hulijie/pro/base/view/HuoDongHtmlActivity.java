package com.ssj.hulijie.pro.base.view;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import com.ssj.hulijie.pro.firstpage.view.widget.SharePopWindow;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.WxUtil;
import com.ssj.hulijie.wxapi.ConstantsWechat;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.net.URISyntaxException;

import static com.ssj.hulijie.utils.WxUtil.buildTransaction;

/**
 * @author qufa
 */
public class HuoDongHtmlActivity extends BaseActivity implements View.OnClickListener {
    private IWXAPI api;
    private String title;
    private String url;

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h5_comm);
        api = WXAPIFactory.createWXAPI(this, ConstantsWechat.APPID, false);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        initView();


    }

    private void initView() {

        initToolBar();
        WebView wv = (WebView) findViewById(R.id.wv);

        wv.getSettings().setJavaScriptEnabled(true);
        AppLog.Log("wv_url:" + url);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
//                view.setVisibility(View.VISIBLE);
//                final Animation fade = new AlphaAnimation(0.0f, 1.0f);
//                fade.setDuration(200);
//                view.startAnimation(fade);
//                view.setVisibility(View.VISIBLE);

            }

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

    enum ShareStatues {
        Wechat, Pengyouquan;
    }

    private ShareStatues current_share_status = ShareStatues.Wechat;
    private TextView nav_center_title;
    private RelativeLayout toolbar_base;
    private ImageView iv_navigation_back;
    private ImageView iv_navigation_right;

    private void initToolBar() {
        toolbar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        nav_center_title = (TextView) findViewById(R.id.tv_navigation_center);
        iv_navigation_back = (ImageView) findViewById(R.id.iv_navigation_back);
        iv_navigation_right = (ImageView) findViewById(R.id.iv_navigation_right);
        iv_navigation_back.setOnClickListener(this);
        iv_navigation_right.setOnClickListener(this);
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
            default:
                break;
        }
    }

    /**
     * 分享到微信
     */
    private void shareToWx() {


        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "";
        msg.description = title;
        msg.thumbData = WxUtil.bmpToByteArray(BitmapFactory.decodeResource(getResources(), R.mipmap.logo128), true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        if (current_share_status == ShareStatues.Wechat) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        api.sendReq(req);
    }
}
