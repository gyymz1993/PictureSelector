package itbour.onetouchshow.fragment.main;


import itbour.onetouchshow.mvp.BasePresenter;
import itbour.onetouchshow.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseView {
        void loadPageInfoSuccess(String result);

        void loadPageInfoFail(String result);
    }

    interface Presenter extends BasePresenter<View> {
        
    }
}
