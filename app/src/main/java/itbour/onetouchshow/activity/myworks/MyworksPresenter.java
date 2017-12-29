package itbour.onetouchshow.activity.myworks;

import android.view.View;

import com.google.gson.Gson;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;

import java.util.HashMap;

import itbour.onetouchshow.App;
import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.bean.MyWorksBean;
import itbour.onetouchshow.bean.NoDataResult;
import itbour.onetouchshow.custom.DialogUtils;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MyworksPresenter extends BasePresenterImpl<MyworksContract.View> implements MyworksContract.Presenter {


    public void loadMyWorksList(MyworksActivity activity) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringObjectHashMap.put("userId", App.getUserId());
        //pageNum	是
        stringObjectHashMap.put("pageNum", activity.mPageIndex);
        //页码 pageSize
        stringObjectHashMap.put("pageSize", AppConst.PAGE_SIZE);
        //   页容量
        stringObjectHashMap.put("withTotalPage", 1);
        HttpUtils.getInstance().executeGet(AppConfig.GETUSERWORKLIST_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                T_.showToastWhendebug(exception);
                L_.e(exception);
            }

            @Override
            protected void onSuccess(String response) {
                NoDataResult noDataResult = new Gson().fromJson(response, NoDataResult.class);
                if (noDataResult == null || noDataResult.getList().size() == 0) {
                    mvpView.loadFaild(AppConst.NoData);
                } else {
                    if (mvpView != null) {
                        mvpView.loadSucceed(response);
                    }
                }
            }
        });
    }

    public void deleteWork(MyworksActivity activity, MyWorksBean.ListBean listBean) {
        //listBean.getWatermarkPay()
        if (listBean.getWatermarkPay() == 1) {
            //T_.showToastReal("已经付款");
            DialogUtils.getInstance(new DialogUtils.Builder().setTitle(" ").
                    setCancletext("取消").
                    setCancleListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtils.getInstance().dismiss();
                        }
                    }).setConfirmText("删除").setConfirmListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtils.getInstance().dismiss();
                    doRemoveWork(listBean);
                }
            }).setContext("你以为该作品去除水印,删除后将无法恢复。").setPlatform(DialogUtils.DialogPlatform.TWO_BTN)).builder(activity);
        } else {
            DialogUtils.getInstance(new DialogUtils.Builder().setTitle(" ").
                    setCancletext("取消").
                    setCancleListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtils.getInstance().dismiss();
                        }
                    }).setConfirmText("删除").setConfirmListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtils.getInstance().dismiss();
                    doRemoveWork(listBean);
                }
            }).setContext("是否删除该作品?").setPlatform(DialogUtils.DialogPlatform.TWO_BTN)).builder(activity);
        }

    }

    private void doRemoveWork(MyWorksBean.ListBean listBean) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringObjectHashMap.put("userId", App.getUserId());
        stringObjectHashMap.put("docId", listBean.getId());
        HttpUtils.getInstance().executeGet(AppConfig.REMOVEUSERDOC_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                L_.e(exception);
                T_.showToastReal(exception);
            }

            @Override
            protected void onSuccess(String response) {
                mvpView.deleteSuccess(listBean);
            }
        });
    }


    public void reNameWork(MyworksActivity activity, MyWorksBean.ListBean listBean) {



        DialogUtils.getInstance(new DialogUtils.Builder().setTitle("修改作品名").
                setCancletext("取消").setContext(listBean.getName()).
                setCancleListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.getInstance().dismissClean();
                    }
                }).setConfirmText("确认").setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.getInstance().dismiss();
                doReNameWork(listBean, DialogUtils.getInstance().getIdEdContent());
            }
        }).setPlatform(DialogUtils.DialogPlatform.ONE_EDIT)).builder(activity);

    }

    private void doReNameWork(MyWorksBean.ListBean listBean, String name) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringObjectHashMap.put("userId", App.getUserId());
        L_.e(" listBean.getId()" + listBean.getId());
        stringObjectHashMap.put("docId", listBean.getId());
        stringObjectHashMap.put("name", name);
        HttpUtils.getInstance().executeGet(AppConfig.RENAMEUSERDOC_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                L_.e(exception);
                T_.showToastReal(exception);
            }

            @Override
            protected void onSuccess(String response) {
                listBean.setName(name);
                mvpView.reNameSuccess(listBean);
                L_.e(response);
            }
        });
    }
}
