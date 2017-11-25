package itbour.onetouchshow.activity.main;


import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;

import java.util.HashMap;

import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.utils.L_;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter{

    public void getHomeDetailsData(){
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", 610);
        HttpUtils.getInstance().executeGet(AppConfig.GET_APPBASE_INFO_V10,stringStringHashMap , new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView!=null){
                    mvpView.loadFaild(exception);
                }
            }

            @Override
            protected void onSuccess(String response) {
                L_.i("response==="+response);
                if (mvpView!=null){
                    mvpView.loadSucceed(response);
                }
            }
        });
    }
}
