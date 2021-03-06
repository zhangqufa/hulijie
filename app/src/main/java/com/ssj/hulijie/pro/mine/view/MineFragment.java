package com.ssj.hulijie.pro.mine.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.base.view.BaseFragment;
import com.ssj.hulijie.pro.firstpage.view.SelectAddressActivity;
import com.ssj.hulijie.pro.home.view.MainActivity;
import com.ssj.hulijie.pro.mine.bean.ItemImageUpload;
import com.ssj.hulijie.pro.mine.presenter.ImageUpladPresenter;
import com.ssj.hulijie.pro.mine.view.seller.ServiceActivity;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppURL;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.utils.StringFormat;
import com.ssj.hulijie.widget.dialog.ConfirmCancelDialog;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2017/3/26.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {


    private ImageView img;
    public static final int REQUESTPERSIMMIONCODE = 100;
    private static final int REQUEST_LOGIN_CODE = 101;
    private static final int REQUEST_TO_ORDER_LIST = 102;
    private TextView user, user_des;

    public static final String MINE_TO_SELECTADRESS = "mine_to_select_address";
    private RelativeLayout mine_logout;
    private Button btn_go_server;

    @Override
    public int getContentView() {
        return R.layout.frag_mine;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isLogined = SharedUtil.getPreferBool(SharedKey.USER_LOGINED, false);
        if (!isLogined) {
            user.setText("立即登录");
//            btn_go_server.setVisibility(View.GONE);
            user_des.setVisibility(View.GONE);
            user_des.setText("");
            mine_logout.setVisibility(View.GONE);
        } else {
//            btn_go_server.setVisibility(View.VISIBLE);
            String user_name = SharedUtil.getPreferStr(SharedKey.USER_NAME);
            String mobile = SharedUtil.getPreferStr(SharedKey.USER_MOBILE);
            user.setText(TextUtils.isEmpty(user_name) ? "小狐狸" : user_name);
            user_des.setVisibility(View.VISIBLE);
            user_des.setText(StringFormat.toMobile(mobile));
            mine_logout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initContentView(View viewContent) {


        img = (ImageView) viewContent.findViewById(R.id.img);
        Glide.with(getActivity()).load(R.mipmap.loading_logo)
                .into(img);

        viewContent.findViewById(R.id.login).setOnClickListener(this);
        btn_go_server = (Button) viewContent.findViewById(R.id.btn_go_server);

        viewContent.findViewById(R.id.service).setOnClickListener(this);
        viewContent.findViewById(R.id.mine_address).setOnClickListener(this);
        viewContent.findViewById(R.id.mine_contact).setOnClickListener(this);
        viewContent.findViewById(R.id.mine_company_add).setOnClickListener(this);
        viewContent.findViewById(R.id.mine_order_list).setOnClickListener(this);
        viewContent.findViewById(R.id.mine_no_complete).setOnClickListener(this);
        viewContent.findViewById(R.id.mine_complete).setOnClickListener(this);
        viewContent.findViewById(R.id.mine_wait_evaluate).setOnClickListener(this);
        viewContent.findViewById(R.id.mine_all_order).setOnClickListener(this);
        viewContent.findViewById(R.id.btn_go_server).setOnClickListener(this);
        mine_logout = (RelativeLayout) viewContent.findViewById(R.id.mine_logout);


        mine_logout.setOnClickListener(this);

        //show user_name
        user = (TextView) viewContent.findViewById(R.id.user);
        //show person info
        user_des = (TextView) viewContent.findViewById(R.id.user_des);

        String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/share.jpg";
        String s1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaa.jpg";
        File file = new File(s);
        if (file.exists()) {
            AppLog.Log("s文件存在");
        }
        File file1 = new File(s1);
        if (file1.exists()) {
            AppLog.Log("s1文件存在");
        }
        final String[] paths = {s,s1};
//        btn_go_server.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                UpladTask task = new UpladTask();
//                task.execute(paths);
//                return true;
//            }
//        });
    }

    class UpladTask extends AsyncTask<String[], Void, Void> {
        @Override
        protected Void doInBackground(String[]... strings) {
            String[] string = strings[0];
            useHttpUrl(string);
            return null;
        }
    }


    private String useHttpUrl(String path[]) {
        AppLog.Log("开始上传");
        try {
            URL url = new URL(AppURL.URL_PIC_UPLOAD);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //30秒连接超时
            connection.setConnectTimeout(30 * 1000);
            //30秒读取超时
            connection.setReadTimeout(30 * 1000);
            //允许文件输入流
            connection.setDoInput(true);
            //允许文件输出流
            connection.setDoOutput(true);
            //不允许使用缓存
            connection.setUseCaches(false);
            //请求方式为POST
            connection.setRequestMethod("POST");
            //设置编码为utf-8
            connection.setRequestProperty("Charset", "utf-8");
            //保持连接
            connection.setRequestProperty("connection", "keep-alive");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY); //特别注意：Content-Type必须为multipart/form-data
            OutputStream outputSteam = connection.getOutputStream();
            DataOutputStream dos = new DataOutputStream(outputSteam);
            //如果传入的文件路径不为空的话，则读取文件并上传
            for (int i = 0; i < path.length; i++) {
                File file = new File(path[i]);
                if (file != null) {
                    //读取图片进行压缩
                    //如果不需要压缩的话直接读取文件则可 InputStream is = new FileInputStream(file);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //0-100 100为不压缩
                    InputStream is = new ByteArrayInputStream(baos.toByteArray());


                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = is.read(bytes)) != -1) {
                        dos.write(bytes, 0, len);
                    }
                    is.close();
                    if (i < path.length-1) {
                        String diver = "[x]";
                        dos.write(diver.getBytes());
                    }
                }

            }
            dos.flush();
            //获取返回码，根据返回码做相应处理
            int res = connection.getResponseCode();
            AppLog.Log("response code:" + res);
            if (res == 200) {
                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = input.readLine()) != null) {
                    result.append(line).append("\n");
                }
                String s = new String(result.toString().getBytes(), "utf-8");
                AppLog.Log("图片上传result: " + s);
                return s;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private void getPersimmions() {
        if (Build.VERSION.SDK_INT > 23) {
            AndPermission.with(this)
                    .requestCode(REQUESTPERSIMMIONCODE)
                    .permission(Manifest.permission.CALL_PHONE)
                    .send();
        } else {

            callPhone();
        }
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "13806583199"));
        startActivity(intent);
    }

    private final int SDK_PERMISSION_REQUEST = 127;


    private PermissionListener listener = new PermissionListener() {
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
            if (AndPermission.hasAlwaysDeniedPermission(getActivity(), deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(getActivity(), SDK_PERMISSION_REQUEST).show();

                // 第二种：用自定义的提示语。
                // AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                // .setTitle("权限申请失败")
                // .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                // .setPositiveButton("好，去设置")
                // .show();

                // 第三种：自定义dialog样式。
                // SettingService settingService =
                //    AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
                // 你的dialog点击了确定调用：
                // settingService.execute();
                // 你的dialog点击了取消调用：
                // settingService.cancel();
            }
        }
    };

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        boolean isForResult = false;
        boolean logined = false;

        switch (view.getId()) {
            case R.id.login:
                if (!SharedUtil.getPreferBool(SharedKey.USER_LOGINED, false)) {
                    intent = new Intent(getContext(), LoginActivity.class);
                } else {
//                    intent = new Intent(getContext(), SettingActivity.class);
                }
                break;
            case R.id.mine_address:
                logined = checkIsLogin();
                if (!logined) {
                    return;
                }
                intent = new Intent(getContext(), SelectAddressActivity.class);
                intent.putExtra(MINE_TO_SELECTADRESS, MINE_TO_SELECTADRESS);  //由我的 页进入地址管理 不注册 itemclick
                break;
            case R.id.mine_contact:
                //用intent启动拨打电话
                ConfirmCancelDialog dialog = new ConfirmCancelDialog(getActivity(), new ConfirmCancelDialog.GoOther() {
                    @Override
                    public void go() {
                        // after andrioid m,must request Permiision on runtime
                        getPersimmions();
                    }

                    @Override
                    public void cancel() {

                    }
                }, "是否拨打客服电话？");
                dialog.show();

                break;
            case R.id.mine_company_add:
                intent = new Intent(getContext(), ShopVerifyActivity.class);
                break;
            case R.id.mine_order_list:
                logined = checkIsLogin();
                if (!logined) {
                    return;
                }
                isForResult = true;
                intent = new Intent(getContext(), MineOrderListActivity.class);

                break;
            case R.id.mine_no_complete:
                logined = checkIsLogin();
                if (!logined) {
                    return;
                }
                isForResult = true;
                intent = new Intent(getContext(), MineOrderListActivity.class);
                intent.putExtra(MineOrderListActivity.DEFAULT_PAGE, 0);
                break;
            case R.id.mine_complete:
                logined = checkIsLogin();
                if (!logined) {
                    return;
                }
                isForResult = true;
                intent = new Intent(getContext(), MineOrderListActivity.class);
                intent.putExtra(MineOrderListActivity.DEFAULT_PAGE, 1);
                break;
            case R.id.mine_wait_evaluate:
                logined = checkIsLogin();
                if (!logined) {
                    return;
                }
                isForResult = true;
                intent = new Intent(getContext(), MineOrderListActivity.class);
                intent.putExtra(MineOrderListActivity.DEFAULT_PAGE, 2);
                break;
            case R.id.mine_all_order:
                logined = checkIsLogin();
                if (!logined) {
                    return;
                }
                isForResult = true;
                intent = new Intent(getContext(), MineOrderListActivity.class);
                intent.putExtra(MineOrderListActivity.DEFAULT_PAGE, 3);
                break;
            case R.id.btn_go_server:
            case R.id.service:
                intent = new Intent(getContext(), ServiceActivity.class);
                break;
            case R.id.mine_logout:
                intent = new Intent(getContext(), SettingActivity.class);
                break;

            default:
                break;
        }

        if (intent == null) {
            return;
        }
        if (isForResult) {
            startActivityForResult(intent, REQUEST_TO_ORDER_LIST);
        } else {
            startActivity(intent);
        }


    }

    private boolean checkIsLogin() {
        boolean logined = SharedUtil.getPreferBool(SharedKey.USER_LOGINED, false);
        if (!logined) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        return logined;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TO_ORDER_LIST && resultCode == Activity.RESULT_OK) {
            ((MainActivity) getActivity()).changeTab(0);
        }
    }
}
