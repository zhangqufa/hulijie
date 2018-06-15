package com.ssj.hulijie.pro.mine.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.ssj.hulijie.pro.mine.adapter.Adapter;
import com.ssj.hulijie.pro.mine.adapter.EvaluateLevelAdapter;
import com.ssj.hulijie.pro.mine.bean.ItemEvaluateLevel;
import com.ssj.hulijie.pro.mine.presenter.OrderListPresenter;
import com.ssj.hulijie.utils.AppLog;
import com.ssj.hulijie.utils.AppToast;
import com.ssj.hulijie.utils.DisplayUtils;
import com.ssj.hulijie.widget.DividerGridItemDecoration;
import com.ssj.hulijie.widget.recylerview.GridItemDecoration;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.impl.OnItemClickListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_evaluate_submit);
        initToolbar();
        initView();
    }

    private void initView() {

        lists = new ArrayList<>();
        for (int i = 0; i < imgRes.length; i++) {
            lists.add(new ItemEvaluateLevel(imgRes[i], imgResSelect[i], titles[i], 1));
        }

        adapter = new EvaluateLevelAdapter(lists);
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
                if (position == mAlbumFiles.size() - 1) {
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
                AppToast.ShowToast("提交");
            }
        });
    }


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
