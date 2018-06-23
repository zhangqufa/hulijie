/*
 * Copyright (c) 2016, HuiZhe Corporation, All Rights Reserved
 */
package com.ssj.hulijie.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetUtil {


    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager ex = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = ex.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception var4) {
            return " 获取IP出错了!!!!请保证是WIFI,或者请重新打开网络!\n" + var4.getMessage();
        }
    }

    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 255).append(".");
        sb.append(ipInt >> 8 & 255).append(".");
        sb.append(ipInt >> 16 & 255).append(".");
        sb.append(ipInt >> 24 & 255);
        return sb.toString();
    }
    /**
     * 检查用户的网络:是否有网络
     */
    public static boolean checkNet(Context context) {
        boolean isWIFI = isWIFIConnection(context);  // 判断：WIFI链接
        boolean isMOBILE = isMOBILEConnection(context);  // 判断：Mobile链接

        // 如果Mobile在链接，判断是哪个APN被选中了
//		if (isMOBILE) {
//			// APN被选中,的代理信息是否有内容，如果有wap方式
//			readAPN(context);// 判断是哪个APN被选中了
//		}
        return isWIFI || isMOBILE;
    }

    /**
     * APN被选中,的代理信息是否有内容，如果有wap方式
     *
     * @param context
     */
/*	private static void readAPN(Context context) {
        Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");// 4.0模拟器屏蔽掉该权限
		// 操作联系人类似
		ContentResolver resolver = context.getContentResolver();
		// 判断是哪个APN被选中了
		Cursor cursor = resolver.query(PREFERRED_APN_URI, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			GlobalParams.PROXY = cursor.getString(cursor.getColumnIndex("proxy"));
			GlobalParams.PORT = cursor.getInt(cursor.getColumnIndex("port"));
		}

	}*/

    // 判断：Mobile链接
    private static boolean isMOBILEConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return networkInfo != null && networkInfo.isConnected();
    }

    // 判断：WIFI链接
    private static boolean isWIFIConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String getWifiMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }
}
