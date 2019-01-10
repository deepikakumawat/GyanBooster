package com.gyanbooster.dao.profile_response;

import java.io.Serializable;
import java.util.ArrayList;

public class YourCourseResponse implements Serializable {

    private ArrayList<UserCourseData> user_courses;

    private String status;

    private String code;

    public ArrayList<UserCourseData> getUser_courses() {
        return user_courses;
    }

    public void setUser_courses(ArrayList<UserCourseData> user_courses) {
        this.user_courses = user_courses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
