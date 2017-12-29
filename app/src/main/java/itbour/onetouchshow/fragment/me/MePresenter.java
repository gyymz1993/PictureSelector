package itbour.onetouchshow.fragment.me;

import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;

import java.util.HashMap;

import itbour.onetouchshow.App;
import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.SpUtils;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MePresenter extends BasePresenterImpl<MeContract.View> implements MeContract.Presenter {

    /**
     * 用户中心作品数据
     */
    public void loadPersonalCenterInfo() {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringObjectHashMap.put("userId", App.getUserId());
        stringObjectHashMap.put("docCnt", 4);
        HttpUtils.getInstance().executeGet(AppConfig.GETPERSONALCENTERINFO_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                L_.e(exception);
            }

            @Override
            protected void onSuccess(String response) {
                L_.e(response);
                L_.e("我的作品列表" + response);
                if (mvpView!=null){
                    mvpView.loadSucceed(response);
                }
            }
        });
    }

    public void loadUserDetailInfo() {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringObjectHashMap.put("userId", App.getUserId());
        HttpUtils.getInstance().executeGet(AppConfig.GETUSERDETAILINFO_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                L_.e(exception);
            }

            @Override
            protected void onSuccess(String response) {
               // SpUtils.getInstance().saveString(AppConst.USER_INFO, response);
                mvpView.loaduserDetailSuccess(response);
            }
        });
    }


}
