package com.ssj.hulijie.utils.compresspic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.widget.ScrollView;

import com.ssj.hulijie.pro.firstpage.view.widget.MyScrollView;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.Constant;
import com.ssj.hulijie.widget.dialog.WaitDialog;
import com.ssj.hulijie.wxapi.UtilShare;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.bitmap;
import static android.R.attr.iconifiedByDefault;


/**
 * Created by vic on 2016/8/16.
 */
public class CompressImageTask extends AsyncTask<Void, Void, Bitmap> {
    private static String path_sd = Constant.APP_LOCAL_FILE_URL;//sd路径
    private Bitmap bitmap_temp;
    private Context context;
    private WaitDialog dlg;
    private String fileName;
    private CompressImageLister lister;
    private ScrollView scrollView;

    public CompressImageTask(Context context, CompressImageLister lister) {
        this.context = context;
        this.lister = lister;
        dlg = new WaitDialog(context);
    }

    public CompressImageTask(Context context, ScrollView scrollView, CompressImageLister lister) {
        this.context = context;
        this.lister = lister;
        this.scrollView = scrollView;
        bitmap_temp = getBitmapByView(scrollView);
        dlg = new WaitDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dlg.show();
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

//        final String s = savePic(bitmap_temp);
        Bitmap bp = compressImage(bitmap_temp);
        return bp;
    }

    /**
     * 保存到sdcard
     *
     * @param b
     * @return
     */
    public static String savePic(Bitmap b) {
        String file_directory = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/hulijie/image";

        File dirFile = new File(file_directory);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        String file_path = file_directory + File.separator + "short_pic.jpg";

        File outfile = new File(file_path);
        // 如果文件不存在、则创建一个新文件

        FileOutputStream fos = null;
        try {
            if (!outfile.isDirectory()) {
                outfile.createNewFile();
            }
            fos = new FileOutputStream(outfile);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (b != null && !b.isRecycled()) {
                b.recycle();
            }

        }
        return file_path;
    }

    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */
    public Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h = scrollView.getChildAt(i).getHeight();
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


    @Override
    protected void onPostExecute(Bitmap bt) {
        dlg.dismiss();
        lister.onCompressSuccess(bt);
    }


    private static final float MAX_SIZE = 2.5f;
    /**
     * 压缩图片  先判断图片width & height是否>1500  ,如果大于1500，先进行比例压缩，之后再进行大小判断，如果大于3M，进行质量压缩
     *
     * @param bit
     * @return
     */
    private Bitmap compressImage(Bitmap bit) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            float total_count = bos.toByteArray().length / (1024f * 1024f);
            AppLog.Log("压缩后图片的大小_ 变化前： " + total_count + "M宽度为" + bit.getWidth() + "高度为" + bit.getHeight());
            Bitmap bitmap = null;
            if (total_count > MAX_SIZE) {
                float i = total_count / MAX_SIZE;
                // 取得想要缩放的matrix参数
                Matrix matrix = new Matrix();
                matrix.postScale(1 / i, 1 / i);
                // 得到新的图片   www.2cto.com
                bitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
                if (bit != null && !bit.isRecycled()) {
                    bit.recycle();
                    bit =null;
                }
                bos.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                AppLog.Log("图片size:" + bos.toByteArray().length / (1024f * 1024f) + ",  width:" + bitmap.getWidth() + ", height:" + bitmap.getHeight());
                bos.close();
                return bitmap;
            } else {
                return bit;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return bit;
        }
    }
}
