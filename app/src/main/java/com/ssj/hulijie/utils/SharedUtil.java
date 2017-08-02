/*
 * Copyright (c) 2016, HuiZhe Corporation, All Rights Reserved
 */
package com.ssj.hulijie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.ssj.hulijie.base.HljAppliation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;

// 封装 sharepreferences
public final class SharedUtil {

    /**
     * SharedPreferences中储存数据的路径
     **/
    public final static String DATA_URL = "/data/data/";

    private static String getPackageName(Context mthis) {
        String str = "";
        PackageManager manager = mthis.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(mthis.getPackageName(), 0);
            str = info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return str;
    }

    // 字符串
    public static void setPreferStr(String key, String content) {
        setPreferStr(HljAppliation.getContext(), key, content);
    }

    private static void setPreferStr(Context mthis, String key, String content) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, content);
        editor.apply();
    }

    public static String getPreferStr(String key) {
        return getPreferStr(HljAppliation.getContext(), key);
    }

    public static String getPreferStr(String key, String defValue) {
        SharedPreferences sp = HljAppliation.getContext().getSharedPreferences(getPackageName(HljAppliation.getContext()), 0);
        return sp.getString(key, defValue);
    }

    private static String getPreferStr(Context mthis, String key) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        return sp.getString(key, "");
    }

    public static long getPreferLong(String key, long defValue) {

        return getPreferLong(HljAppliation.getContext(), key, defValue);
    }

    private static long getPreferLong(Context mthis, String key, long defValue) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);

    }


    public static void setPreferLong(String key, long content) {
        setPreferLong(HljAppliation.getContext(), key, content);
    }

    private static void setPreferLong(Context mthis, String key, long content) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, content);
        editor.apply();
    }


    // boolean
    public static void setPreferBool(String key, boolean content) {
        setPreferBool(HljAppliation.getContext(), key, content);
    }

    private static void setPreferBool(Context mthis, String key, boolean content) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, content);
        editor.apply();
    }

    public static boolean getPreferBool(String key, boolean defValue) {
        return getPreferBool(HljAppliation.getContext(), key, defValue);
    }

    private static boolean getPreferBool(Context mthis, String key, boolean defValue) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    // int
    public static int getPreferInt(String key, int defValue) {
        return getPreferInt(HljAppliation.getContext(), key, defValue);
    }

    private static int getPreferInt(Context mthis, String key, int defValue) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static void setPreferInt(String key, int content) {
        setPreferInt(HljAppliation.getContext(), key, content);
    }

    private static void setPreferInt(Context mthis, String key, int content) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, content);
        editor.apply();
    }

    // int
    public static Float getPreferFloat(String key, float defValue) {
        return getPreferFloat(HljAppliation.getContext(), key, defValue);
    }

    private static Float getPreferFloat(Context mthis, String key, float defValue) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), Context.MODE_PRIVATE);
        return sp.getFloat(key, defValue);
    }

    public static void setPreferFloat(String key, float content) {
        setPreferFloat(HljAppliation.getContext(), key, content);
    }

    private static void setPreferFloat(Context mthis, String key, float content) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, content);
        editor.apply();
    }

    // list
    public static List getListObj(String key, Class clazz) {
        List list = null;
        try {
            String str = getPreferStr(key);
            if (!TextUtils.isEmpty(str)) {
                list = JSON.parseArray(str, clazz);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return list;
    }

    public static void setListObj(String key, Object t) {
        String str = "";
        if (t != null) {
            str = JSON.toJSONString(t);
        }
        setPreferStr(key, str);
    }


    public static void setObject(String key, Object obj) {
        setObject(HljAppliation.getContext(), key, obj);
    }

    /**
     * desc:保存对象
     *
     * @param mthis
     * @param key
     * @param obj   要保存的对象，只能保存实现了serializable的对象
     *              modified:
     */
    public static void setObject(Context mthis, String key, Object obj) {
        try {
            // 保存对象
            SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            editor.putString(key, bytesToHexString);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getObject(String key) {
        return getObject(HljAppliation.getContext(), key);
    }

    /**
     * desc:获取保存的Object对象
     *
     * @param mthis
     * @param key
     * @return modified:
     */
    public static Object getObject(Context mthis, String key) {
        try {
            SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), Context.MODE_PRIVATE);
            if (sp.contains(key)) {
                String string = sp.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:将16进制的数据转为数组
     * <p>创建人：聂旭阳 , 2014-5-25 上午11:08:33</p>
     *
     * @param data
     * @return modified:
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// a 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// a 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

    public static void clear() {
        /** 删除SharedPreferences文件 **/
        File file = new File(DATA_URL + getPackageName(HljAppliation.getContext())
                + "/shared_prefs", getPackageName(HljAppliation.getContext()) + ".xml");
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 清除配置信息
     * @param mthis
     */
    public static void init(Context mthis) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

}
