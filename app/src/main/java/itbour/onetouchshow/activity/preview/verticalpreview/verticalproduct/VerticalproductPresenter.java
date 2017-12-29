package itbour.onetouchshow.activity.preview.verticalpreview.verticalproduct;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lsjr.callback.DownloadSubscriber;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;

import java.util.HashMap;

import itbour.onetouchshow.App;
import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.bean.DownloadVideoBean;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.utils.DateUtils;
import itbour.onetouchshow.utils.FileUtils;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VerticalproductPresenter extends BasePresenterImpl<VerticalproductContract.View> implements VerticalproductContract.Presenter {

    public void loadProductData(int docId) {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringStringHashMap.put("userId", App.getUserId());
        stringStringHashMap.put("docId", docId);

        HttpUtils.getInstance().executeGet(AppConfig.GET_DOC_PREVIEW_V10, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView != null) {
                    L_.i(exception);
                    T_.showToastReal(exception);
                    mvpView.loadFaild(exception);
                }
            }

            @Override
            protected void onSuccess(String response) {

                L_.i("loadProductData" + (response));
                if (mvpView != null) {
                    mvpView.loadSucceed(response);
                }

            }
        });
    }


    /**
     * 去水印
     *
     * @param docId
     */
    public void removeWatermark(String docId) {
        //L_.e(App.getLoginBean().getUserInfo().getUserId() + "");
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", 610);
        stringObjectHashMap.put("userId", App.getUserId());
        stringObjectHashMap.put("docId", docId);
        HttpUtils.getInstance().executeGet(AppConfig.REMOVEWATERMARK_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                L_.e(exception);
                if (mvpView!=null){
                    mvpView.removeWatermarkFail(exception);
                }

            }

            @Override
            protected void onSuccess(String response) {
                L_.e("response" + response);
                if (mvpView!=null){
                    mvpView.loadSucceed(response);
                }
                //mvpView.removeWaterLoadDocPreview(response);
            }
        });

    }


    /**
     * 核心数据拷贝
     */
    public void getCopyDocStringAndNew(String docId) {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        //  L_.e(videoBean.getId() + "");
        stringStringHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringStringHashMap.put("docId", docId);
        stringStringHashMap.put("userId", App.getUserId());
        HttpUtils.getInstance().executeGet(AppConfig.GETUSERDOCCOPYTMPL_V1_0, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                L_.e(exception);
                T_.showToastReal(exception);
            }

            @Override
            protected void onSuccess(String response) {
                L_.e(response);
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);
                // order no  61R45L09X95P6
                String orderid = jsonObject.getString("tmplId");

                if (mvpView!=null){
                    mvpView.copyandNewProducesuccess(orderid);
                }
                L_.e(orderid);
                // JSONObject jsonObject=new JSONObject(response);
            }
        });

    }

    public void getDownVideoUrl(String docId) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("device", 610);
        stringObjectHashMap.put("userId", App.getUserId());
        stringObjectHashMap.put("docId", docId);
        HttpUtils.getInstance().executeGet(AppConfig.GETDOCDOWNLOADURL_V1_0, stringObjectHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                T_.showToastReal(exception);

                if (mvpView!=null){
                    mvpView.loadFaild(exception);
                }
            }

            @Override
            protected void onSuccess(String response) {
                L_.e("response" + response);
                DownloadVideoBean downloadVideoBean = new Gson().fromJson(response, DownloadVideoBean.class);
                int size = downloadVideoBean.getDownloadUrls().size();
                for (int i = 0; i < size; i++) {

                    doDonwload(downloadVideoBean.getDownloadUrls().get(i), i+1, size);
                }
               /*
               * response{"downloadUrls":
               * ["http://itbour-generate.itbour.com/video/U299486/2017/12/14/212552705_wA1raO87Rlw5wceagVZl/0.mp4"],
               * "fileSize":388367,"docId":2228,"opType":2}
               * */
                // mvpView.deleteVideoBgSuccess(response);
                //mvpView.removeWaterLoadDocPreview(response);
            }
        });
    }

    public void doDonwload(String url, int index, int size) {
        // File outputfile = new File(UIUtils.getContext().getExternalCacheDir(), "output.mp4");

        String name = DateUtils.getYMD()+ FileUtils.getFileName(url);
        //先查找本地   必须保证在我们自己的目录下打开才能找到  如果自己发出相当于重新下载一次
//        boolean downLoad = true;
//        if (AppCache.getInstance().fileExists(AppCache.getInstance().mFilesDir, name)) {
//            downLoad = false;
//        }
        L_.e("下载文件" + name);
        HttpUtils.getInstance().downloadFile(url, new DownloadSubscriber(UIUtils.getContext(),
                null, name) {
            @Override
            public void onComplete(String path) {
                L_.e("文件保存路径：" + path);
                //保存图片后发送广播通知更新数据库

                if (index == size) {
                    if (mvpView!=null){
                        mvpView.downLoadPrintSuccess(path);
                    }

                }
            }

            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                int progress = (int) (bytesRead * 100 / contentLength);
                L_.e(progress + "% ");
            }

            @Override
            protected void onXError(String exception) {
                L_.e(exception + "% ");
                T_.showToastReal(exception);

                if (mvpView!=null){
                    mvpView.loadFaild(exception);
                }
            }
        });
    }
}
