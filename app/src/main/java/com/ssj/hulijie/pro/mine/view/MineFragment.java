package com.ssj.hulijie.pro.mine.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.view.BaseFragment;
import com.ssj.hulijie.pro.firstpage.view.AddressActivity;
import com.ssj.hulijie.pro.firstpage.view.FirstPageFrament;
import com.ssj.hulijie.pro.firstpage.view.SelectAddressActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import static com.baidu.location.b.g.A;
import static com.ssj.hulijie.base.HljAppliation.context;

/**
 * Created by Administrator on 2017/3/26.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {


    private ImageView img;
    private static final int REQUESTPERSIMMIONCODE= 100;

    @Override
    public int getContentView() {
        return R.layout.frag_mine;
    }

    @Override
    public void initContentView(View viewContent) {
        img = (ImageView) viewContent.findViewById(R.id.img);
        Glide.with(getActivity()).load(FirstPageFrament.img[0])
                .into(img);

        viewContent.findViewById(R.id.login).setOnClickListener(this);

        //// TODO: 2017/7/6 去掉代金券、邀请有礼、微信绑定、添加上“企业加盟”
        viewContent.findViewById(R.id.mine_address).setOnClickListener(this);
        viewContent.findViewById(R.id.mine_contact).setOnClickListener(this);
        viewContent.findViewById(R.id.mine_company_add).setOnClickListener(this);


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

        switch (view.getId()) {
            case R.id.login:
                intent = new Intent(getContext(), LoginActivity.class);

                break;
            case R.id.mine_address:
                intent = new Intent(getContext(), SelectAddressActivity.class);
                break;
            case R.id.mine_contact:
                //用intent启动拨打电话

                // after andrioid m,must request Permiision on runtime
                getPersimmions();

                break;
            case R.id.mine_company_add:
                intent = new Intent(getContext(), CompanyShopInActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);

        }
    }

}
