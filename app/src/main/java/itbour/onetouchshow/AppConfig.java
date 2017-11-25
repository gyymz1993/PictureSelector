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
    public final static String THIRD_lOGIN = "/yjx/v1/user/loginByThirdPlatform_v1_0";
    public final static String GET_CODE = "/yjx/v1/user/getMobileVerCode_v1_0";
    //验证码登录
    public final static String CODE_LOIGN = "/yjx/v1/user/loginByMobileCode_v1_0";
    // 微信登录
    public final static int YJX_THIRD_LOGIN_PLATFORM_WECHAT = 20;

    /**
     *     获取 APP 基础信息
     */
    public final static String GET_APPBASE_INFO_V10 =  "/yjx/v1/sundry/getAppBaseInfo_v1_0";

    //获取模板预览
    public final static String GET_VERTICAL_MODEL_PREVIEW_V10 = "/yjx/v1/document/getTmplDetail_v1_0";

    //获取模板收藏状态
    public final static String GET_THIS_MODEL_ISCOLLECT_V10 = "/yjx/v1/document/getTmplCollectStatus_v1_0";

    //收藏或取消搜藏模板
    public final static  String SET_CANCEL_MODEL_COLLECT_V10 = "/yjx/v1/document/setTmplCollectStatus_v1_0";

    //获取模板/作品核心数据
    public final static String GET_DOC_CONTENT_V10 = "/yjx/v1/document/getDocContent_v1_0";


    //创建去水印订单
    public final static String GET_CREATEWATERMARKORDER_V1_0="/yjx/v1/order/createWatermarkOrder_v1_0";

    /*获取支付参数*/

    public final static String GETORDERPAYPARAM_V1_0="/yjx/v1/order/getOrderPayParam_v1_0";

}
