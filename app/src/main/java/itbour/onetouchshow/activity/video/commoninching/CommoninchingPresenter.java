package itbour.onetouchshow.activity.video.commoninching;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lsjr.callback.EncryBeanCallBack;
import com.lsjr.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itbour.onetouchshow.App;
import itbour.onetouchshow.AppConfig;
import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.R;
import itbour.onetouchshow.bean.HomeDetailslBean;
import itbour.onetouchshow.bean.canvas.PosBean;
import itbour.onetouchshow.bean.inching.DocContentV10Bean;
import itbour.onetouchshow.bean.inching.noppt.CreateDocSuccessReturnV10Bean;
import itbour.onetouchshow.bean.inching.noppt.InchingAllOutLineV10Bean;
import itbour.onetouchshow.bean.inching.noppt.InchingCoreRootNoPPTV10Bean;
import itbour.onetouchshow.bean.inching.noppt.InchingDocStringNoPPTV10Bean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineAllTypeV10Bean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineImgBean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineTableBean;
import itbour.onetouchshow.bean.inching.noppt.InchingOutLineTextOrShapeBean;
import itbour.onetouchshow.gsonanalysis.CoreJsonToObjNoPPTRules;
import itbour.onetouchshow.gsonanalysis.CoreObjToJsonNoPPTRules;
import itbour.onetouchshow.gsonanalysis.DocStringJsonToObjCommonRules;
import itbour.onetouchshow.gsonanalysis.DocStringObjToJsonCommonRules;
import itbour.onetouchshow.gsonanalysis.InchingOutLineAnalysisRules;
import itbour.onetouchshow.mvp.BasePresenterImpl;
import itbour.onetouchshow.platform.DataPlatform;
import itbour.onetouchshow.platform.TypePlatform;
import itbour.onetouchshow.singlecase.InchingDataSingleCase;
import itbour.onetouchshow.utils.CommonUtils;
import itbour.onetouchshow.utils.FineTuneRender;
import itbour.onetouchshow.utils.L_;
import itbour.onetouchshow.utils.T_;
import itbour.onetouchshow.utils.UIUtils;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CommoninchingPresenter extends BasePresenterImpl<CommoninchingContract.View> implements CommoninchingContract.Presenter {

    private DocContentV10Bean docContentV10Bean;
    //docId
    private int id;
    private int nowPageIndex;
    private int pageCount;

    /**
     * 当从竖屏模板进入微调界面
     *
     * @param id
     */
    public void onVerticalModelEnter(int id, int tag) {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringStringHashMap.put("id", id);
        //1   模板
        stringStringHashMap.put("isTmpl", tag);
        stringStringHashMap.put("userId", App.getUserId());

        HttpUtils.getInstance().executeGet(AppConfig.GET_DOC_CONTENT_V10, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView != null) {
                    L_.i(exception);
                    mvpView.loadFaild(exception);

                }
            }

            @Override
            protected void onSuccess(String response) {

                L_.i("SET_CANCEL_MODEL_COLLECT_V10===" + response + "id===" + id);
                if (mvpView != null) {

                    docContentV10Bean = new GsonBuilder().registerTypeAdapter
                            (DocContentV10Bean.class, new DocStringJsonToObjCommonRules()).create()
                            .fromJson(response, DocContentV10Bean.class);
                    L_.i(docContentV10Bean);
                    InchingDocStringNoPPTV10Bean noPptCoreData = docContentV10Bean.getNoPptCoreData();
                    InchingDataSingleCase.INSTANCE.setInstance(noPptCoreData);
                    initQuickEditFineTune();
                }
            }
        });
    }


    /**
     * 核心数据拷贝
     */
    public void getCopyDocStringAndNew(int docId) {
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
                setId(Integer.valueOf(orderid));
                L_.i("SET_CANCEL_MODEL_COLLECT_V10===" + response + "id===" + id);
                if (mvpView != null) {
                    docContentV10Bean = new GsonBuilder().registerTypeAdapter
                            (DocContentV10Bean.class, new DocStringJsonToObjCommonRules()).create()
                            .fromJson(response, DocContentV10Bean.class);
                    L_.i(docContentV10Bean);
                    InchingDocStringNoPPTV10Bean noPptCoreData = docContentV10Bean.getNoPptCoreData();
                    InchingDataSingleCase.INSTANCE.setInstance(noPptCoreData);
                    initQuickEditFineTune();
                }
            }
        });

    }


    /**
     * 当从竖屏视频 进入微调界面
     *
     * @param id
     */
    public void onVerticalVideoEnter(int id) {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringStringHashMap.put("docId", id);
        stringStringHashMap.put("userId", App.getUserId());
        HttpUtils.getInstance().executeGet(AppConfig.GETVIDEODOCBGINFO_V1_0, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView != null) {
                    L_.i(exception);
                    mvpView.loadFaild(exception);
                }
            }

            @Override
            protected void onSuccess(String response) {

                L_.i("SET_CANCEL_MODEL_COLLECT_V10===" + response + "id===" + id);
                if (mvpView != null) {
                    InchingDocStringNoPPTV10Bean inchingCoreNoPPTV10Bean =
                            new GsonBuilder().registerTypeAdapter(InchingCoreRootNoPPTV10Bean.class, new CoreJsonToObjNoPPTRules()).create()
                                    .fromJson(response, InchingDocStringNoPPTV10Bean.class);
                    L_.e(inchingCoreNoPPTV10Bean + "");
                    //InchingDocStringNoPPTV10Bean.CoreStructureBean coreStructure = inchingCoreNoPPTV10Bean.getCoreStructure();
                    //InchingDocStringNoPPTV10Bean noPptCoreData = docContentV10Bean.getNoPptCoreData();
                    // InchingDataSingleCase.INSTANCE.setInstance(noPptCoreData);

                    // InchingDocStringNoPPTV10Bean noPptCoreData = docContentV10Bean.getNoPptCoreData();
                    InchingDataSingleCase.INSTANCE.setInstance(inchingCoreNoPPTV10Bean);
                    initQuickEditFineTune(inchingCoreNoPPTV10Bean);

                }
            }
        });
    }

    /***
     * 视频
     * */
    private void initQuickEditFineTune(InchingDocStringNoPPTV10Bean data) {
        //InchingDocStringNoPPTV10Bean data = InchingDataSingleCase.INSTANCE.getInstance();
        InchingDocStringNoPPTV10Bean.CoreStructureBean coreStructure = data.getCoreStructure();
        String tg = new GsonBuilder()
                .registerTypeAdapter(InchingCoreRootNoPPTV10Bean.class, new CoreObjToJsonNoPPTRules())
                .create().toJson(coreStructure);
        //视频不可编辑区域
        PosBean videoFrame = data.getVideoFrame();
        mvpView.createEnabledEreaData(videoFrame);

        if (data != null && data.getCoreStructure() != null && data.getTextObjects() != null) {
            String textObjects = data.getTextObjects();
            //计算宽高比
            int pageHeight = data.getPageHeight();
            int pageWidth = data.getPageWidth();
            int bleedHeight = data.getBleedHeight();
            int bleedWidth = data.getBleedWidth();
            //
            L_.i("pageHeight===" + pageHeight + "pageWidth===" + pageWidth + "textObjects===" + textObjects);
            float scale = getScale(pageWidth, pageHeight, bleedWidth, bleedHeight);
            InchingDataSingleCase.INSTANCE.setNowScale(scale);
            //初始化微调库
            long initFineTuneSuccess = FineTuneRender.initFineTuneForJava(scale, textObjects, tg);

            HomeDetailslBean.TextConfigBean textConfig = InchingDataSingleCase.INSTANCE.getTextConfig();
            String defaultText = textConfig.getDefaultText();
            //初始化文字默认配置
            FineTuneRender.initDefalutTextForJava(defaultText);
            //初始化默认文字
            FineTuneRender.initTextConfigForJava(textConfig.getMinFs(), textConfig.getMaxFs()
                    , textConfig.getMinLs(), textConfig.getMaxLs()
                    , textConfig.getMinCs(), textConfig.getMaxCs());
            if (initFineTuneSuccess == 0) {
                pageCount = 0;

                //如果 版本号小于3 不支持加字和加图
                boolean showAddTextImg = CommonUtils.jugleIsSupportAddTextAndImg(data);
                mvpView.setAddTextImgVisableStates(showAddTextImg);
                mvpView.jugleIsNeedShowPageSwitch(pageCount);

                //设置出血区域
//                FineTuneRender.initBleedGapForJava(bleedWidth * 2, bleedHeight * 2
//                        , bleedWidth * 2, bleedHeight * 2);
                //设置出血区域
                FineTuneRender.initBleedGapForJava(0.0f, 0.0f
                        , 0.0f, 0.0f);


                initSetPageIndexData(nowPageIndex);
            }
        }
    }


    /**
     * 修改作品
     */
    public void updataProduct() {
        if (docContentV10Bean != null) {
            //更新微调库中数据
            String objectsForSaveForJava = FineTuneRender.getObjectsForSaveForJava();
            docContentV10Bean.getNoPptCoreData().setTextObjects(objectsForSaveForJava);

            String dataJson = new GsonBuilder().registerTypeAdapter
                    (DocContentV10Bean.class, new DocStringObjToJsonCommonRules())
                    .create().toJson(docContentV10Bean);
            JSONObject jsonObject = JSON.parseObject(dataJson);
            String docString = jsonObject.getString("docString");

            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("device", AppConst.ANDROID_DEVICE);
            stringStringHashMap.put("docId", id);
            stringStringHashMap.put("docString", docString);
            stringStringHashMap.put("userId", App.getUserId());

            HttpUtils.getInstance().executePost(AppConfig.UPDATEUSERDOC_V1_0, stringStringHashMap, new EncryBeanCallBack() {

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

                    L_.i("CREATE_DOC_V10===" + response + "id===" + id);
                    CreateDocSuccessReturnV10Bean createDocSuccessReturnV10Bean =
                            new Gson().fromJson(response, CreateDocSuccessReturnV10Bean.class);

                    if (mvpView != null) {
                        int docId = createDocSuccessReturnV10Bean.getDocId();
                        int opType = createDocSuccessReturnV10Bean.getOpType();
                        if (opType != DataPlatform.ORTYPE_PPT) {
                            mvpView.toProductPreview(docId, opType);
                        }

                    }
                }
            });
        }
    }


    public Integer getIsVideo() {
        if (docContentV10Bean == null) {
            return 1;
        }
        int opType = docContentV10Bean.getOpType();
        if (opType == TypePlatform.OBJECT_TYPE_VIDEO) {
            return 1;
        } else
//        if (opType == TypePlatform.OBJECT_TYPE_DESIGN || opType == TypePlatform.OBJECT_TYPE_PRINT)
        {
            return 0;
        }
    }

    /**
     * 修改作品
     */
    public void updataVideoBgProduct() {
        if (docContentV10Bean == null) {
            //视频修改和作品数据不一致    伪造一个假数据
            docContentV10Bean = new DocContentV10Bean();
            InchingDocStringNoPPTV10Bean noPPTV10Bean = InchingDataSingleCase.INSTANCE.getInstance();
            docContentV10Bean.setNoPptCoreData(noPPTV10Bean);
        }
        if (docContentV10Bean != null) {
            //更新微调库中数据
            String objectsForSaveForJava = FineTuneRender.getObjectsForSaveForJava();
            String tgForSaveForJava = FineTuneRender.getTgForSaveForJava();
            InchingDocStringNoPPTV10Bean.CoreStructureBean inchingCoreNoPPTV10Bean =
                    new GsonBuilder().registerTypeAdapter(InchingCoreRootNoPPTV10Bean.class, new CoreJsonToObjNoPPTRules()).create()
                            .fromJson(tgForSaveForJava, InchingDocStringNoPPTV10Bean.CoreStructureBean.class);

            docContentV10Bean.getNoPptCoreData().setCoreStructure(inchingCoreNoPPTV10Bean);
            docContentV10Bean.getNoPptCoreData().setTextObjects(objectsForSaveForJava);

            String dataJson = new GsonBuilder().registerTypeAdapter
                    (DocContentV10Bean.class, new DocStringObjToJsonCommonRules())
                    .create().toJson(docContentV10Bean);
            JSONObject jsonObject = JSON.parseObject(dataJson);
            String docString = jsonObject.getString("docString");

            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("device", AppConst.ANDROID_DEVICE);
            stringStringHashMap.put("docId", id);
            L_.e("999999" + id);
            L_.e("bgString" + docString);
            stringStringHashMap.put("bgString", docString);
            stringStringHashMap.put("userId", App.getUserId());
            HttpUtils.getInstance().executePost(AppConfig.UPDATEVIDEODOCBG_V1_0, stringStringHashMap, new EncryBeanCallBack() {

                @Override
                protected void onXError(String exception) {
                    if (mvpView != null) {
                        L_.i(exception);
                    }
                }

                @Override
                protected void onSuccess(String response) {
                    mvpView.toUpVideoBgSuccess(response);
                }
            });
        }
    }


    /**
     * 创建一个新文档并跳转到作品预览
     */
    public void createProduct() {

        if (docContentV10Bean != null) {
            //更新微调库中数据
            String objectsForSaveForJava = FineTuneRender.getObjectsForSaveForJava();
            String tgForSaveForJava = FineTuneRender.getTgForSaveForJava();

            InchingDocStringNoPPTV10Bean.CoreStructureBean inchingCoreNoPPTV10Bean =
                    new GsonBuilder().registerTypeAdapter(InchingCoreRootNoPPTV10Bean.class, new CoreJsonToObjNoPPTRules()).create()
                            .fromJson(tgForSaveForJava, InchingDocStringNoPPTV10Bean.CoreStructureBean.class);


            docContentV10Bean.getNoPptCoreData().setCoreStructure(inchingCoreNoPPTV10Bean);
            docContentV10Bean.getNoPptCoreData().setTextObjects(objectsForSaveForJava);

            String dataJson = new GsonBuilder().registerTypeAdapter
                    (DocContentV10Bean.class, new DocStringObjToJsonCommonRules())
                    .create().toJson(docContentV10Bean);
            JSONObject jsonObject = JSON.parseObject(dataJson);
            String docString = jsonObject.getString("docString");

            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("device", AppConst.ANDROID_DEVICE);
            stringStringHashMap.put("tmplId", id);   //440
            stringStringHashMap.put("docString", docString);
            stringStringHashMap.put("userId", App.getUserId());   //35

            HttpUtils.getInstance().executePost(AppConfig.CREATE_DOC_V10, stringStringHashMap, new EncryBeanCallBack() {

                @Override
                protected void onXError(String exception) {
                    if (mvpView != null) {
                        L_.i(exception);
                    }
                }

                @Override
                protected void onSuccess(String response) {

                    L_.i("CREATE_DOC_V10===" + response + "id===" + id);
                    CreateDocSuccessReturnV10Bean createDocSuccessReturnV10Bean =
                            new Gson().fromJson(response, CreateDocSuccessReturnV10Bean.class);

                    if (mvpView != null) {
                        int docId = createDocSuccessReturnV10Bean.getDocId();
                        int opType = createDocSuccessReturnV10Bean.getOpType();
                        if (opType != DataPlatform.ORTYPE_PPT) {
                            mvpView.toProductPreview(docId, opType);
                        }

                    }
                }
            });
        }
    }

    /**
     * 从act传过来id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 有一页以上的情况 切换前一页
     */
    public void previousPage() {
        if (nowPageIndex == 0) {
            mvpView.showToast("已是第一页");
        } else if (nowPageIndex - 1 >= 0) {
            nowPageIndex = nowPageIndex - 1;
            initSetPageIndexData(nowPageIndex);
        }

    }

    public void nextPage() {
        if (nowPageIndex == pageCount - 1) {
            mvpView.showToast("已是最后一页");

        } else if (nowPageIndex + 1 <= pageCount - 1) {
            nowPageIndex = nowPageIndex + 1;
            initSetPageIndexData(nowPageIndex);

        }
    }

    private void initQuickEditFineTune() {
        InchingDocStringNoPPTV10Bean data = InchingDataSingleCase.INSTANCE.getInstance();

        InchingDocStringNoPPTV10Bean.CoreStructureBean coreStructure = data.getCoreStructure();
        String tg = new GsonBuilder()
                .registerTypeAdapter(InchingCoreRootNoPPTV10Bean.class, new CoreObjToJsonNoPPTRules())
                .create().toJson(coreStructure);


        if (data != null && data.getCoreStructure() != null && data.getTextObjects() != null) {
            String textObjects = data.getTextObjects();
            //计算宽高比
            int pageHeight = data.getPageHeight();
            int pageWidth = data.getPageWidth();
            int bleedHeight = data.getBleedHeight();
            int bleedWidth = data.getBleedWidth();
            //
            L_.i("pageHeight===" + pageHeight + "pageWidth===" + pageWidth + "textObjects===" + textObjects);
            float scale = getScale(pageWidth, pageHeight, bleedWidth, bleedHeight);
            //初始化微调库
            long initFineTuneSuccess = FineTuneRender.initFineTuneForJava(scale, textObjects, tg);

            InchingDataSingleCase.INSTANCE.setNowScale(scale);


            HomeDetailslBean.TextConfigBean textConfig = InchingDataSingleCase.INSTANCE.getTextConfig();
            String defaultText = textConfig.getDefaultText();
            //初始化文字默认配置
            FineTuneRender.initDefalutTextForJava(defaultText);
            //初始化默认文字
            FineTuneRender.initTextConfigForJava(textConfig.getMinFs(), textConfig.getMaxFs()
                    , textConfig.getMinLs(), textConfig.getMaxLs()
                    , textConfig.getMinCs(), textConfig.getMaxCs());
            if (initFineTuneSuccess == 0) {
                pageCount = data.getPageCount();

                //如果 版本号小于3 不支持加字和加图
                boolean showAddTextImg = CommonUtils.jugleIsSupportAddTextAndImg(data);
                mvpView.setAddTextImgVisableStates(showAddTextImg);
                mvpView.jugleIsNeedShowPageSwitch(pageCount);

                //设置出血区域
//                FineTuneRender.initBleedGapForJava(bleedWidth * 2, bleedHeight * 2
//                        , bleedWidth * 2, bleedHeight * 2);

                FineTuneRender.initBleedGapForJava(0.0f, 0.0f
                        , 0.0f, 0.0f);
                initSetPageIndexData(nowPageIndex);
            }
        }
    }

    public float getScale(int pageWidth, int pageHeight, int bleedWidth, int bleedHeight) {
        int sw = UIUtils.WHD()[0];
        //计算控制图标的宽高

        Bitmap bitmap = BitmapFactory.decodeResource(UIUtils.getResource(), R.drawable.iv_inching_control);
        int controller_width = bitmap.getWidth();
        int controller_height = bitmap.getHeight();
        L_.i("controller_width===" + controller_width + "controller_height===" + controller_height);
        //根宽为屏幕宽减去控制图标的宽
        int rootWith = sw - controller_width;
        //高度按宽高比计算
        int rootHeight = (int) Math.ceil(rootWith * (pageHeight * 1.0f / pageWidth));

        //计算出缩放比
        float scale = rootWith * 1.0f / pageWidth;

        //计算缩放比后的出血区域
        int bw = (int) (bleedWidth * scale);
        int bh = (int) (bleedHeight * scale);

//        mvpView.setInchingBaseAreaParams(controller_width, controller_height, rootWith, rootHeight, bw, bh);
        mvpView.setInchingBaseAreaParams(controller_width, controller_height, rootWith, rootHeight, 0, 0);


        return scale;

    }

    //初始化 重置 上一页 下一页会调用
    public void initSetPageIndexData(int nowPageIndex) {
        mvpView.clearNowPageChild();

        //请求文字轮廓
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", AppConst.ANDROID_DEVICE);
        String texts = FineTuneRender.getPageTextPropertyForJava(nowPageIndex, -1, 1);

        stringStringHashMap.put("texts", texts);

        HttpUtils.getInstance().executePost(AppConfig.GET_CONTOUROF_TEXTS_V10, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView != null) {
                    L_.i(exception);
                    mvpView.loadFaild(exception);
                }
            }

            @Override
            protected void onSuccess(String response) {
                L_.i("GET_CONTOUROF_TEXTS_V10===" + response + "id===" + id);
                if (mvpView != null) {
                    analysisOutLine(response);
                    mvpView.loadSucceed(response);
                }
            }
        });


    }

    /**
     * 解析文字轮廓
     */
    public void analysisOutLine(String response) {
        if (mvpView != null) {
            InchingAllOutLineV10Bean data =
                    new Gson().fromJson(response, InchingAllOutLineV10Bean.class);

            List<InchingAllOutLineV10Bean.TextsBean> texts = data.getTexts();

            String textsJson = new Gson().toJson(texts);
            //初始化当前页的文字轮廓数据 这是一个json类型
            long getTextsIsSuccess = FineTuneRender.changePageTextContourForJava(nowPageIndex, textsJson);
            if (getTextsIsSuccess == 0) {
                String paths = FineTuneRender.getObjectsForAppForJava(nowPageIndex, -1);


                List<InchingOutLineAllTypeV10Bean> stringList = new GsonBuilder()
                        .registerTypeAdapter(InchingOutLineAllTypeV10Bean.class, new InchingOutLineAnalysisRules())
                        .create().fromJson(paths, new TypeToken<List<InchingOutLineAllTypeV10Bean>>() {
                        }.getType());

                L_.i(stringList);
                mvpView.setNowPageIndex(nowPageIndex);
                for (int i = 0; i < stringList.size(); i++) {
                    Object o = stringList.get(i);
                    if (o instanceof InchingOutLineImgBean) {
                        addInchingImgView((InchingOutLineImgBean) o);
                    } else if (o instanceof InchingOutLineTextOrShapeBean) {
                        addInchingTextOrShapeView((InchingOutLineTextOrShapeBean) o);
                    } else if (o instanceof InchingOutLineTableBean) {
                        addInchingTableView((InchingOutLineTableBean) o);
                    }
                }

                mvpView.addMaskAndGridView();

            }
        }
    }


    public void addInchingTableView(InchingOutLineTableBean data) {
        mvpView.createTable(data);
    }

    public void addInchingTextOrShapeView(InchingOutLineTextOrShapeBean data) {
        mvpView.createTextOrShape(data);
    }

    public void addInchingImgView(InchingOutLineImgBean data) {
        int editable = data.getEditable();
        String source = data.getSource();
        if (source != null && !source.equals("") && source.length() > 0) {

            if (editable == 0) {
                //不可操作的图片 仅仅是创建一个图片 没有操作
//                mvpView.createStubImgView(data);
                mvpView.createOperaImage(data);
            } else {
                //可操作的图片
                //创建一个图片控件
                mvpView.createOperaImage(data);
            }


        }

    }


    /**
     * 改字体的时候传入 修改字体后的texts
     *
     * @param id
     * @param texts
     * @param inchingTextviewType
     */
    public void updateInchingView(int id, String texts, int inchingTextviewType) {
        //请求文字轮廓
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("device", AppConst.ANDROID_DEVICE);
        stringStringHashMap.put("texts", texts);

        HttpUtils.getInstance().executePost(AppConfig.GET_CONTOUROF_TEXTS_V10, stringStringHashMap, new EncryBeanCallBack() {
            @Override
            protected void onXError(String exception) {
                if (mvpView != null) {
                    L_.i(exception);
                }
            }

            @Override
            protected void onSuccess(String response) {
                L_.i("GET_CONTOUROF_TEXTS_V10===" + response + "id===" + id);
                if (mvpView != null) {

                    InchingAllOutLineV10Bean inchingContourOfTextsBean = new Gson().fromJson(response, InchingAllOutLineV10Bean.class);
                    List<InchingAllOutLineV10Bean.TextsBean> texts = inchingContourOfTextsBean.getTexts();
                    //把这个集合再转为String传入
                    String textsJson = new Gson().toJson(texts);

                    long initTextIsSuccess = FineTuneRender.changePageTextContourForJava(nowPageIndex, textsJson);
                    if (initTextIsSuccess == 0) {
                        String oneObjectForAppForJava = FineTuneRender.getObjectsForAppForJava(nowPageIndex, id);
                        if (inchingTextviewType == AppConst.INCHING_TEXTVIEW_TYPE) {

                            ArrayList<InchingOutLineTextOrShapeBean> inchingOutLineAllTypeV10Beans = CommonUtils.jsonToArrayList(oneObjectForAppForJava, InchingOutLineTextOrShapeBean.class);
                            InchingOutLineTextOrShapeBean data = inchingOutLineAllTypeV10Beans.get(0);
                            mvpView.upDateTextOrShapeView((InchingOutLineTextOrShapeBean) data);
                        } else if (inchingTextviewType == AppConst.INCHING_TABLEVIEW_TYPE) {

                            ArrayList<InchingOutLineTableBean> inchingOutLineAllTypeV10Beans = CommonUtils.jsonToArrayList(oneObjectForAppForJava, InchingOutLineTableBean.class);
                            InchingOutLineAllTypeV10Bean data = inchingOutLineAllTypeV10Beans.get(0);
                            mvpView.upDateTableView((InchingOutLineTableBean) data);
                        }


                    }


                }
            }
        });

    }

    public void deleteViewData(int nowPageIndex, int id) {


        long isSuccess = FineTuneRender.deleteObjectForJava(nowPageIndex, id);
        if (isSuccess == 0) {

            mvpView.deleteView();
        }
    }
}
