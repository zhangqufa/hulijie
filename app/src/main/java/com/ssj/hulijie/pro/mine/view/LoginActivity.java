package com.ssj.hulijie.pro.mine.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.bean.LoginItem;
import com.ssj.hulijie.pro.mine.bean.VerifyCode;
import com.ssj.hulijie.pro.mine.presenter.LoginPresenter;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.DateUtil;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;

import java.util.Timer;

import static com.baidu.location.b.k.ca;


/**
 * Created by Administrator on 2017/6/26.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_getmsgcode; //获取验证码按钮
    private Button btn_login;
    private EditText edt_phone;
    private EditText edt_msgcode; //输入验证码框

    @Override
    public MvpBasePresenter bindPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        initView();
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        RelativeLayout toolbar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        TitlebarUtil.inittoolBar(this, toolbar_base, true, "登录", android.R.color.white, 0, R.drawable.select_close, false, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_getmsgcode = (Button) findViewById(R.id.btn_getmsgcode);
        btn_getmsgcode.setOnClickListener(this);

        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_msgcode = (EditText) findViewById(R.id.edt_msgcode);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getmsgcode:

                //发送请求验证码
                getVerifyCode();


                break;

            case R.id.btn_login:

                login();
                break;
        }
    }

    private void chageBtnStatus() {
        countDownTimer.start();

    }



    private CountDownTimer countDownTimer = new CountDownTimer(60*1000,1000) {
        @Override
        public void onTick(long l) {
            btn_getmsgcode.setClickable(false);
            btn_getmsgcode.setTextColor(getColor(R.color.comm_grey_666666));
            btn_getmsgcode.setText(DateUtil.getIntervalUpdateTime(l));
        }

        @Override
        public void onFinish() {
            btn_getmsgcode.setClickable(true);
            btn_getmsgcode.setTextColor(getColor(R.color.colorPrimary));
            btn_getmsgcode.setText("获取验证码");
        }
    };

    private void login() {
        String moible = edt_phone.getText().toString();
        String code = edt_msgcode.getText().toString();
        if (TextUtils.isEmpty(moible)) {
            AppToast.ShowToast("手机号码不可为空！");
            edt_phone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(code)) {
            AppToast.ShowToast("验证码不可为空！");
            edt_msgcode.requestFocus();
            return;
        }
        ((LoginPresenter) presenter).loginPresenter(this, moible, code, new BasePresenter.OnUIThreadListener<LoginItem>() {
            @Override
            public void onResult(LoginItem result,int return_code) {

                if (result != null) {
                    //保存数据到本地
                    SharedUtil.setPreferStr(SharedKey.USER_MOBILE, result.getMobile());
                    SharedUtil.setPreferStr(SharedKey.USER_NAME, result.getUser_name());
                    SharedUtil.setPreferStr(SharedKey.USER_ID, result.getUser_id());
                    SharedUtil.setPreferBool(SharedKey.USER_LOGINED, true);
                    SharedUtil.setPreferStr(SharedKey.USER_KEY, result.getKey());
                    finish();

                }
            }
        });

    }

    private void getVerifyCode() {
        String mobile = edt_phone.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            AppToast.ShowToast("手机号码不可为空！");
            edt_phone.requestFocus();
            return;
        }

        //改变按钮状态 和 倒计时
        chageBtnStatus();

        ((LoginPresenter) presenter).getVerifyCodePresenter(this, mobile, new BasePresenter.OnUIThreadListener<VerifyCode>() {
            @Override
            public void onResult(VerifyCode result,int return_code) {
                if (result != null) {
                    String verifyCode = result.getCode();
                    edt_msgcode.setText(verifyCode);


                }
            }
        });
    }

}
