package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.course_listing.CourseListingResponse;

import java.util.Observable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 1/31/2018.
 */

public class CourseListingModel extends Observable {

    private String TAG = CourseListingModel.class.getSimpleName();

    public void courseListingyApi(final Context context, String subCourseId) {


        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);
            Call call = apiCall.courseListing(subCourseId);
            call.enqueue(new Callback<CourseListingResponse>() {
                @Override
                public void onResponse(Call<CourseListingResponse> call, Response<CourseListingResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    CourseListingModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<CourseListingResponse> call, Throwable t) {
                    CourseListingModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
