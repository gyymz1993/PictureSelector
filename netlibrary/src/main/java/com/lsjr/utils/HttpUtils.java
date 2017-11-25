package com.lsjr.utils;

import android.util.Log;

import com.lsjr.bean.Encrypt;
import com.lsjr.bean.EncryptBean;
import com.lsjr.bean.EncryptReturnBean;
import com.lsjr.callback.BaseCallBack;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.callback.StringCallBack;
import com.lsjr.net.BaseUrl;
import com.lsjr.net.DcodeService;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/7/21 10:42
 */

public class HttpUtils {

    /**
     * 内网使用
     */
    private String HTTP_ENCRYPT = "http://192.168.1.250:2918/encrypt/encrypt";

    private static HttpUtils httpUtils;
    private CompositeSubscription mCompositeSubscription;
    private HttpUtils() {}
    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                httpUtils = new HttpUtils();
            }
        }
        return httpUtils;
    }

    private void loadDataForNet(Observable observable, Subscriber subscriber) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber));
    }

    private void downLoadForNet(Observable observable, Subscriber subscriber) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe(subscriber));
    }

    /**
     *  post 网络请求   加密处理
     * */
    private void postServiceData(String postUrl,final HashMap stringStringHashMap, final StringCallBack stringCallBack) {
        HashMap requestParams = Encrypt.transEncrytionParams(stringStringHashMap, postUrl);
        loadDataForNet(DcodeService.postServiceData(BaseUrl.HTTP_ENCRYPT_ENDDING_POST,requestParams).onErrorResumeNext(new HttpResultFunc<String>(stringCallBack)), stringCallBack);
    }

    /**
     * post 网络请求 forbody   加密处理
     * */
    public void postServiceDataForbody(String postUrl,final HashMap stringStringHashMap, final StringCallBack stringCallBack) {
        HashMap requestParams = Encrypt.transEncrytionParams(stringStringHashMap, postUrl);
        loadDataForNet(DcodeService.postServiceDataForBody(BaseUrl.HTTP_ENCRYPT_ENDDING_POST,requestParams).onErrorResumeNext(new HttpResultFunc<String>(stringCallBack)), stringCallBack);
    }

    /**
     * get 网络请求   加密处理
     * */
    public void getServiceData(String getUrl,final HashMap stringStringHashMap, final StringCallBack stringCallBack) {
        HashMap requestParams = Encrypt.transEncrytionParams(stringStringHashMap, getUrl);
        loadDataForNet(DcodeService.getServiceData(BaseUrl.HTTP_ENCRYPT_ENDDING_GET,requestParams).onErrorResumeNext(new HttpResultFunc<String>(stringCallBack)), stringCallBack);
    }


    /**
     * get 网络请求   加密处理
     * */
    public void executeGet(String getUrl,final HashMap stringStringHashMap, final EncryBeanCallBack stringCallBack) {
        EncryptBean encryptBean = Encrypt.transEncrytionParamsReftrofit(stringStringHashMap, getUrl);
        loadDataForNet(DcodeService.executeGet(encryptBean).onErrorResumeNext(new HttpResultFunc<EncryptReturnBean>(stringCallBack)), stringCallBack);
    }


    /**
     * post 网络请求 加密处理
     * */
    public void executePost(String getUrl,final HashMap stringStringHashMap, final EncryBeanCallBack stringCallBack) {
        EncryptBean encryptBean = Encrypt.transEncrytionParamsReftrofit(stringStringHashMap, getUrl);
        loadDataForNet(DcodeService.executePost(encryptBean).onErrorResumeNext(new HttpResultFunc<EncryptReturnBean>(stringCallBack)), stringCallBack);
    }


    private class HttpResultFunc<T> implements Func1<Throwable, Observable<T>> {
        BaseCallBack httpSubscriber;

        HttpResultFunc(BaseCallBack httpSubscriber) {
            this.httpSubscriber = httpSubscriber;
        }

        @Override
        public Observable<T> call(Throwable throwable) {
            Log.e("HttpResultFunc","ApiException.handleException(throwable)");
            httpSubscriber.onError(throwable);
            return Observable.error(throwable);
        }
    }



}


