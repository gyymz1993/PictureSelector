package itbour.onetouchshow.fragment.modelpreview;

import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;

import java.util.HashMap;

import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.bean.DesignListBean;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.utils.L_;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ModelpreviewPresenter extends BasePresenterImpl<ModelpreviewContract.View> implements ModelpreviewContract.Presenter {
    /**
     * 获取当前页的显示数据
     *
     * @param data
     */
    public void loadThisPageInfo(DesignListBean.ListBean data) {
        int id = data.getId();
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", 610);
        stringStringHashMap.put("id", id);
        HttpUtils.getInstance().executeGet(AppConfig.GET_VERTICAL_MODEL_PREVIEW_V10, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView != null) {
                    mvpView.loadFaild(exception);
                }
            }

            @Override
            protected void onSuccess(String response) {
                L_.i("response===" + response);
                if (mvpView != null) {
                    mvpView.loadSucceed(response);
                }
            }
        });
    }


}
