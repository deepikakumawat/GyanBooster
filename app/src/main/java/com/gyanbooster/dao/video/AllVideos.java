package com.gyanbooster.dao.video;

import java.io.Serializable;

public class AllVideos implements Serializable {

    private String video_id;
    private String v_course_id;
    private String v_sub_course_id;
    private String v_topic_id;
    private String course_type;
    private String video_thumb;
    private String video_url;
    private String vimeo_url;
    private String youtube_url;
    private String storage_video_type;
    private String video_title;
    private String video_desc;
    private String video_type;
    private String video_status;
    private String video_play_number;
    private String create_on;
    private String topic_name;

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getV_course_id() {
        return v_course_id;
    }

    public void setV_course_id(String v_course_id) {
        this.v_course_id = v_course_id;
    }

    public String getV_sub_course_id() {
        return v_sub_course_id;
    }

    public void setV_sub_course_id(String v_sub_course_id) {
        this.v_sub_course_id = v_sub_course_id;
    }

    public String getV_topic_id() {
        return v_topic_id;
    }

    public void setV_topic_id(String v_topic_id) {
        this.v_topic_id = v_topic_id;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getVideo_thumb() {
        return video_thumb;
    }

    public void setVideo_thumb(String video_thumb) {
        this.video_thumb = video_thumb;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVimeo_url() {
        return vimeo_url;
    }

    public void setVimeo_url(String vimeo_url) {
        this.vimeo_url = vimeo_url;
    }

    public String getYoutube_url() {
        return youtube_url;
    }

    public void setYoutube_url(String youtube_url) {
        this.youtube_url = youtube_url;
    }

    public String getStorage_video_type() {
        return storage_video_type;
    }

    public void setStorage_video_type(String storage_video_type) {
        this.storage_video_type = storage_video_type;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_desc() {
        return video_desc;
    }

    public void setVideo_desc(String video_desc) {
        this.video_desc = video_desc;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public String getVideo_status() {
        return video_status;
    }

    public void setVideo_status(String video_status) {
        this.video_status = video_status;
    }

    public String getVideo_play_number() {
        return video_play_number;
    }

    public void setVideo_play_number(String video_play_number) {
        this.video_play_number = video_play_number;
    }

    public String getCreate_on() {
        return create_on;
    }

    public void setCreate_on(String create_on) {
        this.create_on = create_on;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }
}
