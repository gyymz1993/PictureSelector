package itbour.onetouchshow.fragment.me;

import android.content.Context;

import itbour.onetouchshow.mvp.BasePresenter;
import itbour.onetouchshow.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MeContract {
    interface View extends BaseView {
        void loaduserDetailSuccess(String string);
        void loaduserDetailFail(String string);
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
