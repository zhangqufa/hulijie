package com.ssj.hulijie.pro.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ssj.hulijie.utils.AppLog;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "jlm.db";
    public static final int DB_VERSION = 2;


    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //数据库创建时会调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        AppLog.Log("db_oncreate");
        db.beginTransaction();
        try {
            db.execSQL("create table history(_hid integer primary key autoincrement, h_name varchar(20)," +
                    "addtime varchar(20));");
            db.execSQL("create table footmark(_fid varchar(20) primary key," +
                    "f_itemimg varchar(255)," +
                    "f_itemtype varchar(20)," +
                    "f_title varchar(255)," +
                    "f_buyprice varchar(20)," +
                    "f_couponbills varchar(20)," +
                    "f_category integer," +
                    "addtime varchar(20));");
            db.setTransactionSuccessful();//设置事务成功


            AppLog.Log("数据库history第一次创建成功");
//            AppLog.Log("数据库footmark第一次创建成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();//结束事务
        }
    }

    //数据库在版本更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        AppLog.Log("db_onUpgrade");
        db.beginTransaction();
        try {
            if (oldVersion == 1) {
                db.execSQL("create table footmark(_fid varchar(20) primary key," +
                        "f_itemimg varchar(255)," +
                        "f_itemtype varchar(20)," +
                        "f_title varchar(255)," +
                        "f_buyprice varchar(20)," +
                        "f_couponbills varchar(20)," +
                        "f_category integer," +
                        "addtime varchar(20));");
            }
            db.setTransactionSuccessful();//设置事务成功
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();//结束事务
        }
        //版本更新回调
        AppLog.Log("数据库版本从" + oldVersion + "更新到" + newVersion);
    }


}
