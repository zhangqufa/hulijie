package com.ssj.hulijie.utils.compresspic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ScrollView;

import com.ssj.hulijie.pro.firstpage.view.widget.MyScrollView;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.Constant;
import com.ssj.hulijie.widget.dialog.WaitDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by vic on 2016/8/16.
 */
public class CompressImageTask extends AsyncTask<Void, Void, Boolean> {
    private static String path_sd = Constant.APP_LOCAL_FILE_URL;//sd路径
    private  Bitmap bitmap_temp;
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
    protected Boolean doInBackground(Void... params) {

        final String s = savePic(bitmap_temp);
        Boolean aBoolean = compressImage(s);
        return aBoolean;
    }

    /**
     * 保存到sdcard
     *
     * @param b
     * @return
     */
    public static String savePic(Bitmap b) {
        String  file_directory= Environment.getExternalStorageDirectory().getAbsoluteFile() + "/hulijie/image" ;

        File dirFile = new File(file_directory);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        String file_path = file_directory + File.separator + "short_pic.png";

        File outfile = new File(file_path);
        // 如果文件不存在、则创建一个新文件

        FileOutputStream fos = null;
        try {
            if (!outfile.isDirectory()) {
                outfile.createNewFile();
            }
            fos = new FileOutputStream(outfile);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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
    public static Bitmap getBitmapByView(ScrollView scrollView) {
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
    protected void onPostExecute(Boolean aBoolean) {
        dlg.dismiss();
        if (aBoolean) {
            File file = new File(fileName);
            lister.onCompressSuccess(file);
        }
    }


    /**
     * 压缩图片  先判断图片width & height是否>1500  ,如果大于1500，先进行比例压缩，之后再进行大小判断，如果大于3M，进行质量压缩
     *
     * @param path
     * @return
     */
    private Boolean compressImage(String path) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = true;
            options.inSampleSize = 1;
            Bitmap bitmap = BitmapFactory.decodeFile(path,options);
            AppLog.Log("压缩后图片的大小_ 变化前： " + (bitmap.getByteCount() / 1024 / 1024)
                    + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
            while (bitmap.getWidth() > 3500 || bitmap.getHeight() > 3500) {
                options.inSampleSize *= 2;
                options.inJustDecodeBounds = false; //真正的去解析位图

                bitmap = BitmapFactory.decodeFile(path, options);
                AppLog.Log("压缩后图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                        + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
            }
            FileOutputStream b = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            File file = new File(path_sd);
            if (!file.exists()) {
                file.mkdirs();// 创建文件夹
            }
            fileName = path_sd + "/hulijie.png";//图片名字
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);   //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int intoptions = 100;
            while (bos.toByteArray().length / 1024 > 30* 100) {            //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                bos.reset();//重置baos即清空baos
                intoptions -= 10;   //每次都减少10
                bitmap.compress(Bitmap.CompressFormat.PNG, intoptions, bos);//这里压缩options%，把压缩后的数据存放到baos中
                AppLog.Log("图片size:" + bos.toByteArray().length / 1024 + ",  width:" + bitmap.getWidth() + ", height:" + bitmap.getHeight());
            }
            byte[] bytes = bos.toByteArray();
            b.write(bytes);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bos.close();
            b.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
