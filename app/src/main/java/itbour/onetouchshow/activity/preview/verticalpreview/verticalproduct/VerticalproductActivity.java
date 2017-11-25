package itbour.onetouchshow.activity.preview.verticalpreview.verticalproduct;


import android.os.Bundle;

import itbour.onetouchshow.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class VerticalproductActivity extends MVPBaseActivity<VerticalproductContract.View, VerticalproductPresenter> implements VerticalproductContract.View {

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
