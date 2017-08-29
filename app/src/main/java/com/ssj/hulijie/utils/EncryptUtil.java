/*
 * Copyright (c) 2016, HuiZhe Corporation, All Rights Reserved
 */
package com.ssj.hulijie.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// 加密工具类
public class EncryptUtil {
    // 用来将字节转换成 16 进制表示的字符
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    // SHA-1加密
    public static String sha1Hex(String buffer) {
        MessageDigest sha1;
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update(buffer.getBytes());
            byte[] m = sha1.digest();// SHA-1加密
            return getString(m);
        } catch (NoSuchAlgorithmException e) {
            AppLog.Log("SHA-1加密失败");
            e.printStackTrace();
            return null;
        }
    }

    // MD5加密
    public static String getMD5(String string) {

        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(string.getBytes());
            byte tmp[] = md5.digest();
            return getString(tmp);
        } catch (NoSuchAlgorithmException e) {
            AppLog.Log("MD5加密失败");
            e.printStackTrace();
            return string;
        }

    }

    // 单个文件MD5加密
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest md5;
        FileInputStream in;
        byte buffer[] = new byte[1024];
        int len;
        try {
            md5 = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                md5.update(buffer, 0, len);
            }
            in.close();
            byte tmp[] = md5.digest();
            return getString(tmp);
        } catch (NoSuchAlgorithmException e) {
            AppLog.Log("MD5加密失败");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static String getString(byte[] tmp) {
        String back;
        int len = tmp.length;
        char str[] = new char[len * 2];
        int k = 0; // 表示转换结果中对应的字符位置
        for (byte byte0 : tmp) { // 从第一个字节开始，对 MD5 的每一个字节转换成 16 进制字符的转换
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,>>> 为逻辑右移，将符号位一起右移
            str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        back = new String(str); // 换后的结果
        return back;
    }

}
