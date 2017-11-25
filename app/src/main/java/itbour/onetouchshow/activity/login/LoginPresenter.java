package itbour.onetouchshow.activity.login;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.callback.StringCallBack;
import com.lsjr.utils.HttpUtils;
import com.three.login.LoginPlatform;
import com.three.login.ThirdLoginUtils;
import com.three.login.bean.BaseToken;
import com.three.login.listener.LoginListener;
import com.three.login.result.LoginResult;
import com.three.pay.PayPlatform;
import com.three.pay.ThirdPayUtils;
import com.three.pay.bean.PayInfo;
import com.three.pay.listener.PayListener;

import java.util.HashMap;

import itbour.onetouchshow.App;
import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.bean.PayInfoBean;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    public void doCodeLogin(String number, String code) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", 2 + "");
        stringStringHashMap.put("uuid", "EEBAF7D1EC6914A914CE95209D6BFDBB-Android");
        stringStringHashMap.put("zone", "+86");
        stringStringHashMap.put("mobile", number);
        stringStringHashMap.put("verCode", code);
        stringStringHashMap.put("type", 1 + "");
        HttpUtils.getInstance().executeGet(AppConfig.CODE_LOIGN, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                T_.showToastReal(exception);
                mvpView.loginfailed(exception);
            }

            @Override
            protected void onSuccess(String response) {
                mvpView.loginSuccess(response);
            }
        });

    }

    public void getCode(String number) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", 2 + "");
        // stringStringHashMap.put("uuid", "EEBAF7D1EC6914A914CE95209D6BFDBB-Android");
        stringStringHashMap.put("zone", "+86");
        stringStringHashMap.put("mobile", number);
        stringStringHashMap.put("type", 1 + "");
        //用于获取服务器通知例如服务器维护等 已加密
        //  String NOTIFICATON = "/v5/tg3/getMainPage_v6_3";
        //加密请求地址
        //HashMap requestParams = Encrypt.transEncrytionParams(stringStringHashMap, NOTIFICATON);

        HttpUtils.getInstance().executeGet(AppConfig.GET_CODE, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                // mvpView.loadFaild(exception);
                mvpView.getCodefailed();
            }

            @Override
            protected void onSuccess(String response) {
                mvpView.getCodeSuccess(true);
            }
        });
    }


    /**
     * 去除水
     */
    public void toWatermark() {

        L_.e(App.getLoginBean().getUserInfo().getUserId() + "");
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", 610);
        stringObjectHashMap.put("userId", "299188");
        stringObjectHashMap.put("docId", "94");
        stringObjectHashMap.put("orderType", "2");
        //微信10  支付宝20
        stringObjectHashMap.put("paymentPlatform", 20);
        HttpUtils.getInstance().executeGet(AppConfig.GET_CREATEWATERMARKORDER_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                L_.e(exception);
            }

            @Override
            protected void onSuccess(String response) {

                L_.e("response"+response);
                try {
                    JSONObject jsonObject = JSON.parseObject(response);
                    // order no  61R45L09X95P6
                    String orderid = jsonObject.getString("orderId");
                    String orderNo = jsonObject.getString("orderNo");
                    L_.e("orderid  :" + orderid + "----orderNo ：" + orderNo);
                    //onOrderPay(orderid);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void onOrderPay(String orderId,Activity activity) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", 610);
        stringObjectHashMap.put("userId","299188");
        stringObjectHashMap.put("orderId", 48+"");
        HttpUtils.getInstance().executeGet(AppConfig.GETORDERPAYPARAM_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                L_.e(exception);
            }

            @Override
            protected void onSuccess(String response) {

                PayInfoBean payInfoBean = new Gson().fromJson(response, PayInfoBean.class);
                if ( payInfoBean.getPaymentPlatform()==10){
                    PayInfo payInfo=new PayInfo();
                    payInfo.setNonceStr(payInfoBean.getWechat().getNonceStr());
                    payInfo.setPrepayId(payInfoBean.getWechat().getPrepayId());
                    payInfo.setTimeStamp(payInfoBean.getWechat().getTimeStamp());
                    payInfo.setSign(payInfoBean.getWechat().getSign());
                    ThirdPayUtils.initialize(activity).pay(PayPlatform.WxPay,payInfo, new PayListener() {
                        @Override
                        public void paySuccess() {
                            T_.showToastReal("支付成功");
                            L_.e("支付成功");
                        }

                        @Override
                        public void payFailure(Exception e) {
                            L_.e("支付失败");
                            T_.showToastReal("支付失败"+e.getMessage());
                        }

                        @Override
                        public void userCancel() {
                            T_.showToastReal("取消支付");
                            L_.e("取消支付");
                        }
                    });
                }
                L_.e(response);
            }
        });
    }


    public void postDataTest() {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", 2);
        //userId
        stringObjectHashMap.put("userId", "88");
        String GET_INFO_QUESTIONS_V62 = "/v5/lab/getInfoQuestions_v6_2";
        HttpUtils.getInstance().postServiceDataForbody(GET_INFO_QUESTIONS_V62, stringObjectHashMap, new StringCallBack() {
            @Override
            protected void onXError(String exception) {
                L_.e(exception);
            }

            @Override
            protected void onSuccess(String response) {
                //L_.e(response);
            }
        });

    }


    public void weichatLogin(Activity activity) {
        ThirdLoginUtils.initialize(activity).login(LoginPlatform.WX, new LoginListener() {
            @Override
            public void loginSuccess(LoginResult result) {
                L_.e("TAG", result.toString() + result.getUserInfo().toString());
               // L_.e("TAG", "登录成功");
                thirdLogin(result);
                //mPresenter.thirdLogin();
            }

            @Override
            public void beforeFetchUserInfo(BaseToken token) {
                L_.e("TAG", "获取用户信息" + token.toString());
            }

            @Override
            public void loginFailure(Exception e) {
                e.printStackTrace();
                L_.e("TAG", "登录失败");
            }

            @Override
            public void loginCancel() {
                Log.i("TAG", "登录取消");
            }
        });
    }

    private void thirdLogin(LoginResult result) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", 2 + "");
        // stringStringHashMap.put("uuid", "EEBAF7D1EC6914A914CE95209D6BFDBB-Android");
        stringStringHashMap.put("nickName", result.getUserInfo().getNickname());
        stringStringHashMap.put("avatarUrl", result.getUserInfo().getHeadImageUrl());
        stringStringHashMap.put("field1", result.getToken().getAccessToken());
        stringStringHashMap.put("field2", result.getToken().getOpenid());
        stringStringHashMap.put("platform", AppConfig.YJX_THIRD_LOGIN_PLATFORM_WECHAT + "");
        //用于获取服务器通知例如服务器维护等 已加密
        //  String NOTIFICATON = "/v5/tg3/getMainPage_v6_3";
        //加密请求地址
        //HashMap requestParams = Encrypt.transEncrytionParams(stringStringHashMap, NOTIFICATON);

        HttpUtils.getInstance().executeGet(AppConfig.THIRD_lOGIN, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                // mvpView.loadFaild(exception);
                L_.e("exception" + exception);
                mvpView.loginfailed(exception);

            }

            @Override
            protected void onSuccess(String response) {
                L_.e("exception" + response);
                mvpView.loginSuccess(response);
            }
        });
    }


}
