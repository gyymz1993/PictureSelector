package itbour.onetouchshow.activity.myworks;

import android.content.Context;

import itbour.onetouchshow.bean.MyWorksBean;
import itbour.onetouchshow.mvp.BasePresenter;
import itbour.onetouchshow.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyworksContract {
    interface View extends BaseView {

        void deleteSuccess(MyWorksBean.ListBean listBean);


        void reNameSuccess(MyWorksBean.ListBean listBean);
        
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
