package com.gyanbooster.dao.course_category;

import java.io.Serializable;

public class Courses implements Serializable {

    private String course_id;
    private String course_name;
    private String course_summary;
    private String course_thumbnail;
    private String course_banner;
    private String course_price;
    private String course_old_price;
    private String course_slug;
    private String status;
    private String create_on;
    private String featured;
    private String course_desc;
    private String additional_info;


    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_summary() {
        return course_summary;
    }

    public void setCourse_summary(String course_summary) {
        this.course_summary = course_summary;
    }

    public String getCourse_thumbnail() {
        return course_thumbnail;
    }

    public void setCourse_thumbnail(String course_thumbnail) {
        this.course_thumbnail = course_thumbnail;
    }

    public String getCourse_banner() {
        return course_banner;
    }

    public void setCourse_banner(String course_banner) {
        this.course_banner = course_banner;
    }

    public String getCourse_price() {
        return course_price;
    }

    public void setCourse_price(String course_price) {
        this.course_price = course_price;
    }

    public String getCourse_old_price() {
        return course_old_price;
    }

    public void setCourse_old_price(String course_old_price) {
        this.course_old_price = course_old_price;
    }

    public String getCourse_slug() {
        return course_slug;
    }

    public void setCourse_slug(String course_slug) {
        this.course_slug = course_slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_on() {
        return create_on;
    }

    public void setCreate_on(String create_on) {
        this.create_on = create_on;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getCourse_desc() {
        return course_desc;
    }

    public void setCourse_desc(String course_desc) {
        this.course_desc = course_desc;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }
}
