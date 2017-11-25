package itbour.onetouchshow.fragment.design;

import android.content.Context;

import itbour.onetouchshow.mvp.BasePresenter;
import itbour.onetouchshow.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class DesignContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
