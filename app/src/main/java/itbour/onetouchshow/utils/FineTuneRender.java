package itbour.onetouchshow.utils;


/**
 * Created by SJG on 2017/12/09.
 */
public class FineTuneRender {

    private static final String TAG = "FineTuneRender";
    public static void main(String[] stra){
	FineTuneRender m = new FineTuneRender(); 
        Integer o =  Integer.valueOf((int)m.initFineTuneForJava(1.0f,"",""));
		System.out.print("\n****java call from dylib,result  is: "+o.toString()+"\n******\n");
	}
    
    // 加载微调库
    static {
        System.loadLibrary("FineTuneRender");
    }
    
  
    //------------------------------------ 基础接口 ------------------------------------
    /*
     接口名称:initFineTune 初始化微调库
     参数：r，float类型， 缩放比
     参数：objects，string类型，所有页对象的Json
     参数：tg，string类型，题纲树的Json
     返回结果：0成功，
     -1页对象数据错误
     -2提纲数据错误
     注意：1、tg数据仅仅支持3以上的版本
     */
    public static long initFineTuneForJava(float r, String objects, String tg) {
        return (new FineTuneRender()).initFineTune(r, objects, tg);
    }
    
    /*
     接口名称:initTextConfig 初始化文字配置信息
     参数：maxFs，float类型， 最大字号
     参数：minFs，float类型， 最小字号
     参数：maxLs，float类型， 最大行距
     参数：minLs，float类型， 最小行距
     参数：maxCs，float类型， 最大字距
     参数：minCs，float类型， 最小字距
     返回结果：0成功，<0错误码
     */
    public static long initTextConfigForJava(float minFs, float maxFs, float minLs, float maxLs, float minCs, float maxCs) {
        return (new FineTuneRender()).initTextConfig(minFs, maxFs, minLs, maxLs, minCs, maxCs);
    }
    
    /*
     接口名称:initDefalutText 初始化默认文字
     参数：textProperty，String 类型，默认文字属性信息
     参数：textContour，String 类型， 默认文字轮廓信息
     返回结果：0成功，<0错误码
     */
    public static long initDefalutTextForJava(String text) {
        return (new FineTuneRender()).initDefalutText(text);
    }
    
    /*
     功能：获取第n页的对象数据用于App显示
     接口名称:getObjectsForApp
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，如果为-1，则返回整页的对象；否则返回指定对象
     返回结果：String类型，本页微调对象的 json 字符串, 如:
     [{"editable":0,"ct":609,"id":1,"pos":{"y":100,"x":0,"w":200,"h":80},"color":{"r":11,"b":0,"g":0},"cp":[],"fn":"微软雅黑","fs":0.3,"ls":0.1,"cs":0.8},{"editable":0,"ct":603,"id":1,"pos":{"y":100,"x":0,"w":200,"h":80},"color":{"r":11,"b":0,"g":0},"cp":[]},{"editable":0,"ct":610,"id":3,"pos":{"y":100,"x":0,"w":200,"h":80},"children":[{"editable":0,"ct":609,"id":1,"pos":{"y":100,"x":0,"w":200,"h":80},"color":{"r":11,"b":0,"g":0},"cp":[]}]}]
     */
    public static String getObjectsForAppForJava(int pageIndex, long objId) {
        return (new FineTuneRender()).getObjectsForApp(pageIndex, objId);
    }
    
    /*
     功能：文档是否变化
     接口名称:isChange
     返回结果：1表示改变，0表示未改变
     */
    public static long isChangeForJava() {
        return (new FineTuneRender()).isChange();
    }
    
    /*
     功能：重置文档的变换状态,
     接口名称:resetChangeStatus
     返回结果：0 标识无错误, <0 标记错误码
     注意: 以当前状态为准, 重置改变的状态
     */
    public static long resetChangeStatusForJava() {
        return (new FineTuneRender()).resetChangeStatus();
    }
    
    /*
     功能：所有文字列表的JSON数据
     接口名称:getObjectsForSave
     返回结果：String的数组类型，所有页文字属性列表的JSON，结构类似于getFineTuneOfUserDoc返回的textObjects,例如：
     "[{\"ct\":607,\"id\":1}]",
     注意：1、用于保存到DB，2、TextExtObj只保留几本属性(且只保留fz\te\style\color\id\pos几个属性)
     */
    public static String getObjectsForSaveForJava() {
        return (new FineTuneRender()).getObjectsForSave();
    }
    /*
     功能：获取提纲数据
     接口名称:getTgForSave
     返回结果：String的数组类型，提纲的JSON
     注意：1、用于保存到DB
     */
    public static String getTgForSaveForJava() {
        return (new FineTuneRender()).getTgForSave();
    }
    
    /*
     功能：更新缩放倍数
     接口名称:changeRatio
     参数：R，float类型， 缩放比
     返回结果：无
     */
    public static void changeRatioForJava(float R) {
        (new FineTuneRender()).changeRatio(R);
    }
    
    /*
     接口名称：deleteObject 删除对象
     参数：pageIndex，int类型，页码从0开始
     参数：id,
     返回结果：0表示成功，<0错误码
     */
    public static long deleteObjectForJava(int pageIndex, long objId) {
        return (new FineTuneRender()).deleteObject(pageIndex, objId);
    }
    //------------------------------------ 文字接口 ------------------------------------
    
    /*
     功能：读取第n页文字的属性, 用于请求用户轮廓
     接口名称:getPageTextProperty(原getPageTextInfoEx)
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，如果为-1，则返回整页对象；否则返回指定对象(如果是表格，则返回表格内部所有文字的属性，如果是文字，则返回指定文字对象的属性)
     参数：mode，int类型，0表示取得所文字的属性，1表示取得“没有文字轮廓”的文字的属性(返回数组，个数为1)
     返回结果：String类型，本页文字属性列表的JSON，例如：
     [{"id":1,"fz":3200,"style":{"bi":0,"cs":0.00,"fn":"文泉驿等宽正黑","force":0,"fs":36.00,"ls":10.00,"max":100,"min":1,"po":1,"td":0,"vpo":2},"te":"默认文字"}]
     */
    public static String getPageTextPropertyForJava(int pageIndex, long objId,  int mode) {
        return (new FineTuneRender()).getPageTextProperty(pageIndex, objId, mode);
    }
    
    /*
     功能：更新第n页的文字轮廓数据
     接口名称:changePageTextContour
     参数：pageIndex，int类型，页码从0开始
     参数：contours，String类型，本页文字的轮廓列表的JSON，例如：[{\"id\":3,\"cp\":\"[]\"}]"
     返回结果：0成功，<0错误码
     */
    public static long changePageTextContourForJava(int pageIndex, String contours) {
        return (new FineTuneRender()).changePageTextContour(pageIndex, contours);
    }
    
    /*
     接口名称:changeTextInfo
     参数：pageIndex，int类型，页码从0开始
     参数：objId 文字id
     参数：fs:float类型，范围：(0,1.0)
     参数：ls:float类型，范围：(0,1.0)
     参数：cs:float类型，范围：(0,1.0)
     返回结果：String类型，本文字轮廓的JSON，参考getObjectsForApp
     */
    public static String changeTextInfoForJava(int pageIndex, long objId, float fs, float ls, float cs) {
        return (new FineTuneRender()).changeTextInfo(pageIndex, objId, fs, ls, cs);
    }
    
    /*
     功能：更改文字位置
     接口名称:changeTextPosition
     参数：pageIndex，int类型，页码从0开始
     参数：objId 文字id
     参数：x
     参数：y
     参数：w
     参数：h
     返回结果：String类型，本文字轮廓的JSON，例如：
     "{"color":{"b":0,"g":0,"r":11},"cp":[{"h":0.00,"paths":[{"h":0.00,"points":[[162.82,24.94,8],[162.66,25.35,2],[168.07,19.03,2]],"w":0.00}],"w":0.00,"x":0.00,"y":0.00}],"id":1,"pos":{"h":80,"w":200,"x":0,"y":100}}"
     */
    public static String changeTextPositionForJava(int pageIndex, long objId, float x, float y, float w, float h) {
        return (new FineTuneRender()).changeTextPosition(pageIndex, objId, x, y, w, h);
    }
    
    /*
     功能：更新某个文字的字体, 更新后需刷新文字轮廓
     接口名称:
     参数：pageIndex，  int类型，页码从0开始
     参数：objId,       文字id
     参数：fontFamily   字体名称, 如: 平方中
     返回结果：long objId
     */
    public static long changeFontFamilyForJava(int pageIndex, long objId, String fontFamily) {
        return (new FineTuneRender()).changeFontFamily(pageIndex, objId, fontFamily);
    }
    
    /*
     功能：更新某个文字的内容, 更新后需刷新文字轮廓
     接口名称:
     参数：pageIndex，  int类型，页码从0开始
     参数：objId,       文字id
     参数：text         文字内容
     返回结果：String类型，文字信息, 无 cp, 故需要刷新轮廓，
     */
    public static long changeTextContentForJava(int pageIndex, long objId, String text) {
        return (new FineTuneRender()).changeTextContent(pageIndex, objId, text);
    }
    
    /*
     功能：添加文字, 初始化默认文字时轮廓已带入, 无需刷新
     接口名称:addText
     参数：pageIndex，int类型，页码从0开始
     参数：value, 文字内容, 保留字段, 暂时不使用, 传递空字符串
     返回结果：文字id，<0错误码
     */
    public static long addTextForJava(int pageIndex, String value) {
        return (new FineTuneRender()).addText(pageIndex, value);
    }
    
    /**
     更改文字对象颜色
     
     接口名称:changeTextColor
     参数：pageIndex int类型，页码从0开始
     参数： objId，对象 id
     参数：r，[0-255]，颜色 r 值
     参数：g，[0-255]，颜色 g 值
     参数：b，[0-255]，颜色 b 值
     参数：a，[0-1]，颜色透明度, 预留字段, 固定传1, 标识不透明
     返回结果 该对象更新颜色后的 json 字符串
     */
    public static String changeTextColorForJava(int pageIndex, long objId, int r, int g, int b, float a) {
        return (new FineTuneRender()).changeTextColor(pageIndex, objId, r, g, b, a);
    }
    //------------------------------------ 图片接口 ------------------------------------
    /*
     功能：更新图片位置
     接口名称:changeImagePosition
     参数：pageIndex，int类型，页码从0开始
     参数：objId 图片id
     参数：x
     参数：y
     参数：w
     参数：h
     参数:  a 预留字段角度
     返回结果：String类型，当前图片信息
     */
    public static String changeImagePositionForJava(int pageIndex, long objId, float x, float y, float w, float h, float a) {
        return (new FineTuneRender()).changeImagePosition(pageIndex, objId, x, y, w, h, a);
    }
    
    /*
     功能：修改图片对象内容
     接口名称:addImage
     参数：pageIndex，int类型，页码从0开始
     参数：value, String类型，文字内容或者图片地址
     参数: w, float类型, 图片宽
     参数: h, float 类型, 图片高
     返回结果：图片id，<0错误码
     */
    public static long addImageForJava(int pageIndex,  String value, float w, float h) {
        return (new FineTuneRender()).addImage(pageIndex, value, w, h);
    }
    
    /*
     功能：修改图片对象内容
     接口名称:changeImageContent
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，对象id从0开始
     参数：value, String类型，文字内容或者图片地址
     参数: w, float类型, 图片宽
     参数: h, float 类型, 图片高
     返回结果：当前图片的微调对象信息
     */
    public static String changeImageContentForJava(int pageIndex, long objId, String value, float w, float h) {
        return (new FineTuneRender()).changeImageContent(pageIndex, objId, value, w, h);
    }
    
    
    //------------------------------------ 表格接口 ------------------------------------
    
    /*
     功能：更新table位置
     接口名称:changeTablePosition
     参数：pageIndex，int类型，页码从0开始
     参数：objId ,table's id
     参数：x
     参数：y
     参数：w
     参数：h
     参数:  a 预留字段角度
     返回结果：String类型，当前table信息
     */
    public static String changeTablePositionForJava(int pageIndex, long objId, float x, float y, float w, float h, float a) {
        return (new FineTuneRender()).changeTablePosition(pageIndex, objId, x, y, w, h, a);
    }
    
    /*
     接口名称:changeTableText, 更新完成后需重新获取轮廓
     功能：设置表格的文字内容
     参数：pageIndex，int类型，页码，从0开始
     参数：objId，int类型，表格对象id
     参数：row，int类型，总行数
     参数：col，int类型，总列数
     参数：value, String类型，文字数组的JSON字符串
     返回结果：0成功，<0失败
     */
    public static long changeTableTextForJava(int pageIndex, long objId, int row, int col, String value) {
        return (new FineTuneRender()).changeTableText(pageIndex, objId, row, col, value);
    }
    
    /**
     更改表格中的文字对象颜色
     
     接口名称:changeTableTextColor
     参数：pageIndex int类型，页码从0开始
     参数： id，对象 id
     参数：r，[0-255]，颜色 r 值
     参数：g，[0-255]，颜色 g 值
     参数：b，[0-255]，颜色 b 值
     参数：a，[0-1]，颜色透明度, 预留字段, 固定传1, 标识不透明
     返回结果 该对象更新颜色后的 json 字符串
     */
    public static String changeTableTextColorForJava(int pageIndex, long objId, int r, int g, int b, float a) {
        return (new FineTuneRender()).changeTableTextColor(pageIndex, objId, r, g, b, a);
    }
    
    
    //------------------------------------ 图形接口 ------------------------------------
    
    /*
     功能：更新graph位置
     接口名称:changeGraphPosition
     参数：pageIndex，int类型，页码从0开始
     参数：objId ,table's id
     参数：x
     参数：y
     参数：w
     参数：h
     参数:  a 预留字段角度
     返回结果：String类型，当前图形信息
     */
    public static String changeGraphPositionForJava(int pageIndex, long objId, float x, float y, float w, float h, float a) {
        return (new FineTuneRender()).changeGraphPosition(pageIndex, objId, x, y, w, h, a);
    }
    
    /**
     更改图形对象颜色
     
     接口名称:changeGraphColor
     参数：pageIndex int类型，页码从0开始
     参数：objId，对象 id
     参数: pathIndex 符合路径的索引, 目前固定传-1, 标记将所有路径颜色都改为此色值
     参数：r，[0-255]，颜色 r 值
     参数：g，[0-255]，颜色 g 值
     参数：b，[0-255]，颜色 b 值
     参数：a，[0-1]，颜色透明度, 预留字段, 固定传1, 标识不透明
     返回结果 该对象更新颜色后的 json 字符串
     */
    public static String changeGraphColorForJava(int pageIndex, long objId, int pathIndex, int r, int g, int b, float a) {
        return (new FineTuneRender()).changeGraphColor(pageIndex, objId, pathIndex, r, g, b, a);
    }
    
    
    //------------------------------------ 其他接口 ------------------------------------
    
    /*
     功能：设置出血区域
     接口名称:initBleedGap
     参数：left，float类型，左侧出血区域
     参数：top，float类型，上侧出血区域
     参数：right，float类型，右侧出血区域
     参数：bottom，float类型，下侧出血区域
     返回结果：无
     */
    public static void initBleedGapForJava(float left, float top, float right, float bottom) {
        (new FineTuneRender()).initBleedGap(left, top, right, bottom);
    }
    
    /*
     获取link对象的状态
     
     接口名称:getLinkObjStatus
     参数：pageIndex，int类型，页码从0开始
     参数：id, 对象id
     返回结果：String类型，返回空字符串表示没有link对象，否则返回link对象信息，例如：
     {
     "id":[2,3],   // 链接的对象id的列表, 不包括自己的id
     "h":0 // 1表示隐藏
     }
     */
    public static String getLinkObjStatusForJava(int pageIndex, long objId) {
        return (new FineTuneRender()).getLinkObjStatus(pageIndex, objId);
    }
    
    
   
    //------------------基础接口--------------------------------------------------------------------------

    // 初始化微调库
    public native long initFineTune(float r, String objects, String tg);
    
    // 初始化文字默认配置
    public native long initTextConfig(float minFs, float maxFs, float minLs, float maxLs, float minCs, float maxCs);
    
    // 初始化默认文字
    public native long initDefalutText(String text);
    
    // 获取 APP 显示所用的微调对象的 json 字符串
    public native String getObjectsForApp(int pageIndex, long objId);
    
    // 文档是否改变
    public native long isChange();
    
    // 重置文档的变换状态
    public native long resetChangeStatus();
    
    // 获取用于保存的微调信息
    public native String getObjectsForSave();
    
    // 获取用于保存的提纲树
    public native String getTgForSave();
    
    // 更新微调库缩放倍数
    public native void changeRatio(float R);
    
    
    //------------------文字接口--------------------------------------------------------------------------
    
    // 获取文字属性信息
    public native String getPageTextProperty(int pageIndex, long objId,  int mode);
    
    // 更新指定页文字轮廓信息
    public native long changePageTextContour(int pageIndex, String contours);
    
    // 更新文字属性信息
    public native String changeTextInfo(int pageIndex, long objId, float fs, float ls, float cs);
    
    // 更新微调文字对象位置
    public native String changeTextPosition(int pageIndex, long objId, float x, float y, float w, float h);
    
    // 更新微调文字对象字体
    public native long changeFontFamily(int pageIndex, long objId, String fontFamily);
    
    // 更新文字内容
    public native long changeTextContent(int pageIndex, long objId, String text);
    
    // 加字
    public native long addText(int pageIndex, String value);
    
    // 修改微调文字颜色
    public native String changeTextColor(int pageIndex, long objId, int r, int g, int b, float a);
    
    // 删除微调对象
    public native long deleteObject(int pageIndex, long objId);
    
    
    //------------------图片接口--------------------------------------------------------------------------
    // 修改图片位置
    public native String changeImagePosition(int pageIndex, long objId, float x, float y, float w, float h, float a);
    
    // 添加图片
    public native long addImage(int pageIndex,  String value, float w, float h);
    
    // 修改图片
    public native String changeImageContent(int pageIndex, long objId, String value, float w, float h);
    
    
    //------------------表格接口--------------------------------------------------------------------------
    // 更新表格位置
    public native String changeTablePosition(int pageIndex, long objId, float x, float y, float w, float h, float a);
    
    // 更新表格文字内容
    public native long changeTableText(int pageIndex, long objId, int row, int col, String value);
    
    // 更新表格内文字颜色
    public native String changeTableTextColor(int pageIndex, long objId, int r, int g, int b, float a);
    
    //------------------图形接口--------------------------------------------------------------------------
    
    // 更新图形位置
    public native String changeGraphPosition(int pageIndex, long objId, float x, float y, float w, float h, float a);
    
    // 更新图形颜色
    public native String changeGraphColor(int pageIndex, long objId, int pathIndex, int r, int g, int b, float a);
    
    
    //------------------其它--------------------------------------------------------------------------
    
    // 设置出血区域
    public native void initBleedGap(float left, float top, float right, float bottom);
    
    // 获取链接对象信息
    public native String getLinkObjStatus(int pageIndex, long objId);
    
}
