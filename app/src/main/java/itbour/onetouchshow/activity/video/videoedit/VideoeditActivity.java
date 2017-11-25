package itbour.onetouchshow.activity.video.videoedit;


import android.os.Bundle;

import itbour.onetouchshow.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VideoeditActivity extends MVPBaseActivity<VideoeditContract.View, VideoeditPresenter> implements VideoeditContract.View {
    @Override
    protected void initTitle() {
        super.initTitle();

    }

    @Override
    public void loadSucceed(String result) {

    }

    @Override
    public void loadFaild(String error) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }
}
