package itbour.onetouchshow.bean;

import java.util.List;

/**
 * Created by onetouch on 2017/11/30.
 */

public class MyCollectBean {

    /**
     * list : [{"typeDesc":"","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U439445/2017/09/04/145216291_i11jA0pqg8Trdg8c5y2y/0.jpg","time":"2017-9-01 05:17:54","id":2535,"name":"创意黑色理发店美发优惠券","useCounts":0,"show_type_id":2,"h":496,"w":1537,"author":"丁雯","opType":1},{"typeDesc":"","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U352822/2017/07/07/171405969_nZJQQvktxcypEkfIQzff/0.jpg","time":"2017-7-07 04:44:12","id":1354,"name":"大暑冰棍公众号小图","useCounts":0,"show_type_id":2,"h":800,"w":800,"author":"小明","opType":1},{"typeDesc":"","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U5/2017/07/04/142039266_PyWqA7x2udeqkmI0JDG7/0.jpg","time":"2017-6-02 04:00:10","id":888,"name":"绿色健康素食促销宣传单","useCounts":0,"h":1719,"w":1276,"author":" 周静HR","opType":1},{"typeDesc":"","thumb":"[\"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2016/10/10/111611198_75YW2KSzgXq4VQgqR0uX/0.jpg\"]","time":"2017-10-09 12:59:55","id":4607,"name":"测试视频模板1","useCounts":0,"h":400,"w":500,"author":"TST_一键生成君","opType":2},{"typeDesc":"","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U11/2017/07/27/173840338_NaBISAKAZdVMS8WIJzTF/0.jpg","time":"2016-8-08 04:51:16","id":4232,"name":"扁平风格美国队长创业路演","useCounts":0,"h":900,"w":1600,"author":"FengE","opType":3},{"typeDesc":"","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2017/06/30/132711067_n9NO5wZzA9E9nqtUE0Ij/0.jpg","time":"2016-10-11 04:20:14","id":3,"name":"品牌秋冬上新推广模板","useCounts":0,"show_type_id":2,"h":1280,"w":720,"author":"TST_一键生成君","opType":1}]
     * totalPage : 1
     */

    private int totalPage;
    private List<ListBean> list;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * typeDesc :
         * thumb : http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U439445/2017/09/04/145216291_i11jA0pqg8Trdg8c5y2y/0.jpg
         * time : 2017-9-01 05:17:54
         * id : 2535
         * name : 创意黑色理发店美发优惠券
         * useCounts : 0
         * show_type_id : 2
         * h : 496
         * w : 1537
         * author : 丁雯
         * opType : 1
         */

        private String typeDesc;
        private String thumb;
        private String time;
        private int id;
        private String name;
        private int useCounts;
        private int show_type_id;
        private int h;
        private int w;
        private String author;
        private int opType;

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUseCounts() {
            return useCounts;
        }

        public void setUseCounts(int useCounts) {
            this.useCounts = useCounts;
        }

        public int getShow_type_id() {
            return show_type_id;
        }

        public void setShow_type_id(int show_type_id) {
            this.show_type_id = show_type_id;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
        }

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getOpType() {
            return opType;
        }

        public void setOpType(int opType) {
            this.opType = opType;
        }

        @Override
        public String toString() {
            return "ListBean{" + "typeDesc='" + typeDesc + '\'' + ", thumb='" + thumb + '\'' + ", time='" + time + '\'' + ", id=" + id + ", name='" + name + '\'' + ", useCounts=" + useCounts + ", show_type_id=" + show_type_id + ", h=" + h + ", w=" + w + ", author='" + author + '\'' + ", opType=" + opType + '}';
        }
    }
}
