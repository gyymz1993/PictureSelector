package itbour.onetouchshow;

import android.app.Application;

import com.google.gson.Gson;
import com.lsjr.net.DcodeService;
import com.squareup.leakcanary.LeakCanary;
import com.three.ConfigAppKey;
import com.three.ThirdConfigManager;

import itbour.onetouchshow.base.BaseApplication;
import itbour.onetouchshow.bean.LoginBean;
import itbour.onetouchshow.utils.SpUtils;

/**
 * Created by onetouch on 2017/11/8.
 */

public class App extends Application {

    public static String APP_ID = "wx5022d3c8ad81e74f";
    String APP_SECRET = "db846ab731f65c89fe77c49ad3af5612";

    @Override
    public void onCreate() {
        super.onCreate();

        SpUtils.getInstance().init(App.this);
        DcodeService.initialize(this);
        BaseApplication.instance().initialize(this);
        //初始化视频播放管理器
       // VideoPlayManager.instance().initialize(this);
        //leakCanary内存泄露检查
        LeakCanary.install(this);

        ConfigAppKey config = ConfigAppKey.instance().wxId(APP_ID).wxSecret(APP_SECRET);
        ThirdConfigManager.init(config);
    }

    public static LoginBean getLoginBean() {
        String userInfor = SpUtils.getInstance().getString("userInfor");
        LoginBean loginBean = new Gson().fromJson(userInfor, LoginBean.class);
        if (loginBean != null) {
            try {
                throw new Exception("你还没有登陆哦");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loginBean;
    }
}
