package itbour.onetouchshow.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onetouch on 2017/11/20.
 */

public class DesignListBean implements Parcelable {

    /**
     * list : [{"typeDesc":"设计","order":999,"thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2017/06/30/132326091_5tFfAH8rJ2PPeXLpcxAI/0.jpg","time":"2016-10-11 02:25:41","id":1,"name":"新鲜橙子上市啦快抢","useCounts":0,"h":1280,"w":720,"author":"TST_一键生成君","opType":1},{"typeDesc":"设计","order":999,"thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U352822/2017/07/07/171405969_nZJQQvktxcypEkfIQzff/0.jpg","time":"2017-7-07 04:44:12","id":1354,"name":"大暑冰棍公众号小图","useCounts":0,"h":800,"w":800,"author":"小明","opType":1},{"typeDesc":"设计","order":999,"thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2017/06/30/132711067_n9NO5wZzA9E9nqtUE0Ij/0.jpg","time":"2016-10-11 04:20:14","id":3,"name":"品牌秋冬上新推广模板","useCounts":0,"h":1280,"w":720,"author":"TST_一键生成君","opType":1},{"typeDesc":"设计","order":999,"thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U12/2017/07/05/150713157_tlL9hJXgE8ju1qyUnN2u/0.jpg","time":"2016-10-12 09:38:32","id":7,"name":"水墨风展览会邀请函","useCounts":0,"h":1280,"w":720,"author":"一颗豆几","opType":1},{"typeDesc":"设计","order":999,"thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U12/2017/07/12/140902099_BgNHr5u5J9qIfeIDoEa2/0.jpg","time":"2016-10-12 10:06:18","id":9,"name":"音乐会邀请函","useCounts":0,"h":1280,"w":720,"author":"一颗豆几","opType":1}]
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

    public static class ListBean implements Parcelable {


        /**
         * typeDesc : 设计
         * order : 999
         * thumb : http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2017/06/30/132326091_5tFfAH8rJ2PPeXLpcxAI/0.jpg
         * time : 2016-10-11 02:25:41
         * id : 1
         * name : 新鲜橙子上市啦快抢
         * useCounts : 0
         * h : 1280
         * w : 720
         * author : TST_一键生成君
         * opType : 1
         */

        private String typeDesc;
        private int order;
        private String thumb;
        private String time;
        private int id;
        private String name;
        private int useCounts;
        private int h;
        private int w;
        private String author;
        private int opType;
        private String subtype;

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
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
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.typeDesc);
            dest.writeInt(this.order);
            dest.writeString(this.thumb);
            dest.writeString(this.time);
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeInt(this.useCounts);
            dest.writeInt(this.h);
            dest.writeInt(this.w);
            dest.writeString(this.author);
            dest.writeInt(this.opType);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.typeDesc = in.readString();
            this.order = in.readInt();
            this.thumb = in.readString();
            this.time = in.readString();
            this.id = in.readInt();
            this.name = in.readString();
            this.useCounts = in.readInt();
            this.h = in.readInt();
            this.w = in.readInt();
            this.author = in.readString();
            this.opType = in.readInt();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalPage);
        dest.writeList(this.list);
    }

    public DesignListBean() {
    }

    protected DesignListBean(Parcel in) {
        this.totalPage = in.readInt();
        this.list = new ArrayList<ListBean>();
        in.readList(this.list, ListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DesignListBean> CREATOR = new Parcelable.Creator<DesignListBean>() {
        @Override
        public DesignListBean createFromParcel(Parcel source) {
            return new DesignListBean(source);
        }

        @Override
        public DesignListBean[] newArray(int size) {
            return new DesignListBean[size];
        }
    };
}
