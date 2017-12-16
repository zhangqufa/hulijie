package com.ssj.hulijie.versioncontrol;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;


import com.ssj.hulijie.R;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class UpdateManager {
    private static final String TAG = "VERSION";
    private Context mContext;
    // 返回的安装包url
    private Dialog noticeDialog;
    /* 下载包安装路径 */
    private static final String savePath = Environment.getExternalStorageDirectory().getPath() + "/";
    private String saveFileName = savePath + "hulijie.apk";
    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;
    private Version newVersion;// 服务器版本信息
    private static final int DOWN_UPDATE = 1;// 更新消息
    private static final int DOWN_OVER = 2;// 结束消息
    private static final int IS_UPDATE = 3;// 更新文件下载完毕
    private int progress;// 进度提示
    private boolean interceptFlag = false;// 标志
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    Log.d(TAG, "progress: " + progress);
                    progressDialog.setProgress(progress);// 下载任务
                    break;
                case DOWN_OVER:
                    //此处应取消安装对话框
                    progressDialog.dismiss();
                    installApk();// 下载结束安装
                    break;
                case IS_UPDATE:
                    if (newVersion.getVerCode() > Version.getVerCode(mContext)) {
                        showNoticeDialog();
                    }
                    break;
                default:
                    break;
            }
        }

    };
    private CommonProgressDialog progressDialog;

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    // 外部接口让主Activity调用，新建线程检查服务 器版本更新
    public void checkUpdate() {
        checkVersionTask();
    }

    // 显示有更新提示对话框
    private void showNoticeDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setIcon(R.mipmap.logo)
                .setMessage("当前版本:" + Version.getVerCode(mContext) + "\n最新版本:" + newVersion.getVerCode() + "\n更新日志:" + newVersion.getContent())
                .setTitle("版本更新")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDownloadDialog();
                    }
                });
        if (newVersion.getStatus() != 1) {  //强制更新
            builder.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            builder.setCancelable(false);
        }
        builder.show();

    }

    // 显示下载进度对话框
    private void showDownloadDialog() {
        progressDialog = new CommonProgressDialog(mContext, new CommonProgressDialog.GoOther() {
            @Override
            public void cancel() {
                interceptFlag = true;
            }
        });
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("软件版本更新");
        progressDialog.show();
        progressDialog.hideProgressInfo();
        progressDialog.setCancelable(false);
        downloadApk();
    }

    private void getJsonByInternet() {
        CheckVersionTask task = new CheckVersionTask();
        task.execute();
    }

    private String getServerVer() {
        try {
            String json = "";
            URL url = new URL(AppURL.URL_VERSION);
//            URL url =new URL("http://www.51pangpang.top/api/json.php");
            HttpURLConnection httpConnection = (HttpURLConnection) url
                    .openConnection();
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
//            httpConnection.setRequestProperty("classid","111111129");
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpConnection.getOutputStream());
            // 发送请求参数
            // flush输出流的缓冲
            printWriter.flush();
            InputStreamReader reader = new InputStreamReader(
                    httpConnection.getInputStream());
            BufferedReader bReader = new BufferedReader(reader);
            json = bReader.readLine();
            AppLog.Log("version: "+json.toString());
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private class CheckVersionTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return getServerVer();
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            if (!TextUtils.isEmpty(json)) {
                Log.d(TAG, "version: " + json);
                Version version = new Version();
                //{"status":1, "verName":"消防管理1.0.0.0","verCode":"1","content":"测试版本，全功能上线","url":"555555555"}
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json);
                    jsonObject = (JSONObject) jsonObject.get("data");
                    int status = jsonObject.getInt("status");
                    version.setStatus(status);
                    String verName = jsonObject.getString("verName");
                    version.setVerName(verName);
                    int verCode = jsonObject.getInt("verCode");
                    version.setVerCode(verCode);
                    String content = jsonObject.getString("content");
                    version.setContent(content);
                    String url = jsonObject.getString("url");
                    version.setUrl(url);
                    newVersion = version;
                    AppLog.Log( "获取到的当前版本号" + String.valueOf(Version.getVerCode(mContext)) + "服务器版本号：" + newVersion.getVerCode());
                    mHandler.sendEmptyMessage(IS_UPDATE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    public void checkVersionTask() {
        getJsonByInternet();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(newVersion.getUrl());//// TODO: 2017/5/13
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消就停止下载.
                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 下载apk
     *
     */
    private void downloadApk() {
        Thread downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(mContext, "com.ssj.hulijie.fileprovider", apkfile);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {

            i.setDataAndType(Uri.fromFile(new File(saveFileName)), "application/vnd.android.package-archive");
        }

        mContext.startActivity(i);
    }
}
