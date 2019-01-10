package com.gyanbooster.dao.course_listing;

import java.io.Serializable;

public class SubCourses implements Serializable {
    private String course_id;
    private String gb_sub_course_id;
    private String sub_course_summary;
    private String sub_course_name;
    private String course_slug;


    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getGb_sub_course_id() {
        return gb_sub_course_id;
    }

    public void setGb_sub_course_id(String gb_sub_course_id) {
        this.gb_sub_course_id = gb_sub_course_id;
    }

    public String getSub_course_summary() {
        return sub_course_summary;
    }

    public void setSub_course_summary(String sub_course_summary) {
        this.sub_course_summary = sub_course_summary;
    }

    public String getSub_course_name() {
        return sub_course_name;
    }

    public void setSub_course_name(String sub_course_name) {
        this.sub_course_name = sub_course_name;
    }

    public String getCourse_slug() {
        return course_slug;
    }

    public void setCourse_slug(String course_slug) {
        this.course_slug = course_slug;
    }
}
