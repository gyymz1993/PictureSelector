package itbour.onetouchshow.bean;

import java.util.List;

/**
 * Created by zjl_d on 2017/11/21.
 */

public class ModelPreviewInfoBean {

    /**
     * id : 1
     * image : {"h":1280,"thumbs":["http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2017/06/30/132326091_5tFfAH8rJ2PPeXLpcxAI/0.jpg"],"w":720}
     * name : 新鲜橙子上市啦快抢
     * opType : 1
     */

    private int id;
    private ImageBean image;
    private String name;
    private int opType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageBean getImage() {
        return image;
    }

    public void setImage(ImageBean image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public static class ImageBean {
        /**
         * h : 1280
         * thumbs : ["http://itbour-generate.oss-cn-hangzhou.aliyuncs.com/image/U1/2017/06/30/132326091_5tFfAH8rJ2PPeXLpcxAI/0.jpg"]
         * w : 720
         */

        private int h;
        private int w;
        private List<String> thumbs;

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

        public List<String> getThumbs() {
            return thumbs;
        }

        public void setThumbs(List<String> thumbs) {
            this.thumbs = thumbs;
        }
    }
}
