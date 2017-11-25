package itbour.onetouchshow.activity.videoplay.helper.video;

import java.io.Serializable;


public class VideoItem implements Serializable{


    private String title;
    private String imgUrl;
    private String videoUrl;
    private String docUrl;

    public VideoItem() {
    }

    public VideoItem(String title, String videoUrl, String docUrl) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.docUrl = docUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }

        if(!(obj instanceof VideoItem)){
            return false;
        }

        VideoItem other= (VideoItem) obj;
        return other.getTitle().equals(getTitle())&&other.getDocUrl().equals(getDocUrl())&&other.getVideoUrl().equals(getVideoUrl());
    }
}
