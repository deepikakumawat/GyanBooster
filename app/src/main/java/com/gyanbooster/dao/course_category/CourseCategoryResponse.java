package com.gyanbooster.dao.course_category;

import java.util.ArrayList;

public class CourseCategoryResponse {

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



}
