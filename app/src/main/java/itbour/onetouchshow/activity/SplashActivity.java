package itbour.onetouchshow.activity;

import android.os.Bundle;

import itbour.onetouchshow.R;
import itbour.onetouchshow.activity.login.LoginActivity;
import itbour.onetouchshow.activity.main.MainActivity;
import itbour.onetouchshow.mvp.MVPBaseActivity;
import itbour.onetouchshow.utils.SpUtils;
import itbour.onetouchshow.utils.UIUtils;

/**
 * Created by onetouch on 2017/11/25.
 */

public class SplashActivity extends MVPBaseActivity {

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SpUtils.getInstance().getBoolean("isFirst", true)) {
                    openActivity(BootVideoPalyActivity.class);
                } else {
                    openActivity(MainActivity.class);
                }
                finish();
            }
        }, 2000);
    }
}
