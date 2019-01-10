package com.gyanbooster.dao.course_category;

import java.io.Serializable;

public class SubCourses implements Serializable{

    public static final String SUBCOURSEDATA = "SUBCOURSEDATA";

    private String gb_sub_course_id;
    private String course_id;
    private String sub_course_name;
    private String sub_course_slug;
    private String sub_course_banner;
    private String sub_course_summary;



    public String getGb_sub_course_id() {
        return gb_sub_course_id;
    }

    public void setGb_sub_course_id(String gb_sub_course_id) {
        this.gb_sub_course_id = gb_sub_course_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getSub_course_name() {
        return sub_course_name;
    }

    public void setSub_course_name(String sub_course_name) {
        this.sub_course_name = sub_course_name;
    }

    public String getSub_course_slug() {
        return sub_course_slug;
    }

    public void setSub_course_slug(String sub_course_slug) {
        this.sub_course_slug = sub_course_slug;
    }

    public String getSub_course_banner() {
        return sub_course_banner;
    }

    public void setSub_course_banner(String sub_course_banner) {
        this.sub_course_banner = sub_course_banner;
    }

    public String getSub_course_summary() {
        return sub_course_summary;
    }

    public void setSub_course_summary(String sub_course_summary) {
        this.sub_course_summary = sub_course_summary;
    }
}