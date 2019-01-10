package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.PermissionToViewResponse;
import com.gyanbooster.dao.course_category.PopularVideoResponse;
import com.gyanbooster.dao.select_courses.SelectCourseResponse;
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

public class SelectCourseModel extends Observable {

    private String TAG = SelectCourseModel.class.getSimpleName();

    public void selectApi(final Context context) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Call call = apiCall.selectCourse();
            call.enqueue(new Callback<SelectCourseResponse>() {
                @Override
                public void onResponse(Call<SelectCourseResponse> call, Response<SelectCourseResponse> response) {
                    if(response.isSuccessful() && response.body()!=null) {
                        Log.d(TAG, call.request().url().toString());
                        SelectCourseModel.this.setChanged();
                        notifyObservers(response.body());
                    }
                }

                @Override
                public void onFailure(Call<SelectCourseResponse> call, Throwable t) {
                    SelectCourseModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void permissionToViewApi(final Context context, final String courseId) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);
            Map<String, Object> requestLogin = new HashMap<>();
            requestLogin.put("user_id", GyanBoosterPreferences.getUserId());
            requestLogin.put("course_id", courseId);
            Call call = apiCall.permissionToView(requestLogin);
            call.enqueue(new Callback<PermissionToViewResponse>() {
                @Override
                public void onResponse(Call<PermissionToViewResponse> call, Response<PermissionToViewResponse> response) {
                    if(response.isSuccessful() && response.body()!=null) {
                        Log.d(TAG, call.request().url().toString());
                        SelectCourseModel.this.setChanged();
                        notifyObservers(response.body());
                    }
                }

                @Override
                public void onFailure(Call<PermissionToViewResponse> call, Throwable t) {
                    SelectCourseModel.this.setChanged();
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
                    SelectCourseModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<PopularVideoResponse> call, Throwable t) {
                    SelectCourseModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
