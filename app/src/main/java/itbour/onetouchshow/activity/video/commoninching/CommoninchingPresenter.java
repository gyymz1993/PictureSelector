package itbour.onetouchshow.activity.video.commoninching;

import com.google.gson.GsonBuilder;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;

import java.util.HashMap;

import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.bean.inching.DocContentV10Bean;
import itbour.onetouchshow.gsonanalysis.InchingContentRules;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.utils.L_;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CommoninchingPresenter extends BasePresenterImpl<CommoninchingContract.View> implements CommoninchingContract.Presenter {

    /**
     * 当从竖屏模板进入微调界面
     *
     * @param id
     */
    public void onVerticalModelEnter(int id) {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringStringHashMap.put("id", id);
        stringStringHashMap.put("isTmpl", 1);
        stringStringHashMap.put("userId", AppConst.TEST_USERID);

        HttpUtils.getInstance().executeGet(AppConfig.GET_DOC_CONTENT_V10, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView != null) {
                    L_.i(exception);
                }
            }

            @Override
            protected void onSuccess(String response) {
                L_.i("SET_CANCEL_MODEL_COLLECT_V10===" + response + "id===" + id);
                if (mvpView != null) {

                    DocContentV10Bean docContentV10Bean = new GsonBuilder().registerTypeAdapter
                            (DocContentV10Bean.class, new InchingContentRules()).create()
                            .fromJson(response, DocContentV10Bean.class);
                    L_.i(docContentV10Bean);



                }
            }
        });
    }
}
