package com.gyanbooster.dao.course_category;

import java.io.Serializable;

public class PopularVideoData implements Serializable {


    public static final String POPULARVIDEODATA = "POPULARVIDEODATA";
    public static final String POPULARVIDEOLIST = "POPULARVIDEOLIST";
    private String video_id;
    private String title;
    private String img_url;
    private String video_url;
    private String video_slug;
    private String video_desc;
    private String youtube_id;

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_slug() {
        return video_slug;
    }

    public void setVideo_slug(String video_slug) {
        this.video_slug = video_slug;
    }

    public String getVideo_desc() {
        return video_desc;
    }

    public void setVideo_desc(String video_desc) {
        this.video_desc = video_desc;
    }

    public String getYoutube_id() {
        return youtube_id;
    }

    public void setYoutube_id(String youtube_id) {
        this.youtube_id = youtube_id;
    }
}
