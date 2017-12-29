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
import itbour.onetouchshow.AppConst;
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
                T_.showCustomToast(exception);
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
                T_.showCustomToast(exception);
                mvpView.getCodefailed();
            }

            @Override
            protected void onSuccess(String response) {
                T_.showCustomToast("获取验证码成功");
                mvpView.getCodeSuccess(true);
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
        ThirdLoginUtils.initialize().login(activity, LoginPlatform.WX, new LoginListener() {
            @Override
            public void loginSuccess(LoginResult result) {
                L_.e("TAG", result.toString() + result.getUserInfo().toString());

                //String resultJson = "{\"mPlatform\":3,\"mToken\":{\"refresh_token\":\"4_gSyEmXbCQb2lkmGQ3_ywcqDlcX4wh-qFRu-Jwam2YW9OBxS_Sf-4X2oxjCLgOHpFfEg5xxb3IfxXs1f5DMb18emikGtxEFS4PiqSLHH-e_Y\",\"access_token\":\"4_6GPjlzzYBBpzaW5Kj1kLXAeKKsP_-i9_vnrrG6D8ClfG9cwqQfLAbrSDC-tf-SxCjAZqsUHKD1c98yoQyBogKQi95JLgfxEybbx6faNGWEM\",\"openid\":\"oL6fO1LXfOtqqKMwAkSN-EDRA7vc\"},\"mUserInfo\":{\"city\":\"Wuhan\",\"country\":\"CN\",\"province\":\"Hubei\",\"unionid\":\"o07tHv3ylDHkC4tyKYrQbi5WMGTI\",\"headImageUrl\":\"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIpr8mtHBHZnAIBXMqtIJSJW43EEyOiaFnxulQURUgqb7SqAwesWb0vP9JUDuMpHSnVCibF8kya4oGg/0\",\"headImageUrlLarge\":\"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIpr8mtHBHZnAIBXMqtIJSJW43EEyOiaFnxulQURUgqb7SqAwesWb0vP9JUDuMpHSnVCibF8kya4oGg/0\",\"nickname\":\"noop\",\"openId\":\"oL6fO1LXfOtqqKMwAkSN-EDRA7vc\",\"sex\":1}}";
                // result = new Gson().fromJson(resultJson, LoginResult.class);
                // L_.e("TAG", "登录成功");
                thirdLogin(result);
//                mPresenter.thirdLogin();
            }

            @Override
            public void beforeFetchUserInfo(BaseToken token) {
                L_.e("TAG", "获取用户信息" + token.toString());
            }

            @Override
            public void loginFailure(Exception e) {
                e.printStackTrace();
                L_.e("TAG", "登录失败");
                T_.showToastReal(e.getMessage());
            }

            @Override
            public void loginCancel() {
                Log.i("TAG", "登录取消");
            }
        });
    }

    public void thirdLogin(LoginResult result) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", AppConst.ANDROID_DEVICE + "");
        // stringStringHashMap.put("uuid", "EEBAF7D1EC6914A914CE95209D6BFDBB-Android");
        stringStringHashMap.put("nickName", result.getUserInfo().getNickname());
        stringStringHashMap.put("avatarUrl", result.getUserInfo().getHeadImageUrl());
        //微信用户唯一标识
        stringStringHashMap.put("field1", result.getUserInfo().getUnionid());
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
