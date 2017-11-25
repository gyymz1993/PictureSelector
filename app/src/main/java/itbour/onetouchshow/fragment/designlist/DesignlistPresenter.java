package itbour.onetouchshow.fragment.designlist;

import com.google.gson.Gson;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;

import java.util.HashMap;
import java.util.List;

import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class DesignlistPresenter extends BasePresenterImpl<DesignlistContract.View> implements DesignlistContract.Presenter{

    public  void getDetailsList(DesignlistFragment designlistFragment,List<Integer> ids){
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("setIds", new Gson().toJson(ids));
        //pageNum	是
        stringStringHashMap.put("pageNum", designlistFragment.mPageIndex);
        //页码 pageSize
        stringStringHashMap.put("pageSize", AppConst.PAGE_SIZE);
        //   页容量
        stringStringHashMap.put("withTotalPage", 1);

        HttpUtils.getInstance().executeGet("/yjx/v1/document/getTmplSetContent_v1_0",stringStringHashMap , new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView!=null){
                    mvpView.loadFaild(exception);
                }
            }

            @Override
            protected void onSuccess(String response) {
                //L_.i("response==="+response);
                if (mvpView!=null){
                    mvpView.loadSucceed(response);
                }
            }
        });


    }
}
