package com.gyanbooster.dao.video;

import java.io.Serializable;

public class Topics implements Serializable{

    private String topic_id;
    private String topic_name;
    private String course_id;
    private String sub_course_id;
    private String topic_slug;
    private String topic_summary;

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getSub_course_id() {
        return sub_course_id;
    }

    public void setSub_course_id(String sub_course_id) {
        this.sub_course_id = sub_course_id;
    }

    public String getTopic_slug() {
        return topic_slug;
    }

    public void setTopic_slug(String topic_slug) {
        this.topic_slug = topic_slug;
    }

    public String getTopic_summary() {
        return topic_summary;
    }

    public void setTopic_summary(String topic_summary) {
        this.topic_summary = topic_summary;
    }
}
