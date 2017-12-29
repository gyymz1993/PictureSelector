package itbour.onetouchshow;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.google.gson.Gson;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.net.DcodeService;
import com.lsjr.utils.HttpUtils;
import com.squareup.leakcanary.LeakCanary;
import com.three.ConfigAppKey;
import com.three.ThirdConfigManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.game.UMGameAgent;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

import itbour.onetouchshow.base.BaseApplication;
import itbour.onetouchshow.bean.LoginBean;
import itbour.onetouchshow.bean.OssSignBean;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.SpUtils;
import itbour.onetouchshow.utils.T_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * Created by onetouch on 2017/11/8.
 */

public class App extends Application {

    public static String APP_ID = "wx5022d3c8ad81e74f";
    String APP_SECRET = "db846ab731f65c89fe77c49ad3af5612";
    public static OSSClient ossClient;
    public static Context context = null;
    //阿里云存储的字段
    private String accessKeyId;
    //阿里云存储的字段
    private String signature;
    private static boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SpUtils.getInstance().init(App.this);
        DcodeService.initialize(this);
        BaseApplication.instance().initialize(this);
        //初始化视频播放管理器
        //leakCanary内存泄露检查
        LeakCanary.install(this);
        L_.init(false,"OneTouchShow");

        iniAliyun();
        // AppInitService.startService(this);

        ConfigAppKey config = ConfigAppKey.instance().wxId(APP_ID).wxSecret(APP_SECRET);
        ThirdConfigManager.init(config);

        //友盟设置统计类型

        // 设置输出运行时日志
        UMGameAgent.setDebugMode(true);
        UMGameAgent.init(this);
        // Deprecated UMGameAgent.setPlayerLevel("LV.01");
        UMGameAgent.setPlayerLevel(1);
        // UMGameAgent.setSessionContinueMillis(1000);
        // UMGameAgent.startWithConfigure(
        // new UMAnalyticsConfig(mContext, "4f83c5d852701564c0000011", "Umeng",
        // EScenarioType.E_UM_GAME));
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_GAME);
       // MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void iniAliyun() {
        // L_.e("初始化阿里云");
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        //阿里云OSS
        OSSCredentialProvider credentialProvider = new OSSCustomSignerCredentialProvider() {

            @Override
            public String signContent(String s) {
                HashMap<String, Object> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("device", AppConst.ANDROID_DEVICE);
                stringStringHashMap.put("strToSign", s);

                final Semaphore semaph = new Semaphore(0);
                L_.e("初始化阿里云" + AppConfig.GETOSSSIGN_V1_0);
                HttpUtils.getInstance().executeGet(AppConfig.GETOSSSIGN_V1_0, stringStringHashMap, new EncryBeanCallBack() {
                    @Override
                    protected void onXError(String exception) {
                        L_.e("初始化阿里云" + exception);
                        semaph.release(1);

                    }

                    @Override
                    protected void onSuccess(String response) {
                        L_.i("response===" + response);

                        OssSignBean ossSignBean = new Gson().fromJson(response, OssSignBean.class);
                        accessKeyId = ossSignBean.getAccessKeyId();
                        signature = ossSignBean.getSignature();
                        semaph.release(1);
                    }
                });

                try {
                    semaph.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (accessKeyId == null || signature == null) {
                    return null;
                }
                return "OSS " + accessKeyId + ":" + signature;
            }
        };
        ossClient = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
    }

    public static LoginBean getLoginBean() {
        String userInfor = SpUtils.getInstance().getString(AppConst.USER_INFO);
        LoginBean loginBean = new Gson().fromJson(userInfor, LoginBean.class);
        if (loginBean == null) {
            try {
                T_.showToastReal("你还没有登陆哟");
                throw new Exception("你还没有登陆哦");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loginBean;
    }

    public static String getUserId() {
        int userId = getLoginBean().getUserInfo().getUserId();
        return String.valueOf(userId);
    }


    public static void loginOut() {
        SpUtils.getInstance().saveBoolean(AppConst.ISLOGIN, false);
        SpUtils.getInstance().saveString(AppConst.USER_INFO, "");
    }

    public static boolean isLogin() {
        return SpUtils.getInstance().getBoolean(AppConst.ISLOGIN, false);
    }

    /**
     * 测试时使用
     */
    @SuppressLint("ShowToast")
    public static boolean testWhendebug() {
        return isDebug;
    }
}
