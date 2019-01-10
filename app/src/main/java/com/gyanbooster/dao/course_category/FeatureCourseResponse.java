package com.gyanbooster.dao.course_category;



import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dell on 1/31/2018.
 */

public class FeatureCourseResponse implements Serializable {

    public static final String SELECTCOURSE = "SELECTCOURSE";


    private ArrayList<FeaturesCourseData> feature_courses;

    public ArrayList<FeaturesCourseData> getFeature_courses() {
        return feature_courses;
    }

    public void setFeature_courses(ArrayList<FeaturesCourseData> feature_courses) {
        this.feature_courses = feature_courses;
    }


}
