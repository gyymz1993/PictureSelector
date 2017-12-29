package itbour.onetouchshow.activity.search;

import java.util.List;

import itbour.onetouchshow.bean.NoDataResult;
import itbour.onetouchshow.mvp.BasePresenter;
import itbour.onetouchshow.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SearchContract {
    interface View extends BaseView {

        void loadHotTagSuccess(List<String> noDataResult);

        void loadHotTagFail();

    }

    interface Presenter extends BasePresenter<View> {

    }
}
