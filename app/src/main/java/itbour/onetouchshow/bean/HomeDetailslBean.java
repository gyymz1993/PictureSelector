package itbour.onetouchshow.bean;

import java.util.List;

/**
 *
 * @author onetouch
 * @date 2017/11/20
 */

public class HomeDetailslBean {

    private List<TypesBean> types;
    private List<FontListBean> fontList;
    private List<List<Integer>> colorList;

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

}
