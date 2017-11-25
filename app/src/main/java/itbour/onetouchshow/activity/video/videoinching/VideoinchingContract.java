package itbour.onetouchshow.activity.video.videoinching;

import itbour.onetouchshow.mvp.BasePresenter;
import itbour.onetouchshow.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class VideoinchingContract {
    interface View extends BaseView {
        void closeKeyBroadAndPanel();
        void showKeyBroadAndPanel();
        void showKeyBroadHideOrderOperaPanel();

    }

    interface  Presenter extends BasePresenter<View> {
        void onInchingDeleteClick();

        void onInchingEnsureClick();

        void onInchingTextColorClick();

        void onInchingTextStyleClick();

        void onInchingTextTypeClick();
    }
}
