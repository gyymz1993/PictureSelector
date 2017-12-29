package itbour.onetouchshow.bean;

import java.util.List;

/**
 * Created by onetouch on 2017/11/30.
 */

public class OrderBean {

    /**
     * list : [{"totalFee":10000,"id":13,"name":"测试水印价格","state":10,"odr_dtl_type_tag":2,"type":0,"detailItems":[{"thumb":"","itemName":"测试视频模板1","id":13,"docName":"","unit":"次","itemThumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2016/10/10/111611198_75YW2KSzgXq4VQgqR0uX/0.jpg","ppName":"","qty":1,"docId":"我的作品","type":10,"update_time":"2017-11-23T07:19:46.000Z","itemDesc":"测试水印价格"}]},{"totalFee":10000,"id":14,"name":"测试水印价格","state":10,"odr_dtl_type_tag":2,"type":0,"detailItems":[{"thumb":"","itemName":"测试视频模板1","id":14,"docName":"","unit":"次","itemThumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2016/10/10/111611198_75YW2KSzgXq4VQgqR0uX/0.jpg","ppName":"","qty":1,"docId":"我的作品","type":10,"update_time":"2017-11-23T07:19:46.000Z","itemDesc":"测试水印价格"}]},{"totalFee":10000,"id":15,"name":"测试水印价格","state":10,"odr_dtl_type_tag":2,"type":0,"detailItems":[{"thumb":"","itemName":"测试视频模板1","id":15,"docName":"","unit":"次","itemThumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2016/10/10/111611198_75YW2KSzgXq4VQgqR0uX/0.jpg","ppName":"","qty":1,"docId":"我的作品","type":10,"update_time":"2017-11-23T07:19:46.000Z","itemDesc":"测试水印价格"}]},{"totalFee":10000,"id":17,"name":"测试水印价格","state":10,"odr_dtl_type_tag":2,"type":0,"detailItems":[{"thumb":"","itemName":"测试视频模板1","id":17,"docName":"","unit":"次","itemThumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2016/10/10/111611198_75YW2KSzgXq4VQgqR0uX/0.jpg","ppName":"","qty":1,"docId":"我的作品","type":10,"update_time":"2017-11-23T07:19:46.000Z","itemDesc":"测试水印价格"}]},{"totalFee":1,"id":21,"name":"去水印","state":10,"odr_dtl_type_tag":2,"type":0,"detailItems":[]},{"totalFee":1,"id":22,"name":"去水印","state":10,"odr_dtl_type_tag":2,"type":0,"detailItems":[]},{"totalFee":1,"id":25,"name":"去水印","state":10,"odr_dtl_type_tag":2,"type":0,"detailItems":[]}]
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
         * totalFee : 10000
         * id : 13
         * name : 测试水印价格
         * state : 10
         * odr_dtl_type_tag : 2
         * type : 0
         * detailItems : [{"thumb":"","itemName":"测试视频模板1","id":13,"docName":"","unit":"次","itemThumb":"http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2016/10/10/111611198_75YW2KSzgXq4VQgqR0uX/0.jpg","ppName":"","qty":1,"docId":"我的作品","type":10,"update_time":"2017-11-23T07:19:46.000Z","itemDesc":"测试水印价格"}]
         */

        private int totalFee;
        private int id;
        private String name;
        private int state;
        private int odr_dtl_type_tag;
        private int type;
        private List<DetailItemsBean> detailItems;

        public int getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(int totalFee) {
            this.totalFee = totalFee;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getOdr_dtl_type_tag() {
            return odr_dtl_type_tag;
        }

        public void setOdr_dtl_type_tag(int odr_dtl_type_tag) {
            this.odr_dtl_type_tag = odr_dtl_type_tag;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<DetailItemsBean> getDetailItems() {
            return detailItems;
        }

        public void setDetailItems(List<DetailItemsBean> detailItems) {
            this.detailItems = detailItems;
        }

        public static class DetailItemsBean {
            /**
             * thumb :
             * itemName : 测试视频模板1
             * id : 13
             * docName :
             * unit : 次
             * itemThumb : http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2016/10/10/111611198_75YW2KSzgXq4VQgqR0uX/0.jpg
             * ppName :
             * qty : 1
             * docId : 我的作品
             * type : 10
             * update_time : 2017-11-23T07:19:46.000Z
             * itemDesc : 测试水印价格
             */

            private String thumb;
            private String itemName;
            private int id;
            private String docName;
            private String unit;
            private String itemThumb;
            private String ppName;
            private int qty;
            private String docId;
            private int type;
            private String update_time;
            private String itemDesc;

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDocName() {
                return docName;
            }

            public void setDocName(String docName) {
                this.docName = docName;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getItemThumb() {
                return itemThumb;
            }

            public void setItemThumb(String itemThumb) {
                this.itemThumb = itemThumb;
            }

            public String getPpName() {
                return ppName;
            }

            public void setPpName(String ppName) {
                this.ppName = ppName;
            }

            public int getQty() {
                return qty;
            }

            public void setQty(int qty) {
                this.qty = qty;
            }

            public String getDocId() {
                return docId;
            }

            public void setDocId(String docId) {
                this.docId = docId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getItemDesc() {
                return itemDesc;
            }

            public void setItemDesc(String itemDesc) {
                this.itemDesc = itemDesc;
            }
        }
    }
}
