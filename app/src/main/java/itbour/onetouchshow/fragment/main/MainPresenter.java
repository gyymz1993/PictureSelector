package itbour.onetouchshow.fragment.main;


import com.google.gson.Gson;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;

import java.util.HashMap;
import java.util.List;

import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.fragment.designlist.DesignlistFragment;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.utils.L_;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {
    public void requestData() {

//        UIUtils.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mvpView.loadSucceed("成功");
//            }
//        },1000);

        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", 610);
        HttpUtils.getInstance().executeGet(AppConfig.GET_APPBASE_INFO_V10, stringStringHashMap, new EncryBeanCallBack() {
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


    public void getDetailsList(MainFragment mainFragment, List<Integer> ids) {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("setIds", new Gson().toJson(ids));
        //pageNum	是
        stringStringHashMap.put("pageNum", mainFragment.mPageIndex);
        //页码 pageSize
        stringStringHashMap.put("pageSize", AppConst.PAGE_SIZE);
        //   页容量
        stringStringHashMap.put("withTotalPage", 1);

        HttpUtils.getInstance().executeGet("/yjx/v1/document/getTmplSetContent_v1_0", stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView != null) {
                    mvpView.loadFaild(exception);
                }
            }

            @Override
            protected void onSuccess(String response) {
                //L_.i("response==="+response);
                if (mvpView != null) {
                    mvpView.loadSucceed(response);
                }
            }
        });


    }

}
