package com.gyanbooster.dao.course_listing;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseListingResponse implements Serializable {

    private ArrayList<Topics> topics;
    private  SubCourses sub_course;



    public SubCourses getSub_course() {
        return sub_course;
    }

    public void setSub_course(SubCourses sub_course) {
        this.sub_course = sub_course;
    }

    public ArrayList<Topics> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topics> topics) {
        this.topics = topics;
    }
}
