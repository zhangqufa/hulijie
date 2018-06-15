package com.ssj.hulijie.pro.mine.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.presenter.BasePresenter;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.adapter.Adapter;
import com.ssj.hulijie.pro.mine.adapter.EvaluateLevelAdapter;
import com.ssj.hulijie.pro.mine.bean.ItemEvaluateLevel;
import com.ssj.hulijie.pro.mine.presenter.OrderListPresenter;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.AppURL;
import com.ssj.hulijie.utils.DisplayUtils;
import com.ssj.hulijie.utils.SharedKey;
import com.ssj.hulijie.utils.SharedUtil;
import com.ssj.hulijie.widget.DividerGridItemDecoration;
import com.ssj.hulijie.widget.recylerview.GridItemDecoration;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.impl.OnItemClickListener;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public class EvaluateSubmitActivity extends BaseActivity<OrderListPresenter> {

    private EvaluateLevelAdapter adapter;

    private int[] imgRes = {
            R.mipmap.level_one,
            R.mipmap.level_two,
            R.mipmap.level_three,
            R.mipmap.level_four,
            R.mipmap.level_five
    };
    private int[] imgResSelect = {
            R.mipmap.level_one_check,
            R.mipmap.level_two_check,
            R.mipmap.level_three_check,
            R.mipmap.level_four_check,
            R.mipmap.level_five_check
    };

    private String[] titles = {
            "差", "一般", "还不错", "很满意", "强烈推荐"
    };

    private List<ItemEvaluateLevel> lists;
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();

    private Adapter mAdapter;
    private RecyclerView rv_pic_list;

    private String order_id;
    private EditText et_evaluate_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_evaluate_submit);
        order_id = getIntent().getStringExtra("order_id");
        initToolbar();
        initView();
    }

    private void initView() {
        et_evaluate_content = findViewById(R.id.et_evaluate_content);
        lists = new ArrayList<>();
        for (int i = 0; i < imgRes.length; i++) {
            lists.add(new ItemEvaluateLevel(imgRes[i], imgResSelect[i], titles[i], 1));
        }

        adapter = new EvaluateLevelAdapter(lists);
        adapter.setOnItemClickListener(levelOnItemClickListener);
        RecyclerView rv_evaluate_level = (RecyclerView) findViewById(R.id.rv_evaluate_level);
        rv_evaluate_level.setAdapter(adapter);
        rv_evaluate_level.setLayoutManager(new GridLayoutManager(this, 5));
        rv_evaluate_level.addItemDecoration(new GridItemDecoration(this));

        rv_pic_list = (RecyclerView) findViewById(R.id.rv_pic_list);
        rv_pic_list.setLayoutManager(new GridLayoutManager(this, 3));
        rv_pic_list.addItemDecoration(new DividerGridItemDecoration(DisplayUtils.dip2px(6), getResources().getColor(android.R.color.white)));
        int itemSize = (DisplayUtils.screenWidth - (DisplayUtils.dip2px(6) * 2)) / 3;
        mAdapter = new Adapter(this, itemSize, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (position == mAlbumFiles.size() - 1 && flagShowSelectPic) {
                    selectImage();
                } else {

                    previewImage(position);
                }
            }
        });
        mAlbumFiles.add(new AlbumFile());
        rv_pic_list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged(mAlbumFiles);
    }

    /***
     * 评价分
     */
    private int score = 0;
    /**
     * 评价点击
     */
    private EvaluateLevelAdapter.OnItemClickListener<ItemEvaluateLevel> levelOnItemClickListener = new EvaluateLevelAdapter.OnItemClickListener<ItemEvaluateLevel>() {
        @Override
        public void onItemClickLister(ItemEvaluateLevel itemEvaluateLevel, int position) {
            for (int i = 0; i < lists.size(); i++) {
                lists.get(i).setSelected(false);
            }
            lists.get(position).setSelected(true);
            adapter.notifyDataSetChanged();

            for (int i = 0; i < lists.size(); i++) {
                boolean selected = lists.get(i).isSelected();
                if (selected) {
                    score = i + 1;
                }
            }
            AppLog.Log("评价分： " + score);
        }
    };

    @Override
    public OrderListPresenter bindPresenter() {
        return new OrderListPresenter(this);
    }

    private void initToolbar() {
        RelativeLayout title_bar_base = (RelativeLayout) findViewById(R.id.title_bar_base);
        title_bar_base.findViewById(R.id.iv_navigation_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_bar_base.findViewById(R.id.tv_navigation_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEvaluate();
            }


        });
    }

    /**
     * 提交评价
     */
    private void submitEvaluate() {
        //2.判断是否有图片，如果有图片就上传
        /**
         * 1. flagShowSelectPic 显示了没有
         *     --显示了： mAlbumFiles.size()>1 说明有图片
         *     --没有显示：mAlbumFiles.size()==6
         */

        if (flagShowSelectPic && mAlbumFiles.size() > 1 || !flagShowSelectPic && mAlbumFiles.size() == 6) {
            String[] paths;
            if (flagShowSelectPic) {
                paths = new String[mAlbumFiles.size() - 1];
                for (int i = 0; i < mAlbumFiles.size() - 1; i++) {
                    paths[i] = mAlbumFiles.get(i).getPath();
                }

            } else {
                paths = new String[mAlbumFiles.size()];
                for (int i = 0; i < mAlbumFiles.size(); i++) {
                    paths[i] = mAlbumFiles.get(i).getPath();
                }

            }

            UpladTask task = new UpladTask();
            task.execute(paths);


        } else {
            sumbitAfterPic("");
        }
    }

    private void sumbitAfterPic(final String reurl) {

        presenter.getEvaluateSubmitPresenter(this, SharedUtil.getPreferStr(SharedKey.USER_ID),order_id, et_evaluate_content.getText().toString(), score, reurl, new BasePresenter.OnUIThreadListener<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                if (result) {
                    AppToast.ShowToast("提交成功");
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    }

    class UpladTask extends AsyncTask<String[], Void, String> {
        @Override
        protected String doInBackground(String[]... strings) {
            String[] string = strings[0];
            return useHttpUrl(string);
        }

        @Override
        protected void onPostExecute(String string) {
            sumbitAfterPic(string);
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
                    if (i < path.length - 1) {
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

    /**
     * 是否显示了 选择图片这个控件
     */
    private boolean flagShowSelectPic = true;

    /**
     * Preview image, to album.
     */
    private void previewImage(int position) {
        if (mAlbumFiles == null || mAlbumFiles.size() == 0) {
            Toast.makeText(this, "请先选择媒体文件", Toast.LENGTH_LONG).show();
        } else {
            if (flagShowSelectPic) {
                mAlbumFiles.remove(mAlbumFiles.size() - 1);
            }
            Album.galleryAlbum(this)
                    .checkable(true)
                    .checkedList(mAlbumFiles)
                    .currentPosition(position)
                    .widget(
                            Widget.newDarkBuilder(this)
                                    .title("选择图片")
                                    .build()
                    )
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            mAlbumFiles.clear();
                            mAlbumFiles = result;
                            if (mAlbumFiles.size() < 6) {
                                mAlbumFiles.add(new AlbumFile());
                                flagShowSelectPic = true;
                            } else {
                                flagShowSelectPic = false;
                            }
                            mAdapter.notifyDataSetChanged(mAlbumFiles);
                        }
                    })
                    .onCancel(new Action<String>() {
                        @Override
                        public void onAction(@NonNull String result) {

                        }
                    }).start();
        }
    }

    /**
     * Select picture, from album.
     */
    private void selectImage() {
        Album.image(this)
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(6)
                .checkedList(mAlbumFiles)
                .widget(
                        Widget.newDarkBuilder(this)
                                .statusBarColor(getResources().getColor(R.color.colorPrimary))
                                .toolBarColor(getResources().getColor(R.color.colorPrimary))
                                .title("选择图片")
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles.clear();
                        mAlbumFiles = result;
                        if (mAlbumFiles.size() < 6) {
                            mAlbumFiles.add(new AlbumFile());
                            flagShowSelectPic = true;
                        } else {
                            flagShowSelectPic = false;
                        }
                        for (AlbumFile f : result) {
                            if (f != null) {
                                AppLog.Log("f:" + f.getPath());
                            }
                        }
                        mAdapter.notifyDataSetChanged(mAlbumFiles);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();

    }


}
