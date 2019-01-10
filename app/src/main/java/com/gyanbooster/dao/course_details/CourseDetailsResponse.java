package com.gyanbooster.dao.course_details;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseDetailsResponse implements Serializable {

    private Course courses;
    private AvgReview avg_review;
    private ArrayList<CourseReviewsData> reviews;
    private String totalreview;

    public Course getCourses() {
        return courses;
    }

    public void setCourses(Course courses) {
        this.courses = courses;
    }

    public AvgReview getAvg_review() {
        return avg_review;
    }

    public void setAvg_review(AvgReview avg_review) {
        this.avg_review = avg_review;
    }

    public ArrayList<CourseReviewsData> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<CourseReviewsData> reviews) {
        this.reviews = reviews;
    }

    public String getTotalreview() {
        return totalreview;
    }

    public void setTotalreview(String totalreview) {
        this.totalreview = totalreview;
    }
}
