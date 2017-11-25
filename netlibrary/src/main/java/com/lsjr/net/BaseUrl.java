package com.lsjr.net;

public interface BaseUrl {
    //外网
//    String HTTP_ENCRYPT_BASE = "https://mobile.itbour.com/";
    //mock接口
//    String HTTP_ENCRYPT_BASE = "http://114.55.172.253:7000/mock/11/";
    //测试服务器
    String HTTP_ENCRYPT_BASE = "http://192.168.1.250:2918/";
    //王俊测试
//    String HTTP_ENCRYPT_BASE = "http://192.168.31.3:2918/";

    /**
     * post前不带/
     */

    String HTTP_ENCRYPT_ENDDING_POST = "encrypt/encrypt";
    /**
     * get请求
     */
    String HTTP_ENCRYPT_ENDDING_GET = "encrypt/encrypt?";


}
