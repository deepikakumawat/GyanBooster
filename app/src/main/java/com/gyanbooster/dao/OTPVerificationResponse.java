package com.gyanbooster.dao;

import java.util.ArrayList;

public class OTPVerificationResponse {

    private  Courses courses;
    private ArrayList<SubCourses> sub_courses;

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public ArrayList<SubCourses> getSub_courses() {
        return sub_courses;
    }

    public void setSub_courses(ArrayList<SubCourses> sub_courses) {
        this.sub_courses = sub_courses;
    }

    private class Courses {

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

    private class SubCourses {

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
}
