package itbour.onetouchshow;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @描述 全局常量类
 */
public class AppConst {

    public static final String PLAY_LARGE = "视频";
    //  业务分类 暂定如下: 1 竖屏类型(单页/印品), 2 视频类型, 3 ppt 类型
//    public static final int VERTICAL_ORTYPE = 1;
//    public static final int VIDIO_ORTYPE = 2;
//    public static final int PPT_ORTYPE = 3;
    //用于测试数据的userId
    public static final int TEST_USERID = 299188;

    public static final String ID = "ID";
    public static final String DOCID = "DOCID";
    public static final String OPTYPE = "OPTYPE";

    //android 端标识
    public static final int ANDROID_DEVICE = 610;
    //ct:609文字 603图形 602图片   文字和图形的结构基本相同 文字editable为1 图形603为  610代表表格
    public static final int INCHING_IMAGEVIEW_TYPE = 602;
    public static final int INCHING_TEXTVIEW_TYPE = 609;
    public static final int INCHING_SHAPEVIEW_TYPE = 603;
    public static final int INCHING_TABLEVIEW_TYPE = 610;

    public static final String ISLOGIN = "isLogin";
    //标记微调pw的点击事件
    public static final String PW_TV_DEL = "PW_TV_DEL";
    public static final String PW_TV_EDIT = "PW_TV_EDIT";
    public static final String PW_IMG_EDIT = "PW_IMG_EDIT";
    public static final String PW_IMG_DEL = "PW_IMG_DEL";
    public static final String PW_TAB_EDIT = "PW_TAB_EDIT";
    public static final String PW_TAB_DEL = "PW_TAB_DEL";
    public static final String PW_TAB_COLOR = "PW_TAB_COLOR";

    /*默认延迟加载时间*/
    public static final int DELAYED_TIME = 200;
    /*每页加载数据条数*/
    public static final int PAGE_SIZE = 10;


    /*搜索需要用到的ID*/
    public static Set<Integer> mSearchIds = new HashSet<>();

    public static Map<Integer,String> mAllType = new HashMap<>();

    /**
     * 记录微调文字操作控件的选中标记
     */
    public static final String WENBEN = "WENBEN";
    public static final String YANSE = "YANSE";
    public static final String ZITI = "ZITI";
    public static final String YANGSHI = "YANGSHI";
    //从文字操作跳转到颜色
    public static final String WAITYANSE = "WAITYANSE";
    public static final String WAITZITI = "WAITZITI";
    public static final String WAITYANGSHI = "WAITYANGSHI";

    public static final String CONTRL = "JSCallMobile";

    public static final String PORVIDER = "itbour.onetouchshow.fileprovider";

    public static final String OSSBUCKETNAME = "itbour-user";


    //支持表格的版本
    public final static int SUPPROT_TABLE_VERSION = 4;
    //开始支持relations的版本
    public final static int SUPPROT_RELATIONSLIST_VERSION = 3;

    //这两个字段 在取relations时 区别是取objid还是pageindex
    public static final int OBJIDTAG = 100;
    //这两个字段
    public static final int PAGEINDEXTAG = 200;
    public static final String REFRESH_TABLE = "REFRESH_TABLE";

    public static final String USER_INFO = "userInfor";

    //去相册
    public static final int TOPHOTO = 888;


    //返回取到了返回的图片路径
    public static final int CHANGE_IMG_RESULT = 889;
    //加图的路径
    public static final int TO_ADD_IMAGE = 890;
    //加图的返回路径
    public static final int TO_ADD_IMAGE_BACK = 891;
    //去相册传入的蒙板
    public static final String TOPHOTO_MASK = "TOPHOTO_MASK";
    //本地图片的路径
    public static final String LOCAL_PHOTO_PATH = "LOCAL_PHOTO_PATH";
    public static final String LOCAL_PHOTO_PATH_NAME = "LOCAL_PHOTO_PATH_NAME";
    public static final String PHOTO_BITMAP_W = "PHOTO_BITMAP_W";
    public static final String PHOTO_BITMAP_H = "PHOTO_BITMAP_H";
    //用于标识是否是clip界面是否是二维码
    public static final String NODE_IS_QRCODE = "NODE_IS_QRCODE";
    public static final String NODE_IS_PRINT = "NODE_IS_PRINT";

    public static final int ROOT_VALUE_TEXT = 1;
    public static final int ROOT_VALUE_IMG = 2;
    public static final int ROOT_VALUE_TABLE = 3;

    //用于测试数据的docId
    //public static final int TEST_DOCID = 834;

    public static final String NoData = "没有数据";




    /*WEB TYPE*/
    public static final String WEBTYPE = "WEBTYPE";
    public static final String TYPE_ABOUT_ME = "TYPE_ABOUT_ME";
    public static final String TYPE_PAYAGREEMENT = "TYPE_PAYAGREEMENT";
    public static final String TYPE_USERAGREEMENT = "USERAGREEMENT";
    public static final String TYPE_ADDFORM = "TYPE_ADDFORM";
    public static final String TYPE_FREEBACKINFO = "TYPE_FREEBACKINFO";


    public static  int CURRENT_PAGE = -1;
}

