package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.course_category.FeatureCourseResponse;
import com.gyanbooster.dao.course_category.CourseCategoryResponse;
import com.gyanbooster.dao.course_category.PopularVideoResponse;

import java.util.Observable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 1/31/2018.
 */

public class CourseCategoryModel extends Observable {

    private String TAG = CourseCategoryModel.class.getSimpleName();

    public void courseCategoryApi(final Context context, int courseId) {

        try {
            ApiInterface apiCall = APIClient.getClientWithMultipleHeader(URLs.BASE_URL).create(ApiInterface.class);
            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");


            String params = "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"course_id\"\r\n\r\n" + courseId + "\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--";


            RequestBody body = RequestBody.create(mediaType, params);
            Call call = apiCall.courseCategory( body);
            call.enqueue(new Callback<CourseCategoryResponse>() {
                @Override
                public void onResponse(Call<CourseCategoryResponse> call, Response<CourseCategoryResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    CourseCategoryModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<CourseCategoryResponse> call, Throwable t) {
                    CourseCategoryModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void popularVideoApi(final Context context) {
        try {
            ApiInterface apiCall = APIClient.getClientWithMultipleHeader(URLs.BASE_URL).create(ApiInterface.class);

            Call call = apiCall.popularVideo();
            call.enqueue(new Callback<PopularVideoResponse>() {
                @Override
                public void onResponse(Call<PopularVideoResponse> call, Response<PopularVideoResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    CourseCategoryModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<PopularVideoResponse> call, Throwable t) {
                    CourseCategoryModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void featureCourseApi(final Context context) {
        try {
            ApiInterface apiCall = APIClient.getClientWithMultipleHeader(URLs.BASE_URL).create(ApiInterface.class);

            Call call = apiCall.popularVideo();
            call.enqueue(new Callback<FeatureCourseResponse>() {
                @Override
                public void onResponse(Call<FeatureCourseResponse> call, Response<FeatureCourseResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    CourseCategoryModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<FeatureCourseResponse> call, Throwable t) {
                    CourseCategoryModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
