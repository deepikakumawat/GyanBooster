package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.course_details.AddReviewResponse;
import com.gyanbooster.dao.course_details.Course;
import com.gyanbooster.dao.course_details.CourseDetailsResponse;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 1/31/2018.
 */

public class CourseDetailsModel extends Observable {

    private String TAG = CourseDetailsModel.class.getSimpleName();

    public void courseDetailsApi(final Context context, final String courseId) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Call call = apiCall.courseDetails(courseId);
            call.enqueue(new Callback<CourseDetailsResponse>() {
                @Override
                public void onResponse(Call<CourseDetailsResponse> call, Response<CourseDetailsResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    CourseDetailsModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<CourseDetailsResponse> call, Throwable t) {
                    CourseDetailsModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setReviewAPI(final Context context, final String reviewMsg, final String reviewRating, final Course course) {


        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);
            Map<String, Object> requestLogin = new HashMap<>();
            requestLogin.put("user_id", GyanBoosterPreferences.getUserId());
            requestLogin.put("reviewmessage", reviewMsg);
            requestLogin.put("rating_val", reviewRating);
            requestLogin.put("course_id", course.getCourse_id());
            Call call = apiCall.addReview(requestLogin);
            call.enqueue(new Callback<AddReviewResponse>() {
                @Override
                public void onResponse(Call<AddReviewResponse> call, Response<AddReviewResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    CourseDetailsModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<AddReviewResponse> call, Throwable t) {
                    CourseDetailsModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
