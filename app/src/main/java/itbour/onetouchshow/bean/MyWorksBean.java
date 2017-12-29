package itbour.onetouchshow.bean;

import java.util.List;

/**
 * Created by onetouch on 2017/11/30.
 */

public class MyWorksBean {

    /**
     * list : [{"typeDesc":"单页海报","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U299188/2017/11/20/172949155_fFPgNKXE4hc3PuQjDAWK/0.jpg","watermarkPay":0,"time":"2017-11-20T09:06:05.000Z","id":11,"name":"我的作品","watermark":1,"h":1280,"w":720,"opType":1},{"typeDesc":"单页海报","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U299188/2017/11/21/111047146_Il2OXTrJtUsX7j0W6iI7/0.jpg","watermarkPay":0,"time":"2017-11-21T03:03:33.000Z","id":11,"name":"我的作品","watermark":1,"h":1280,"w":720,"opType":1},{"typeDesc":"单页海报","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U299188/2017/11/22/181416096_td5Ci3iCJf1nVnQaXgJv/0.jpg","watermarkPay":0,"time":"2017-11-21T03:03:33.000Z","id":11,"name":"我的作品","watermark":1,"h":1280,"w":720,"opType":1},{"typeDesc":"单页海报","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U299188/2017/11/21/111259048_4AjmwOEnxI9xGpB9NV8i/0.jpg","watermarkPay":0,"time":"2017-11-21T03:03:33.000Z","id":11,"name":"我的作品","watermark":1,"h":1280,"w":720,"opType":1},{"typeDesc":"单页海报","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U299188/2017/11/22/102049663_bhuAyhVvysBV5xmVNSTD/0.jpg","watermarkPay":0,"time":"2017-11-22T02:15:40.000Z","id":12,"name":"我的作品","watermark":1,"h":1280,"w":720,"opType":1},{"typeDesc":"单页海报","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U299188/2017/11/22/174339716_DLsIXj9BLIM1Yrd4k6m6/0.jpg","watermarkPay":0,"time":"2017-11-22T02:15:40.000Z","id":12,"name":"我的作品","watermark":1,"h":1280,"w":720,"opType":1},{"typeDesc":"单页海报","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U299188/2017/11/22/174419413_rddQUx1cXHD8hYdxZQ6q/0.jpg","watermarkPay":0,"time":"2017-11-22T02:15:40.000Z","id":12,"name":"我的作品","watermark":1,"h":1280,"w":720,"opType":1},{"typeDesc":"单页海报","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U299188/2017/11/23/113853369_Zy396q1wh9BWfC4dXFfN/0.jpg","watermarkPay":0,"time":"2017-11-23T03:38:45.000Z","id":42,"name":"我的作品","watermark":1,"h":1280,"w":720,"opType":1},{"typeDesc":"单页海报","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U299188/2017/11/23/114012135_58QwYpxJg3NpjEqT0fLc/0.jpg","watermarkPay":0,"time":"2017-11-23T03:40:05.000Z","id":43,"name":"我的作品","watermark":1,"h":800,"w":800,"opType":1},{"typeDesc":"单页海报","thumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U299188/2017/11/23/114127418_vkFh8D4i7EnFUK0zYJC4/0.jpg","watermarkPay":0,"time":"2017-11-23T03:41:27.000Z","id":44,"name":"我的作品","watermark":1,"h":496,"w":1537,"opType":1}]
     * totalPage : 18
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
         * typeDesc : 单页海报
         * thumb : http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U299188/2017/11/20/172949155_fFPgNKXE4hc3PuQjDAWK/0.jpg
         * watermarkPay : 0
         * time : 2017-11-20T09:06:05.000Z
         * id : 11
         * name : 我的作品
         * watermark : 1
         * h : 1280
         * w : 720
         * opType : 1
         */

        private String typeDesc;
        private String thumb;
        private int watermarkPay;
        private String time;
        private int id;
        private String name;
        private int watermark;
        private int h;
        private int w;
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

        public int getWatermarkPay() {
            return watermarkPay;
        }

        public void setWatermarkPay(int watermarkPay) {
            this.watermarkPay = watermarkPay;
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

        public int getWatermark() {
            return watermark;
        }

        public void setWatermark(int watermark) {
            this.watermark = watermark;
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

        public int getOpType() {
            return opType;
        }

        public void setOpType(int opType) {
            this.opType = opType;
        }
    }
}
