package com.ssj.hulijie.utils;

/**
 * Created by Administrator on 2017/6/3.
 */

public class AppURL {

    public static final String URL_ROOT = "http://jassj.com";

    public static final String URL_API = URL_ROOT + "/i/index.php";

    public static final String URL_FIRST_PAGE_CATETORY = URL_API + "/home/cat";
    public static final String URL_FOUR_MODEL = URL_API + "/Home/active";
    public static final String URL_FIRSTPAGE_LIST = URL_API + "/Home/recommend";
    public static final String URL_CATETORY = URL_API + "/Home/all_cat";
    public static final String URL_SERVICE_DETAIL = URL_API + "/Home/goods";
    public static final String URL_CATEGORY_LIST = URL_API + "/Home/goods_list";
    public static final String URL_ADDRESS_LIST = URL_API + "/Home/address";
    public static final String URL_GET_VERIFY_CODE = URL_API + "/Home/sendcode";
    //极难所需的字符串
    public static final String URL_GET_VERIFY_STR = URL_API + "/home/start_captcha";
    public static final String URL_LOGIN = URL_API + "/Home/logreg";
    public static final String URL_EDIT_ADDRESS = URL_API + "/Home/address_edit";
    public static final String URL_DELETE_ADDRESS = URL_API + "/Home/address_del";
    public static final String URL_HOT_KEYS = URL_API + "/Home/common_keywords";
    public static final String URL_ACCESS_INFO = URL_API + "/Home/get_access_info";

    public static final String URL_ORDER_LIST = URL_API + "/order/order_list";
    public static final String URL_ORDER_PAY = URL_API + "/order/pay";
    public static final String URL_ORDER_ORDER = URL_API + "/order/order";
    public static final String URL_VERSION = URL_API + "/Home/versions";
    public static final String URL_ORDER_SERVICE_LIST = URL_API + "/order/service_list";
    public static final String URL_ORDER_SERVICE_ORDER_LIST = URL_API + "/order/seller_order_list";
    public static final String URL_ORDER_SERVICE_GETORDER = URL_API + "/order/get_order";
    public static final String URL_ORDER_CANCEL = URL_API + "/order/cancel";
    public static final String URL_ORDER_REFUND = URL_API + "/order/refund";
    public static final String URL_ORDER_CANCEL_SELLER = URL_API + "/order/cancel_for_sale";
    public static final String URL_ORDER_FINISH = URL_API + "/order/finish";

    //商家
    public static final String URL_SERVER_MONEY_INFO = URL_API + "/order/seller_money_info";
    //我的服务
    public static final String URL_SERVER_MINE = URL_API + "/home/store_goods_list";


    //图片上传
    public static final String URL_PIC_UPLOAD = URL_API + "/File/stream2Image";


}
