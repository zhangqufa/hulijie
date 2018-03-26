package com.ssj.hulijie.pro.mine.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.ssj.hulijie.R;
import com.ssj.hulijie.mvp.presenter.impl.MvpBasePresenter;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.bean.LoginItem;
import com.ssj.hulijie.pro.mine.bean.VerifyCode;
import com.ssj.hulijie.pro.mine.presenter.LoginPresenter;
import com.ssj.hulijie.utils.AccountValidatorUtil;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.AppURL;
import com.ssj.hulijie.utils.Constant;
import com.ssj.hulijie.utils.DateUtil;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.TitlebarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/6/26.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_getmsgcode; //获取验证码按钮
    private Button btn_login;
    private EditText edt_phone;
    private EditText edt_msgcode; //输入验证码框


    /**
     * 极验  SDK
     */
    private GT3GeetestUtilsBind gt3GeetestUtils;

    @Override
    public MvpBasePresenter bindPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        /**
         * 初始化
         * 务必放在onCreate方法里面执行
         */
        gt3GeetestUtils = new GT3GeetestUtilsBind(this);
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
                String mobile = edt_phone.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    AppToast.ShowToast("手机号不可为空");
                    edt_phone.requestFocus();
                    return;
                }
                /**
                 * 极验滑动验证
                 */
                verifyJiYan(mobile);


                break;

            case R.id.btn_login:

                login();
                break;
            default:
                break;
        }
    }

    private void verifyJiYan(final String mobile) {

        /**
         * AppURL.URL_GET_VERIFY_STR :
         * {"success":1,"gt":"1df59271fb9837effb4ebdd3df1b4416","challenge":"3033b8b1e6ad4dc0b581a48a9d15f02d","new_captcha":1}
         *
         *  AppURL.URL_GET_VERIFY_CODE：
         *  提交手机号
         */
        gt3GeetestUtils.getGeetest(this, AppURL.URL_GET_VERIFY_STR, AppURL.URL_GET_VERIFY_CODE, null, new GT3GeetestBindListener() {

            /**
             * 往二次验证里面put数据
             * put类型是map类型
             * 注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
             * 该方法只适用于不使用自定义api2时使用
             */
            @Override
            public Map<String, String> gt3SecondResult() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("mobile", mobile);
                return map;

            }

            /**
             * 二次验证完成的回调
             * 该方法只适用于不使用自定义api2时使用
             * result为俄二次验证后的数据
             * 根据二次验证返回的数据判断此次验证是否成功
             * 二次验证成功调用 gt3GeetestUtils.gt3TestFinish();
             * 二次验证失败调用 gt3GeetestUtils.gt3TestClose();
             */
            @Override
            public void gt3DialogSuccessResult(String result) {
                AppLog.Log("jiyan result:___" + result);
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jobj = new JSONObject(result);
                        int code = jobj.getInt("code");
                        if (Constant.SUCCESS_CODE == code) {
                            chageBtnStatus();

                            String data = jobj.getString("data");
                            JSONObject jsonObject = new JSONObject(data);
                            String verifyCode = jsonObject.getString("code");
                            edt_msgcode.setText(verifyCode);
                            gt3GeetestUtils.gt3TestFinish();
                        } else {
                            AppToast.ShowToast(jobj.getString("msg"));
                            gt3GeetestUtils.gt3TestClose();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        gt3GeetestUtils.gt3TestClose();
                    }
                } else {
                    gt3GeetestUtils.gt3TestClose();
                }
            }

            /**
             * 验证过程错误
             * 返回的错误码为判断错误类型的依据
             */

            @Override
            public void gt3DialogOnError(String error) {
                AppLog.Log("gt3DialogOnError");

            }
        });
        //设置是否可以点击屏幕边缘关闭验证码
        gt3GeetestUtils.setDialogTouch(false);
    }

    private void chageBtnStatus() {
        countDownTimer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 页面关闭时释放资源
         */
        gt3GeetestUtils.cancelUtils();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        /**
         * 设置后，界面横竖屏不会关闭验证码，推荐设置
         */
        gt3GeetestUtils.changeDialogLayout();
    }

    private CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            btn_getmsgcode.setClickable(false);
            btn_getmsgcode.setTextColor(getResources().getColor(R.color.comm_grey_666666));
            btn_getmsgcode.setText(DateUtil.getIntervalUpdateTime(l));
        }

        @Override
        public void onFinish() {
            btn_getmsgcode.setClickable(true);
            btn_getmsgcode.setTextColor(getResources().getColor(R.color.colorPrimary));
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
            public void onResult(LoginItem result) {
                if (result != null && TextUtils.isEmpty(result.getMsg())) {
                    //保存数据到本地
                    SharedUtil.setPreferStr(SharedKey.USER_MOBILE, result.getMobile());
                    SharedUtil.setPreferStr(SharedKey.USER_NAME, result.getUser_name());
                    SharedUtil.setPreferStr(SharedKey.USER_ID, result.getUser_id());
                    SharedUtil.setPreferBool(SharedKey.USER_LOGINED, true);
                    SharedUtil.setPreferStr(SharedKey.USER_KEY, result.getKey());
                    finish();

                } else if (result != null && !TextUtils.isEmpty(result.getMsg())) {
                    AppToast.ShowToast(result.getMsg());
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
        if (!AccountValidatorUtil.isMobile(mobile)) {
            AppToast.ShowToast("手机号码格式不正确！");
            edt_phone.requestFocus();
            return;
        }

        //改变按钮状态 和 倒计时
        chageBtnStatus();

        ((LoginPresenter) presenter).getVerifyCodePresenter(this, mobile, new BasePresenter.OnUIThreadListener<VerifyCode>() {
            @Override
            public void onResult(VerifyCode result) {
                /**
                 * only for test
                 */
                if (result != null) {
                    String verifyCode = result.getCode();
                    edt_msgcode.setText(verifyCode);


                }
            }
        });
    }

}
