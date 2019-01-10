package com.gyanbooster.dao.select_courses;



import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 1/31/2018.
 */

public class SelectCourseResponse implements Serializable {


    private ArrayList<SelectCourseData> all_courses;

    public ArrayList<SelectCourseData> getAll_courses() {
        return all_courses;
    }

    public void setAll_courses(ArrayList<SelectCourseData> all_courses) {
        this.all_courses = all_courses;
    }
}
