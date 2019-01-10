package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.profile_response.YourCourseResponse;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourCourseModel extends Observable {

    private String TAG = YourCourseModel.class.getSimpleName();

    public void yourCourseApi(final Context context) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Call call = apiCall.yourCourse(GyanBoosterPreferences.getUserId());
            call.enqueue(new Callback<YourCourseResponse>() {
                @Override
                public void onResponse(Call<YourCourseResponse> call, Response<YourCourseResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    YourCourseModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<YourCourseResponse> call, Throwable t) {
                    YourCourseModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
