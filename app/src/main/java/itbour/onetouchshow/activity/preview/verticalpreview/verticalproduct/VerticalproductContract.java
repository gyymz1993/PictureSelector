package itbour.onetouchshow.activity.preview.verticalpreview.verticalproduct;

import itbour.onetouchshow.mvp.BasePresenter;
import itbour.onetouchshow.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class VerticalproductContract {
    interface View extends BaseView {
        void removeWatermarkSuccess(String result);
        void removeWatermarkFail(String result);

        void copyandNewProducesuccess(String tmpId);

        void downLoadPrintSuccess(String path );
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
