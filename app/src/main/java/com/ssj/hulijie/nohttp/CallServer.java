/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ssj.hulijie.nohttp;

import com.ssj.hulijie.pro.base.view.BaseActivity;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;


/**
 * Created in Oct 23, 2015 1:00:56 PM.
 *
 * @author vic.
 */
public class CallServer {

    private static CallServer callServer;

    /**
     * 请求队列.
     */
    private RequestQueue requestQueue;

    /**
     * 下载队列.
     */
    private static DownloadQueue downloadQueue;

    private CallServer() {
        requestQueue = NoHttp.newRequestQueue();
    }

    /**
     * 请求队列.
     */
    public synchronized static CallServer getRequestInstance() {
        if (callServer == null)
            callServer = new CallServer();
        return callServer;
    }

    /**
     * 下载队列.
     */
    public static DownloadQueue getDownloadInstance() {
        if (downloadQueue == null)
            downloadQueue = NoHttp.newDownloadQueue(2);
        return downloadQueue;
    }

    /**
     * 添加一个请求到请求队列.
     *
     * @param context   context用来实例化dialog.
     * @param what      用来标志请求, 当多个请求使用同一个{@link HttpListener}时, 在回调方法中会返回这个what.
     * @param request   请求对象.
     * @param callback  结果回调对象.
     * @param canCancel 是否允许用户取消请求.
     * @param isLoading 是否显示dialog.
     */
    public <T> void add(BaseActivity context, int what, Request<T> request, HttpListener<T> callback, boolean canCancel, boolean isLoading) {

//        if (Constant.LOG_DEBUG) {
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(URLRoot.API_PATH_TEST, 80));
//            request.setProxy(proxy);
//        }
//        String cookies_str = SharedUtil.getPreferStr(SharedKey.MYCOOKIES);
//        if (!TextUtils.isEmpty(cookies_str)&&SharedUtil.getPreferBool(SharedKey.USER_LOGINED,false)) {
//            List<MyCookies> cookiesList = new ArrayList<>(JSON.parseArray(cookies_str, MyCookies.class));
//
//            for (MyCookies c : cookiesList
//                    ) {
//                HttpCookie httpCookie = new HttpCookie(c.getKey(), c.getValue());
//                request.addHeader(httpCookie);
//            }
//
//        }

        requestQueue.add(what, request, new HttpResponseListener<T>(context, request, callback, canCancel, isLoading));
    }

    /**
     * 取消这个sign标记的所有请求.
     */
    public void cancelBySign(Object sign) {
        requestQueue.cancelBySign(sign);
    }

    /**
     * 取消队列中所有请求.
     */
    public void cancelAll() {
        requestQueue.cancelAll();
    }

    /**
     * 退出app时停止所有请求.
     */
    public void stopAll() {
        requestQueue.stop();
    }

}
