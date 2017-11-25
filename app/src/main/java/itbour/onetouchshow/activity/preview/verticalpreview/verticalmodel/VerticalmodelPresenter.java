package itbour.onetouchshow.activity.preview.verticalpreview.verticalmodel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;

import java.util.HashMap;

import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.bean.DesignListBean;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.utils.L_;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VerticalmodelPresenter extends BasePresenterImpl<VerticalmodelContract.View> implements VerticalmodelContract.Presenter {
    /**
     * 获取当前是否收藏
     *
     * @param data
     */
    public void loadThisIsCollect(DesignListBean.ListBean data) {
        int id = data.getId();
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", 610);
        stringStringHashMap.put("tmplId", id);
        stringStringHashMap.put("userId", AppConst.TEST_USERID);
        HttpUtils.getInstance().executeGet(AppConfig.GET_THIS_MODEL_ISCOLLECT_V10, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView != null) {
                    mvpView.loadFaild(exception);
                }
            }

            @Override
            protected void onSuccess(String response) {
                L_.i("GET_VERTICAL_MODEL_PREVIEW_V10===" + response + "id===" + id);
                if (mvpView != null) {
                    JSONObject jsonObject = JSON.parseObject(response);
                    int code = jsonObject.getIntValue(AppConfig.ISCOLLECT);
                    if (code == 0) {
                        mvpView.unCollect();
                    } else {
                        mvpView.isCollect();
                    }

                }
            }
        });
    }


    public void collectOrCancalCollectThisModel(boolean collect, int id) {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", 610);
        stringStringHashMap.put("tmplId", id);
        stringStringHashMap.put("userId", AppConst.TEST_USERID);
        int collectTag = 0;
        if (collect) {
            collectTag = 0;
        } else {

            collectTag = 1;
        }
        stringStringHashMap.put("collect", collectTag);
        final int finalCollectTag = collectTag;
        HttpUtils.getInstance().executeGet(AppConfig.SET_CANCEL_MODEL_COLLECT_V10, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView != null) {
                    mvpView.collectFaild();

                }
            }

            @Override
            protected void onSuccess(String response) {
                L_.i("SET_CANCEL_MODEL_COLLECT_V10===" + response + "id===" + id);
                if (mvpView != null) {
                    if (finalCollectTag == 0) {
                        mvpView.unCollect();
                        mvpView.collectSuccess("取消收藏成功");
                    } else {
                        mvpView.isCollect();
                        mvpView.collectSuccess("收藏成功");

                    }
                }
            }
        });
    }
}
