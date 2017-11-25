package itbour.onetouchshow.activity.preview.verticalpreview.verticalmodel;

import itbour.onetouchshow.bean.DesignListBean;
import itbour.onetouchshow.mvp.BasePresenter;
import itbour.onetouchshow.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class VerticalmodelContract {
    interface View extends BaseView {
        void isCollect();

        void unCollect();

        void nowFragmentVisable(DesignListBean.ListBean listBean);

        void collectSuccess(String text);
        void collectFaild();
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
