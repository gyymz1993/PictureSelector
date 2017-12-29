package itbour.onetouchshow.bean;

import java.util.List;

/**
 * @author onetouch
 * @date 2017/11/20
 */

public class HomeDetailslBean {

    private List<TypesBean> types;
    private List<FontListBean> fontList;
    private List<List<Integer>> colorList;
    private TextConfigBean textConfig;
    private AppControl appControl;
    private LengthControl lengthControl;

    public AppControl getAppControl() {
        return appControl;
    }

    public void setAppControl(AppControl appControl) {
        this.appControl = appControl;
    }

    public LengthControl getLengthControl() {
        return lengthControl;
    }

    public void setLengthControl(LengthControl lengthControl) {
        this.lengthControl = lengthControl;
    }

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }

    public List<FontListBean> getFontList() {
        return fontList;
    }

    public void setFontList(List<FontListBean> fontList) {
        this.fontList = fontList;
    }

    public List<List<Integer>> getColorList() {
        return colorList;
    }

    public void setColorList(List<List<Integer>> colorList) {
        this.colorList = colorList;
    }


    public static class TypesBean {
        /**
         * children : [{"setIds":[4],"children":[],"desc":"全部","name":"全部","column":2},{"setIds":[4],"children":[],"desc":"视频推荐模板组","name":"视频推荐","column":2}]
         * desc : 玩大片
         * name : 视频
         * st_id : 1
         */

        private String desc;
        private String name;
        private int st_id;
        private List<ChildrenBean> children;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSt_id() {
            return st_id;
        }

        public void setSt_id(int st_id) {
            this.st_id = st_id;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return "TypesBean{" + "desc='" + desc + '\'' + ", name='" + name + '\'' + ", st_id=" + st_id + ", children=" + children + '}';
        }

        public static class ChildrenBean {
            /**
             * setIds : [4]
             * children : []
             * desc : 全部
             * name : 全部
             * column : 2
             */

            private String desc;
            private String name;
            private int column;
            private List<Integer> setIds;
            private List<?> children;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getColumn() {
                return column;
            }

            public void setColumn(int column) {
                this.column = column;
            }

            public List<Integer> getSetIds() {
                return setIds;
            }

            public void setSetIds(List<Integer> setIds) {
                this.setIds = setIds;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }

            @Override
            public String toString() {
                return "ChildrenBean{" + "desc='" + desc + '\'' + ", name='" + name + '\'' + ", column=" + column + ", setIds=" + setIds + ", children=" + children + '}';
            }
        }
    }

    public static class FontListBean {
        /**
         * thumb : http://itbour-back.oss-cn-hangzhou.aliyuncs.com/image/U2/2017/08/21/190531886_0bMMwQnA1ulPRVKnu7D0
         * hThumb : http://itbour-back.oss-cn-hangzhou.aliyuncs.com/image/U2/2017/08/21/190534896_ymL0a6FHjabk4WpHt1Kl
         * font : 安卓中文
         */

        private String thumb;
        private String hThumb;
        private String font;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getHThumb() {
            return hThumb;
        }

        public void setHThumb(String hThumb) {
            this.hThumb = hThumb;
        }

        public String getFont() {
            return font;
        }

        public void setFont(String font) {
            this.font = font;
        }
    }


    public TextConfigBean getTextConfig() {
        return textConfig;
    }

    public void setTextConfig(TextConfigBean textConfig) {
        this.textConfig = textConfig;
    }


    public static class LengthControl {

        /*
        *     "nickNameMaxLen": 15,           // 昵称最大长度
            "signMaxLen" : 15,              // 签名最大长度
            "WTTextMaxLen" : 100,           // 微调文字最大长度
            "tableTextMaxLen": 10,          // 表格内文字最大长度
            "tableMaxRow" : 20,             // 表格最大行
            "tableMaxCol" : 20,             // 表格最大列
            "FTMaxImageCnt" : 10,           // 微调可编辑图片对象最大个数
            "FTMaxTextCnt" : 20,            // 微调可编辑文字对象最大个数
        * */
        private int nickNameMaxLen;
        private int signMaxLen;
        private int WTTextMaxLen;
        private int tableTextMaxLen;
        private int tableMaxRow;
        private int tableMaxCol;
        private int FTMaxImageCnt;
        private int FTMaxTextCnt;

        public int getNickNameMaxLen() {
            return nickNameMaxLen;
        }

        public void setNickNameMaxLen(int nickNameMaxLen) {
            this.nickNameMaxLen = nickNameMaxLen;
        }

        public int getSignMaxLen() {
            return signMaxLen;
        }

        public void setSignMaxLen(int signMaxLen) {
            this.signMaxLen = signMaxLen;
        }

        public int getWTTextMaxLen() {
            return WTTextMaxLen;
        }

        public void setWTTextMaxLen(int WTTextMaxLen) {
            this.WTTextMaxLen = WTTextMaxLen;
        }

        public int getTableTextMaxLen() {
            return tableTextMaxLen;
        }

        public void setTableTextMaxLen(int tableTextMaxLen) {
            this.tableTextMaxLen = tableTextMaxLen;
        }

        public int getTableMaxRow() {
            return tableMaxRow;
        }

        public void setTableMaxRow(int tableMaxRow) {
            this.tableMaxRow = tableMaxRow;
        }

        public int getTableMaxCol() {
            return tableMaxCol;
        }

        public void setTableMaxCol(int tableMaxCol) {
            this.tableMaxCol = tableMaxCol;
        }

        public int getFTMaxImageCnt() {
            return FTMaxImageCnt;
        }

        public void setFTMaxImageCnt(int FTMaxImageCnt) {
            this.FTMaxImageCnt = FTMaxImageCnt;
        }

        public int getFTMaxTextCnt() {
            return FTMaxTextCnt;
        }

        public void setFTMaxTextCnt(int FTMaxTextCnt) {
            this.FTMaxTextCnt = FTMaxTextCnt;
        }
    }


    public static class AppControl {

        //        "tel": "027-87101355",          // 客服电话
//        "enableAppraise": 1,            // 是否允许评价(个人中心去评分是否显示)
//        "enableForm" : 1,               // 是否支持视频作品表单功能
        private String tel;
        private int enableAppraise;
        private int enableForm;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getEnableAppraise() {
            return enableAppraise;
        }

        public void setEnableAppraise(int enableAppraise) {
            this.enableAppraise = enableAppraise;
        }

        public int getEnableForm() {
            return enableForm;
        }

        public void setEnableForm(int enableForm) {
            this.enableForm = enableForm;
        }
    }

    public static class TextConfigBean {


        private String defaultText;
        private int minFs;
        private int maxLs;
        private int minCs;
        private int minLs;
        private int maxFs;
        private int maxCs;

        public String getDefaultText() {
            return defaultText;
        }

        public void setDefaultText(String defaultText) {
            this.defaultText = defaultText;
        }

        public int getMinFs() {
            return minFs;
        }

        public void setMinFs(int minFs) {
            this.minFs = minFs;
        }

        public int getMaxLs() {
            return maxLs;
        }

        public void setMaxLs(int maxLs) {
            this.maxLs = maxLs;
        }

        public int getMinCs() {
            return minCs;
        }

        public void setMinCs(int minCs) {
            this.minCs = minCs;
        }

        public int getMinLs() {
            return minLs;
        }

        public void setMinLs(int minLs) {
            this.minLs = minLs;
        }

        public int getMaxFs() {
            return maxFs;
        }

        public void setMaxFs(int maxFs) {
            this.maxFs = maxFs;
        }

        public int getMaxCs() {
            return maxCs;
        }

        public void setMaxCs(int maxCs) {
            this.maxCs = maxCs;
        }
    }
}
