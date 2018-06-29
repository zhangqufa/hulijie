package com.ssj.hulijie.pro.base.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.net.URISyntaxException;

/**
 * @author qufa
 */
public class HtmlActivity extends BaseActivity {
    private String title;

    @Override
    public MvpBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h5_comm);
        initView();


    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
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


    private void initToolBar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, title_bar_base, true, title, android.R.color.white, 0, R.mipmap.back_red_circle, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);
    }
}
