package itbour.onetouchshow.activity.search;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;

import java.util.HashMap;

import itbour.onetouchshow.App;
import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.bean.NoDataResult;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.utils.L_;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SearchPresenter extends BasePresenterImpl<SearchContract.View> implements SearchContract.Presenter {

    public void searchInfo(String keyWord) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", AppConst.ANDROID_DEVICE);
        // stringObjectHashMap.put("userId", App.getUserId());
        //pageNum	是
        stringObjectHashMap.put("pageNum", 0);
        //页码 pageSize
        stringObjectHashMap.put("pageSize", AppConst.PAGE_SIZE);
        //   页容量
        stringObjectHashMap.put("orderBy", 1);
        //L_.e( new Gson().toJson(AppConst.mSearchIds).toString());
        stringObjectHashMap.put("showTypes", new Gson().toJson(AppConst.mSearchIds));
        stringObjectHashMap.put("keyword", keyWord);
        HttpUtils.getInstance().executeGet(AppConfig.SEARCHTEMPLETE_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {

            }

            @Override
            protected void onSuccess(String response) {
                NoDataResult noDataResult = new Gson().fromJson(response, NoDataResult.class);
                if (noDataResult.getList().size() == 0 || noDataResult.getList() == null) {
                    mvpView.loadFaild("");
                } else {
                    mvpView.loadSucceed(response);
                }
                //L_.e(response);
            }
        });
    }


    public void searchInfo(String keyWord, int orderBy) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringObjectHashMap.put("userId", App.getUserId());
        //pageNum	是
        stringObjectHashMap.put("pageNum", 0);
        //页码 pageSize
        stringObjectHashMap.put("pageSize", AppConst.PAGE_SIZE);
        //   页容量
        stringObjectHashMap.put("orderBy", 1);
        //L_.e( new Gson().toJson(AppConst.mSearchIds).toString());
        stringObjectHashMap.put("showTypes", new Gson().toJson(AppConst.mSearchIds));
        stringObjectHashMap.put("keyword", keyWord);
        HttpUtils.getInstance().executeGet(AppConfig.SEARCHTEMPLETE_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {

            }

            @Override
            protected void onSuccess(String response) {
                NoDataResult noDataResult = new Gson().fromJson(response, NoDataResult.class);
                if (noDataResult.getList().size() == 0 || noDataResult.getList() == null) {
                    mvpView.loadFaild("");
                } else {
                    mvpView.loadSucceed(response);
                }
                L_.e(response);
            }
        });
    }

    public void lodaHotTag() {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", AppConst.ANDROID_DEVICE);
        HttpUtils.getInstance().executeGet(AppConfig.GETHOTSEARCH_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {

            }

            @Override
            protected void onSuccess(String response) {
                NoDataResult<String> noDataResult = new Gson().fromJson(response, NoDataResult.class);
                L_.e(noDataResult.getList().toString());

                if (mvpView != null) {
                    mvpView.loadHotTagSuccess(noDataResult.getList());
                }


//                if (noDataResult.getList().size()==0||noDataResult.getList()==null){
//                    mvpView.loadFaild("");
//                }else {
//                    mvpView.loadSucceed(response);
//                }
                // L_.e(response);
            }
        });
    }
}
