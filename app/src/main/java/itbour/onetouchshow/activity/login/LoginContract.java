package itbour.onetouchshow.activity.login;


import itbour.onetouchshow.mvp.BasePresenter;
import itbour.onetouchshow.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {

        void getCodeSuccess(boolean isSucceed);

        void getCodefailed();

        void loginSuccess(String result);

        void loginfailed(String msg);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
