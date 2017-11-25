package itbour.onetouchshow.utils;


/**
 * Created by SJG on 2017/12/09.
 */
public class FineTuneRender {

    private static final String TAG = "FineTuneRender";
    
    
    public static void main(String[] stra){
		FineTuneRender m = new FineTuneRender();
		Integer o =  m.initFineTuneForJava(1.0f,"","");
		System.out.print("\n****java call from dylib,result  is: "+o.toString()+"\n******\n");
	}  
  
    
    
    //---------------------------------工具类接口--------------------------
    /*
     功能：初始化微调的数据
     接口名称:initFineTune
     参数：R，float类型， 缩放比
     参数：textObjects，string类型，所有页文字列表的Json
     参数：tg，string类型，题纲树的Json
     返回结果：1成功，0失败
     */
    public static int initFineTuneForJava(float R, String textObjects, String tg){
    	return (new FineTuneRender()).initFineTune(R,textObjects,tg);
    }
    
    /*
     功能：读取第n页文字的属性
     接口名称:getPageTextInfoEx
     参数：pageIndex，int类型，页码从0开始
     参数：mode，int类型，0表示取得所文字的属性，1表示取得“没有文字轮廓”的文字的属性
     返回结果：string类型，本页文字属性列表的JSON，例如：
     [{id:1,fz:64,te:"xyz",style:{ v:1,fn:"微软雅黑",fs:20,bi:0,po:1,vpo:2,cs:6,ls:10,td:0} }]
     */
     public static String getPageTextInfoExForJava(int pageIndex, int mode){
    	return (new FineTuneRender()).getPageTextInfoEx(pageIndex, mode);
    }
    
    /*
     功能：初始化第n页的文字轮廓数据
     接口名称:initPageTextsContour
     参数：pageIndex，int类型，页码从0开始
     参数：texts，string类型，本页文字的轮廓列表的JSON，例如：[{\"id\":3,\"pc\":{}}]
     返回结果：string类型，本页文字轮廓列表的JSON，例如：
     "[{\"id\":1,\"color\":{\"r\":11,\"g\":0,\"b\":0},\"pos\":{\"x\":0,\"y\":100,\"w\":200,\"h\":80},\"cp\":[{\"paths\":[{\"points\":[[162.82,24.94,8],[162.66,25.35,2],[168.07,19.03,2]],\"w\":0.00,\"h\":0.00}],\"w\":0.00,\"h\":0.00,\"x\":0.00,\"y\":0.00}]}]"
     */
    public static int initPageTextsContourForJava(int pageIndex, String texts){
    	return (new FineTuneRender()).initPageTextsContour(pageIndex,texts);
    }
    
    
    /*
     功能：获取第n页的对象数据用于App显示
     接口名称:getObjectsForApp
     参数：pageIndex，int类型，页码从0开始
     参数：texts，string类型，本页文字的轮廓列表的JSON，例如：[{\"id\":3,\"cp\":\"[]\"}]"
     返回结果：string类型，本页文字轮廓列表的JSON，例如：
     [
     {
     "editable": 0,//1表示可编辑
     "ct": 609[文字],// 或者 603［图形］
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80}
     "color": {"r": 11, "b": 0, "g": 0},
     "cp": [],
     "fn":"平方中"
     },
     {
     "editable": 0,//1表示可编辑
     "ct": 602 , // 图片
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80},
     "source": "http://xxx/1.jpg",
     "image": {"y": 100, "x": 0, "w": 200, "h": 80},
     "mask":[]
     }
     ]
     */
    public static String getObjectsForAppForJava(int pageIndex) {
        return (new FineTuneRender()).getObjectsForApp(pageIndex);
    }
    
    /*
     功能：根据新字号计算文字位置
     接口名称:changeFont
     参数：pageIndex，int类型，页码从0开始
     参数：id 文字id
     参数：increase，1表示增加，-1表示减少
     返回结果：string类型，本页文字轮廓的JSON，例如：
     "{\"id\":1,\"color\":{\"r\":11,\"g\":0,\"b\":0},\"pos\":{\"x\":0,\"y\":100,\"w\":200,\"h\":80},\"cp\":[{\"paths\":[{\"points\":[[162.82,24.94,8],[162.66,25.35,2],[168.07,19.03,2]],\"w\":0.00,\"h\":0.00}],\"w\":0.00,\"h\":0.00,\"x\":0.00,\"y\":0.00}]}"
     */
    public static String changeFontSizeForJava(int pageIndex, int id, int increase){
    	return (new FineTuneRender()).changeFontSize(pageIndex,id,increase);
    }
    
    
    /*
     功能：根据文字框大小计算文字位置
     接口名称:changePosition
     参数：pageIndex，int类型，页码从0开始
     参数：id 文字id
     参数：x
     参数：y
     参数：w
     参数：h
     返回结果：string类型，本文字轮廓的JSON，例如：
     "{\"id\":1,\"color\":{\"r\":11,\"g\":0,\"b\":0},\"pos\":{\"x\":0,\"y\":100,\"w\":200,\"h\":80},\"cp\":[{\"paths\":[{\"points\":[[162.82,24.94,8],[162.66,25.35,2],[168.07,19.03,2]],\"w\":0.00,\"h\":0.00}],\"w\":0.00,\"h\":0.00,\"x\":0.00,\"y\":0.00}]}"
     */
    public static String changeTextPositionForJava(int pageIndex, int id, float x, float y, float w, float h){
    	return (new FineTuneRender()).changeTextPosition(pageIndex,id,x,y,w,h);
    }
    
    
    /*
     功能：重置文档数据
     接口名称:resetData
     */
    public static void resetDataForJava(){
		(new FineTuneRender()).resetData();
    }
    
    
    /*
     功能：文档是否变化
     接口名称:isChange
     返回结果：1表示改变，0表示未改变
     */
    public static int isChangeForJava(){
    	return (new FineTuneRender()).isChange();
    }
    
    
    /*
     功能：所有文字列表的JSON数据
     接口名称:getTextObjects
     返回结果：string的数组类型，所有页文字属性列表的JSON，结构类似于getFineTuneOfUserDoc返回的textObjects
     */
    public static String getObjectsForSaveForJava(){
    	return (new FineTuneRender()).getObjectsForSave();
    }
    
    //-----------------v5.1 新增接口---------------------------------------------
    /*
     功能：获取某个文字的描述信息，用于从文字服务器获取轮廓
     接口名称:
     参数：pageIndex，  int类型，页码从0开始
     参数：id, 			文字id
     参数：fontFamily 	        字体名称, 如: 平方中
     返回结果：string类型，当前文字属性列表的JSON，例如：
     "[{"id":1,"fz":64,"te":"xyz","style":{"v":1,"fn":"平方中","fs":20,"bi":0,"po":1,"vpo":2,"cs":6,"ls":10,"td":0}}]"
     注: 返回结果与库接口2 getPageTextInfo 结果一致
     */
    public static String getFontFamilyTextInfoForJava(int pageIndex, int id, String fontFamily){
        return (new FineTuneRender()).getFontFamilyTextInfo(pageIndex, id, fontFamily);
    }
    
    
    /*
     功能：更新某个文字的字体
     接口名称:
     参数：pageIndex，  int类型，页码从0开始
     参数：id, 			文字id
     参数：fontFamily 	        字体名称, 如: 平方中
     参数：texts，			string类型，本页文字的轮廓列表的JSON，例如："[{\"id\":3,\"cp\":\"[]\"}]"
     返回结果：string类型，本文字轮廓列表的JSON，例如：
     {
     "ct": 609[文字],// 或者 603［图形］
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80}
     "color": {"r": 11, "b": 0, "g": 0},
     "cp": [],
     "fn":"平方中"
     }
     */
    public static String changeFontFamilyForJava(int pageIndex, int id, String fontFamily, String texts) {
        return (new FineTuneRender()).changeFontFamily(pageIndex, id, fontFamily, texts);
    }
    
    /*
     功能：更新图片位置
     接口名称:changeImagePosition
     参数：pageIndex，int类型，页码从0开始
     参数：id 图片id
     参数：x
     参数：y
     参数：w
     参数：h
     参数:  a 预留字段角度
     返回结果：string类型，当前图片信息, 如:
     {
     "ct": 602 , // 图片
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80},
     "source": "http://xxx/1.jpg",
     "image": "http://oss-image.jpg",
     "mask":[]
     }
     */
    public static String changeImagePositionForJava(int pageIndex, int id, float x, float y, float w, float h, float a) {
        return (new FineTuneRender()).changeImagePosition(pageIndex, id, x, y, w, h, a);
    }
    
    
    //--------------------------v5.3 新增接口---------------------------------
    
    /*
     功能：获取第n页的指定对象数据用于App显示
     接口名称:getOneObjectForApp
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，对象id从0开始
     返回结果：string类型，对象的JSON，例如：
     {
     "editable": 0,//1表示可编辑
     "ct": 609[文字],// 或者 603［图形］
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80}
     "color": {"r": 11, "b": 0, "g": 0},
     "cp": [],
     "fn":"平方中"
     }
     */
    public static String getOneObjectForAppForJava(int pageIndex, int objId) {
        return (new FineTuneRender()).getOneObjectForApp(pageIndex, objId);
    }
  
    /*
     功能：清空对象内容的信息
     接口名称:clearOneObjectContent
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，对象id从0开始
     返回结果：无
     */
    public static void clearOneObjectContentForJava(int pageIndex, int objId) {
        (new FineTuneRender()).clearOneObjectContent(pageIndex, objId);
    }
    
    /*
     功能：修改对象内容的信息。如果是文字则修改内容并清空轮廓，如果是图片则修改图片地址
     接口名称:updateTextObjectContent
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，对象id从0开始
     参数：value, string类型，文字内容或者图片地址
     返回结果：无
     */
    public static void updateOneObjectContentForJava(int pageIndex, int objId, String value) {
         (new FineTuneRender()).updateOneObjectContent(pageIndex, objId, value);
    }
    
    /*
     功能：修改图片对象内容
     接口名称:updateOneImageObjContent
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，对象id从0开始
     参数：value, string类型，文字内容或者图片地址
     参数: w, float类型, 图片宽
     参数: h, float 类型, 图片高
     返回结果：无
     */
    public static void updateOneImageObjContentForJava(int pageIndex, int objId, String value, float w, float h) {
        (new FineTuneRender()).updateOneImageObjContent(pageIndex, objId, value, w, h);
    }
    
    /*
     功能：设置还原点
     接口名称:saveRestorePoint
     返回结果：无
     */
    public static void saveRestorePointForJava() {
        (new FineTuneRender()).saveRestorePoint();
    }
    
    /*
     功能：更新缩放倍数
     接口名称:updateRatio
     参数：R，float类型， 缩放比
     返回结果：无
     */
    public static void updateRatioForJava(float R) {
        (new FineTuneRender()).updateRatio(R);
    }
    
    //--------------------------v5.4 新增接口---------------------------------
    /*
     功能：更新table位置
     接口名称:changeTablePosition
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：x
     参数：y
     参数：w
     参数：h
     参数:  a 预留字段角度
     返回结果：string类型，当前table信息, 如:
     {
     "editable": 0,//1表示可编辑
     "ct": 610 , // 表格
     "id": 3,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80},
     "children":[
     {
     "editable": 0,//1表示可编辑
     "ct": 609[文字],// 或者 603［图形］
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80}
     "color": {"r": 11, "b": 0, "g": 0},
     "cp": [],
     }
     ]
     }
     */
    public static String changeTablePositionForJava(int pageIndex, int id, float x, float y, float w, float h, float a) {
        return (new FineTuneRender()).changeTablePosition(pageIndex, id, x, y, w, h, a);
    }
    
    /*
     功能：获取表格中格子的对象id
     接口名称:getTableCellChildrenId
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，表格对象id从0开始
     返回结果：[[[1,2]]]
     注意：需要在以下情况下调用：
     1、初始化
     2 、重置
     3、添加行
     4、添加列
     */
    public static String getTableCellChildrenIdForJava(int pageIndex, int objId) {
        return (new FineTuneRender()).getTableCellChildrenId(pageIndex, objId);
    }
    
    /*
     功能：添加列
     接口名称:removeColumn
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：index,>=0,如果有n个元素，index的范围[0,n]
     返回结果：
     */
    public static void addColumnForJava(int pageIndex, int id, int index) {
        (new FineTuneRender()).addColumn(pageIndex, id, index);
    }
    
    /*
     功能：添加行
     接口名称:removeColumn
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：index,>=0,如果有n个元素，index的范围[0,n]
     返回结果：
     */
    public static void addRowForJava(int pageIndex, int id, int index) {
        (new FineTuneRender()).addRow(pageIndex, id, index);
    }
    
    /*
     功能：删除列
     接口名称:removeColumn
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：index,>=0,如果有n个元素，index的范围[0,n-1]
     返回结果：
     */
    public static void removeColumnForJava(int pageIndex, int id, int index) {
        (new FineTuneRender()).removeColumn(pageIndex, id, index);
    }
    
    /*
     功能：删除行
     接口名称:removeColumn
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：index,>=0,如果有n个元素，index的范围[0,n-1]
     返回结果：
     */
    public static void removeRowForJava(int pageIndex, int id, int index) {
        (new FineTuneRender()).removeRow(pageIndex, id, index);
    }
    
    /*
     功能：添加文字
     接口名称:addText
     参数：pageIndex，int类型，页码从0开始
     参数：value, 文字内容
     返回结果：文字id
     */
    public static long addTextForJava(int pageIndex, String value) {
        return (new FineTuneRender()).addText(pageIndex, value);
    }
    
    
    /*
     功能：修改图片对象内容
     接口名称:addImage
     参数：pageIndex，int类型，页码从0开始
     参数：value, string类型，文字内容或者图片地址
     参数: w, float类型, 图片宽
     参数: h, float 类型, 图片高
     返回结果：无
     */
    public static long addImageForJava(int pageIndex,  String value, float w, float h) {
        return (new FineTuneRender()).addImage(pageIndex, value, w, h);
    }

    //--------------------------v5.5 新增接口---------------------------------
    /*
     功能：设置出血区域
     接口名称:setBleedGap
     参数：left，float类型，左侧出血区域
     参数：top，float类型，上侧出血区域
     参数：right，float类型，右侧出血区域
     参数：bottom，float类型，下侧出血区域
     返回结果：无
     */
    public static void setBleedGapForJava(float left, float top, float right, float bottom) {
        (new FineTuneRender()).setBleedGap(left, top, right, bottom);
    }
    
    
    //--------------------------v5.6 新增接口---------------------------------
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
    public static String changeTextColorForJava(int pageIndex, int id, int r, int g, int b, float a) {
        return (new FineTuneRender()).changeTextColor(pageIndex, id, r, g, b, a);
    }
    
    /**
     更改表格中的文字对象颜色
     
     接口名称:changeTableTextColor
     参数：pageIndex int类型，页码从0开始
     参数： objId，对象 id
     参数：r，[0-255]，颜色 r 值
     参数：g，[0-255]，颜色 g 值
     参数：b，[0-255]，颜色 b 值
     参数：a，[0-1]，颜色透明度, 预留字段, 固定传1, 标识不透明
     返回结果 该对象更新颜色后的 json 字符串
     */
    public static String changeTableTextColorForJava(int pageIndex, int id, int r, int g, int b, float a) {
        return (new FineTuneRender()).changeTableTextColor(pageIndex, id, r, g, b, a);
    }
    //--------------------------v5.7 新增接口---------------------------------
    /*
     获取link对象的状态
     
     接口名称:getLinkObjStatus
     参数：pageIndex，int类型，页码从0开始
     参数：id, 对象id
     返回结果：string类型，返回空字符串表示没有link对象，否则返回link对象信息，例如：
     {
     "id":[2,3],   // 链接的对象id的列表, 不包括自己的id
     "h":0 // 1表示隐藏
     }
     */
    public static String getLinkObjStatusForJava(int pageIndex, long id) {
        return (new FineTuneRender()).getLinkObjStatus(pageIndex, id);
    }
    //--------------------------v6.2 新增接口---------------------------------
    /**
     更改图形对象颜色 v6.2
     
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
    public static String changeGraphColorForJava(int pageIndex, long id, int pathIndex, int r, int g, int b, float a) {
        return (new FineTuneRender()).changeGraphColor(pageIndex,id,pathIndex,r,g,b,a);
    }
    
    /*
     功能：更新graph位置 v6.2
     接口名称:changeGraphPosition
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：x
     参数：y
     参数：w
     参数：h
     参数:  a 预留字段角度
     返回结果：string类型，当前table信息, 如:
     {
     "editable": 0,//1表示可编辑
     "ct": 610 , // 表格
     "id": 3,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80},
     "children":[
     {
     "editable": 0,//1表示可编辑
     "ct": 609[文字],// 或者 603［图形］
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80}
     "color": {"r": 11, "b": 0, "g": 0},
     "cp": [],
     }
     ]
     }
     
     */
    public static String changeGraphPositionForJava(int pageIndex, long id, float x, float y, float w, float h, float a) {
        return (new FineTuneRender()).changeGraphPosition(pageIndex, id, x, y, w, h, a);
    }
    
    
    
    //=============================================================================
    //=============================================================================
    //=================================动态链接库接口=================================
    //=============================================================================
    //=============================================================================
    static {
		//System.out.print(System.mapLibraryName("Hello"));
	   System.loadLibrary("FineTuneRender");  
    }  
    /*
     功能：初始化微调的数据
     接口名称:initFineTune
     参数：R，float类型， 缩放比
     参数：textObjects，string类型，所有页文字列表的Json
     参数：tg，string类型，题纲树的Json
     返回结果：1成功，0失败
     */
    public native int initFineTune(float R, String textObjects, String tg);
    
    /*
     功能：读取第n页文字的属性
     接口名称:getPageTextInfoEx
     参数：pageIndex，int类型，页码从0开始
     参数：mode，int类型，0表示取得所文字的属性，1表示取得“没有文字轮廓”的文字的属性
     返回结果：string类型，本页文字属性列表的JSON，例如：
     [{id:1,fz:64,te:"xyz",style:{ v:1,fn:"微软雅黑",fs:20,bi:0,po:1,vpo:2,cs:6,ls:10,td:0} }]
     */
    public native String getPageTextInfoEx(int pageIndex, int mode);
    
    /*
     功能：初始化第n页的文字轮廓数据
     接口名称:initPageTextsContour
     参数：pageIndex，int类型，页码从0开始
     参数：texts，string类型，本页文字的轮廓列表的JSON，例如：[{\"id\":3,\"pc\":{}}]
     返回结果：string类型，本页文字轮廓列表的JSON，例如：
     "[{\"id\":1,\"color\":{\"r\":11,\"g\":0,\"b\":0},\"pos\":{\"x\":0,\"y\":100,\"w\":200,\"h\":80},\"cp\":[{\"paths\":[{\"points\":[[162.82,24.94,8],[162.66,25.35,2],[168.07,19.03,2]],\"w\":0.00,\"h\":0.00}],\"w\":0.00,\"h\":0.00,\"x\":0.00,\"y\":0.00}]}]"
     */
    public native int initPageTextsContour(int pageIndex, String texts);
    
    /*
     功能：获取第n页的对象数据用于App显示
     接口名称:getObjectsForApp
     参数：pageIndex，int类型，页码从0开始
     参数：texts，string类型，本页文字的轮廓列表的JSON，例如：[{\"id\":3,\"cp\":\"[]\"}]"
     返回结果：string类型，本页文字轮廓列表的JSON，例如：
     [
     {
     "editable": 0,//1表示可编辑
     "ct": 609[文字],// 或者 603［图形］
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80}
     "color": {"r": 11, "b": 0, "g": 0},
     "cp": [],
     "fn":"平方中"
     },
     {
     "editable": 0,//1表示可编辑
     "ct": 602 , // 图片
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80},
     "source": "http://xxx/1.jpg",
     "image": {"y": 100, "x": 0, "w": 200, "h": 80},
     "mask":[]
     }
     ]
     */
    public native String getObjectsForApp(int pageIndex);
    
    /*
     功能：根据新字号计算文字位置
     接口名称:changeFont
     参数：pageIndex，int类型，页码从0开始
     参数：id 文字id
     参数：increase，1表示增加，-1表示减少
     返回结果：string类型，本页文字轮廓的JSON，例如：
     "{\"id\":1,\"color\":{\"r\":11,\"g\":0,\"b\":0},\"pos\":{\"x\":0,\"y\":100,\"w\":200,\"h\":80},\"cp\":[{\"paths\":[{\"points\":[[162.82,24.94,8],[162.66,25.35,2],[168.07,19.03,2]],\"w\":0.00,\"h\":0.00}],\"w\":0.00,\"h\":0.00,\"x\":0.00,\"y\":0.00}]}"
     */
    public native String changeFontSize(int pageIndex, int id, int increase);
    
    
    /*
     功能：根据文字框大小计算文字位置
     接口名称:changePosition
     参数：pageIndex，int类型，页码从0开始
     参数：id 文字id
     参数：x
     参数：y
     参数：w
     参数：h
     返回结果：string类型，本文字轮廓的JSON，例如：
     "{\"id\":1,\"color\":{\"r\":11,\"g\":0,\"b\":0},\"pos\":{\"x\":0,\"y\":100,\"w\":200,\"h\":80},\"cp\":[{\"paths\":[{\"points\":[[162.82,24.94,8],[162.66,25.35,2],[168.07,19.03,2]],\"w\":0.00,\"h\":0.00}],\"w\":0.00,\"h\":0.00,\"x\":0.00,\"y\":0.00}]}"
     */
    public native String changeTextPosition(int pageIndex, int id, float x, float y, float w, float h);
    
    
    /*
     功能：重置文档数据
     接口名称:resetData
     */
    public native void resetData();
    
    
    /*
     功能：文档是否变化
     接口名称:isChange
     返回结果：1表示改变，0表示未改变
     */
    public native int isChange();
    
    
    /*
     功能：所有文字列表的JSON数据
     接口名称:getTextObjects
     返回结果：string的数组类型，所有页文字属性列表的JSON，结构类似于getFineTuneOfUserDoc返回的textObjects
     */
    public native String getObjectsForSave();
    
    //-----------------v5.1 新增接口---------------------------------------------
    /*
     功能：获取某个文字的描述信息，用于从文字服务器获取轮廓
     接口名称:
     参数：pageIndex，  int类型，页码从0开始
     参数：id, 			文字id
     参数：fontFamily 	        字体名称, 如: 平方中
     返回结果：string类型，当前文字属性列表的JSON，例如：
     "[{"id":1,"fz":64,"te":"xyz","style":{"v":1,"fn":"平方中","fs":20,"bi":0,"po":1,"vpo":2,"cs":6,"ls":10,"td":0}}]"
     注: 返回结果与库接口2 getPageTextInfo 结果一致
     */
    public native String getFontFamilyTextInfo(int pageIndex, int id, String fontFamily);
    
    /*
     功能：更新某个文字的字体
     接口名称:
     参数：pageIndex，  int类型，页码从0开始
     参数：id, 			文字id
     参数：fontFamily 	        字体名称, 如: 平方中
     参数：texts，			string类型，本页文字的轮廓列表的JSON，例如："[{\"id\":3,\"cp\":\"[]\"}]"
     返回结果：string类型，本文字轮廓列表的JSON，例如：
     {
     "ct": 609[文字],// 或者 603［图形］
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80}
     "color": {"r": 11, "b": 0, "g": 0},
     "cp": [],
     "fn":"平方中"
     }
     */
    public native String changeFontFamily(int pageIndex, int id, String fontFamily, String texts);
    
    /*
     功能：更新图片位置
     接口名称:changeImagePosition
     参数：pageIndex，int类型，页码从0开始
     参数：id 图片id
     参数：x
     参数：y
     参数：w
     参数：h
     参数:  a 预留字段角度
     返回结果：string类型，当前图片信息, 如:
     {
     "ct": 602 , // 图片
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80},
     "source": "http://xxx/1.jpg",
     "image": "http://oss-image.jpg",
     "mask":[]
     }
     */
    public native String changeImagePosition(int pageIndex, int id, float x, float y, float w, float h, float a);
    
    //--------------------------v5.3 新增接口---------------------------------
    
    /*
     功能：获取第n页的指定对象数据用于App显示
     接口名称:getOneObjectForApp
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，对象id从0开始
     返回结果：string类型，对象的JSON，例如：
     {
     "editable": 0,//1表示可编辑
     "ct": 609[文字],// 或者 603［图形］
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80}
     "color": {"r": 11, "b": 0, "g": 0},
     "cp": [],
     "fn":"平方中"
     }
     */
    public native String getOneObjectForApp(int pageIndex, int objId);
    
    /*
     功能：清空对象内容的信息
     接口名称:clearOneObjectContent
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，对象id从0开始
     返回结果：无
     */
    public native void clearOneObjectContent(int pageIndex, int objId);
    
    /*
     功能：修改对象内容的信息。如果是文字则修改内容并清空轮廓，如果是图片则修改图片地址
     接口名称:updateTextObjectContent
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，对象id从0开始
     参数：value, string类型，文字内容或者图片地址
     返回结果：无
     */
    public native void updateOneObjectContent(int pageIndex, int objId, String value);
    
    /*
     功能：修改图片对象内容
     接口名称:updateOneImageObjContent
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，对象id从0开始
     参数：value, string类型，文字内容或者图片地址
     参数: w, float类型, 图片宽
     参数: h, float 类型, 图片高
     返回结果：无
     */
    public native  void updateOneImageObjContent(int pageIndex, int objId, String value, float w, float h);
    
    /*
     功能：设置还原点
     接口名称:saveRestorePoint
     返回结果：无
     */
    public native void saveRestorePoint();
    
    /*
     功能：更新缩放倍数
     接口名称:updateRatio
     参数：R，float类型， 缩放比
     返回结果：无
     */
    public native void updateRatio(float R);
    //--------------------------v5.4 新增接口---------------------------------
    
    /*
     功能：更新table位置
     接口名称:changeTablePosition
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：x
     参数：y
     参数：w
     参数：h
     参数:  a 预留字段角度
     返回结果：string类型，当前table信息, 如:
     {
     "editable": 0,//1表示可编辑
     "ct": 610 , // 表格
     "id": 3,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80},
     "children":[
     {
     "editable": 0,//1表示可编辑
     "ct": 609[文字],// 或者 603［图形］
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80}
     "color": {"r": 11, "b": 0, "g": 0},
     "cp": [],
     }
     ]
     }
     */
    public native String changeTablePosition(int pageIndex, int id, float x, float y, float w, float h, float a);
    
    /*
     功能：获取表格中格子的对象id
     接口名称:getTableCellChildrenId
     参数：pageIndex，int类型，页码从0开始
     参数：objId，int类型，表格对象id从0开始
     返回结果：[[[1,2]]]
     注意：需要在以下情况下调用：
     1、初始化
     2 、重置
     3、添加行
     4、添加列
     */
    public native String getTableCellChildrenId(int pageIndex, int objId);
    
    /*
     功能：添加列
     接口名称:removeColumn
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：index,>=0,如果有n个元素，index的范围[0,n]
     返回结果：
     */
    public native void addColumn(int pageIndex, int id, int index);
    
    /*
     功能：添加行
     接口名称:removeColumn
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：index,>=0,如果有n个元素，index的范围[0,n]
     返回结果：
     */
    public native void addRow(int pageIndex, int id, int index);
    
    /*
     功能：删除列
     接口名称:removeColumn
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：index,>=0,如果有n个元素，index的范围[0,n-1]
     返回结果：
     */
    public native void removeColumn(int pageIndex, int id, int index);
    
    /*
     功能：删除行
     接口名称:removeColumn
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：index,>=0,如果有n个元素，index的范围[0,n-1]
     返回结果：
     */
    public native void removeRow(int pageIndex, int id, int index);
    
    /*
     功能：添加文字
     接口名称:addText
     参数：pageIndex，int类型，页码从0开始
     参数：value, 文字内容
     返回结果：文字id
     */
    public native long addText(int pageIndex, String value);
    
    
    /*
     功能：修改图片对象内容
     接口名称:addImage
     参数：pageIndex，int类型，页码从0开始
     参数：value, string类型，文字内容或者图片地址
     参数: w, float类型, 图片宽
     参数: h, float 类型, 图片高
     返回结果：无
     */
    public native long addImage(int pageIndex,  String value, float w, float h);
    
    //--------------------------v5.5 新增接口---------------------------------
    /*
     功能：设置出血区域
     接口名称:setBleedGap
     参数：left，float类型，左侧出血区域
     参数：top，float类型，上侧出血区域
     参数：right，float类型，右侧出血区域
     参数：bottom，float类型，下侧出血区域
     返回结果：无
     */
    public native void setBleedGap(float left, float top, float right, float bottom);
    
    
    //--------------------------v5.6 新增接口---------------------------------
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
    public native String changeTextColor(int pageIndex, int id, int r, int g, int b, float a);
    
    /**
     更改表格中的文字对象颜色
     
     接口名称:changeTableTextColor
     参数：pageIndex int类型，页码从0开始
     参数： objId，对象 id
     参数：r，[0-255]，颜色 r 值
     参数：g，[0-255]，颜色 g 值
     参数：b，[0-255]，颜色 b 值
     参数：a，[0-1]，颜色透明度, 预留字段, 固定传1, 标识不透明
     返回结果 该对象更新颜色后的 json 字符串
     */
    public native String changeTableTextColor(int pageIndex, int id, int r, int g, int b, float a);
    
    
    //--------------------------v5.7 新增接口---------------------------------
    /*
     获取link对象的状态
     
     接口名称:getLinkObjStatus
     参数：pageIndex，int类型，页码从0开始
     参数：id, 对象id
     返回结果：string类型，返回空字符串表示没有link对象，否则返回link对象信息，例如：
     {
     "id":[2,3],   // 链接的对象id的列表, 不包括自己的id
     "h":0 // 1表示隐藏
     }
     */
    public native String getLinkObjStatus(int pageIndex, long id);
    

    
    //--------------------------v6.2 新增接口---------------------------------
    /**
     更改图形对象颜色 v6.2
     
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
    public native String changeGraphColor(int pageIndex, long id, int pathIndex, int r, int g, int b, float a);
    
    /*
     功能：更新graph位置 v6.2
     接口名称:changeGraphPosition
     参数：pageIndex，int类型，页码从0开始
     参数：id ,table's id
     参数：x
     参数：y
     参数：w
     参数：h
     参数:  a 预留字段角度
     返回结果：string类型，当前table信息, 如:
     {
     "editable": 0,//1表示可编辑
     "ct": 610 , // 表格
     "id": 3,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80},
     "children":[
     {
     "editable": 0,//1表示可编辑
     "ct": 609[文字],// 或者 603［图形］
     "id": 1,
     "pos": {"y": 100, "x": 0, "w": 200, "h": 80}
     "color": {"r": 11, "b": 0, "g": 0},
     "cp": [],
     }
     ]
     }
     
     */
    public native String changeGraphPosition(int pageIndex, long id, float x, float y, float w, float h, float a);
    
    
    
}
