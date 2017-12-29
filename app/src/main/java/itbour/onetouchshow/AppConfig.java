package itbour.onetouchshow;

/**
 * Created by onetouch on 2017/11/21.
 */

public class AppConfig {
    //通用字段
    public static final String ISCOLLECT = "isCollect";

    /**
     * 第三方登陆
     */
    //public final static String THIRD_lOGIN = "/yjx/v1/user/loginByThirdPlatform_v1_0";
    public final static String THIRD_lOGIN = "/yjx/v1/user/loginByThirdPlatform_v1_0_1";


    public final static String GET_CODE = "/yjx/v1/user/getMobileVerCode_v1_0";
    //验证码登录
    public final static String CODE_LOIGN = "/yjx/v1/user/loginByMobileCode_v1_0";
    // 微信登录
    public final static int YJX_THIRD_LOGIN_PLATFORM_WECHAT = 20;

    /**
     * 获取 APP 基础信息
     */
    public final static String GET_APPBASE_INFO_V10 = "/yjx/v1/sundry/getAppBaseInfo_v1_0";

    //获取模板预览
    public final static String GET_VERTICAL_MODEL_PREVIEW_V10 = "/yjx/v1/document/getTmplDetail_v1_0";

    //获取模板收藏状态
    public final static String GET_THIS_MODEL_ISCOLLECT_V10 = "/yjx/v1/document/getTmplCollectStatus_v1_0";

    //收藏或取消搜藏模板
    public final static String SET_CANCEL_MODEL_COLLECT_V10 = "/yjx/v1/document/setTmplCollectStatus_v1_0";

    //获取模板/作品核心数据
    public final static String GET_DOC_CONTENT_V10 = "/yjx/v1/document/getDocContent_v1_0";

    //获取视频 微调数据
    public final static String GETVIDEODOCBGINFO_V1_0 = "/yjx/v1/document/getVideoDocBgInfo_v1_0";

    //创建作品
    public final static String CREATE_DOC_V10 = "/yjx/v1/document/newUserDoc_v1_0";
    //获取作品预览 如果没排版 现在排版
    public final static String GET_DOC_PREVIEW_V10 = "/yjx/v1/document/getDocPreview_v1_0";
    //获取文字轮廓信息
    public final static String GET_CONTOUROF_TEXTS_V10 = "/yjx/v1/document/getContourOfTexts_v1_0";

    //创建去水印订单
    public final static String GET_CREATEWATERMARKORDER_V1_0 = "/yjx/v1/order/createWatermarkOrder_v1_0";

    /*获取支付参数*/

    public final static String GETORDERPAYPARAM_V1_0 = "/yjx/v1/order/getOrderPayParam_v1_0";

    /*获取列表数据*/
    public final static String GETTMPLSETCONTENT_V1_0 = "/yjx/v1/document/getTmplSetContent_v1_0";


    /*获取收藏列表数据*/
    public final static String GETUSERCOLLECTLIST_V1_0 = "/yjx/v1/user/getUserCollectList_v1_0";

    /*获取作品列表数据*/
    public final static String GETUSERWORKLIST_V1_0 = "/yjx/v1/user/getUserWorkList_v1_0";
    /*获取作品列表数据*/
    public final static String REMOVEUSERDOC_V1_0 = "/yjx/v1/user/removeUserDoc_v1_0";

    /*修改名称*/
    public final static String RENAMEUSERDOC_V1_0 = "/yjx/v1/user/renameUserDoc_v1_0";

    /*用户数据中心*/
    public final static String GETPERSONALCENTERINFO_V1_0 = "/yjx/v1/user/getPersonalCenterInfo_v1_0";

    /*用户详情*/
    public final static String GETUSERDETAILINFO_V1_0 = "/yjx/v1/user/getUserDetailInfo_v1_0";

    /*得到用户订单*/
    public final static String GETUSERORDERLIST_V1_1 = "/yjx/v1_0_0/order/getUserOrderList_v1_1";

    /*预支付订单 */
    public final static String GETPREWATERMARKORDERINFO_V1_0 = "/yjx/v1_0_0/order/getPreWatermarkOrderInfo_v1_0";


    /*用户搜索*/
    public final static String SEARCHTEMPLETE_V1_0 = "/yjx/v1/sundry/searchTemplete_v1_0";

    /*用户资料修改*/
    public final static String UPDATEUSERINFO_V1_0 = "/yjx/v1/user/updateUserInfo_v1_0";

    /*获取OSS*/
    public final static String GETOSSSIGN_V1_0 = "/yjx/v1/sundry/getOssSign_v1_0";

    /*获取作品核心数据*/
    public final static String GETUSERDOCCOPYTMPL_V1_0 = "/yjx/v1/document/getUserDocCopyTmpl_v1_0";


    /*获取热门搜索数据*/
    public final static String GETHOTSEARCH_V1_0 = "/yjx/v1/sundry/getHotSearch_v1_0";

    /* 获取作品预览 */
    public final static String GETDOCPREVIEW_V1_0 = "/yjx/v1/document/getDocPreview_v1_0";

    /* 设置视频背景 */
    public final static String SETVIDEOBG_V1_0 = "/yjx/v1/document/setVideoBG_v1_0";


    /* 获取视频可用背景列表 */
    public final static String GETVIDEOUSERDOCBGLIST_V1_0 = "/yjx/v1/document/getVideoUserDocBgList_v1_0";


    /*上传阿里云路径*/
    public final static String UPLOAD_ALYUN = "http://itbour-user.oss-cn-hangzhou.aliyuncs.com/";


    /*创建作品*/
    public final static String NEWUSERDOC_V1_0 = "/yjx/v1/document/newUserDoc_v1_0";
    //修改作品
    public final static String UPDATE_USERDOC_V1_0 = "/yjx/v1/document/updateUserDoc_v1_0";


    /*去水印*/
    public final static String REMOVEWATERMARK_V1_0 = "/yjx/v1/document/removeWatermark_v1_0";


    /*AboutME*/
    public final static String ABOUT_ME_V1_0 = "/yjx/v1_0_0/user/we";

    /*用户协议*/
    public final static String USERAGREEMENT_V1_0 = "/yjx/v1_0_0/user/userAgreement";

    /*支付协议*/
    public final static String PAYAGREEMENT_V1_0 = "/yjx/v1_0_0/user/payAgreement";


    /*修改作品*/
    public final static String UPDATEUSERDOC_V1_0 = "/yjx/v1/document/updateUserDoc_v1_0";


    /*添加表单*/
    public final static String ADDFORM = "/yjx/v1_0_0/user/addForm";


    /*表单用户反馈*/
    public final static String FREEBACKINFO = "/yjx/v1_0_0/user/freebackInfo";

    /*清空视频背景*/
    public final static String CLEARVIDEOBG_V1_0 = "/yjx/v1/document/clearVideoBG_v1_0";


    /*用户意见反馈*/
    public final static String SENDFEEDBACK_V1_0 = "/yjx/v1/user/sendFeedback_v1_0";


    /*分享URL*/
    // public final static String SHARE = "http://192.168.31.42:3011/yjx/v1_0_0/outside/share?v=";

    /*分享URL*/
    public final static String SHARE = "https://yjxapphome.itbour.com/yjx/v1_0_0/outside/share?v=";

    /*获取下载地址*/
    public final static String GETDOCDOWNLOADURL_V1_0 = "/yjx/v1/document/getDocDownloadUrl_v1_0";

    /*修改视频背景*/
    public final static String UPDATEVIDEODOCBG_V1_0 = "/yjx/v1/document/updateVideoDocBg_v1_0";


     /*核心数据拷贝 */
    // public final static String GETUSERDOCCOPYTMPL_V1_0 = "/yjx/v1/document/getUserDocCopyTmpl_v1_0";

}

