package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.PermissionToViewResponse;
import com.gyanbooster.dao.about_us.AboutUsResponse;
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

public class AboutUsModel extends Observable {

    private String TAG = AboutUsModel.class.getSimpleName();

    public void aboutUsApi(final Context context) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Call call = apiCall.aboutUs();
            call.enqueue(new Callback<AboutUsResponse>() {
                @Override
                public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                    if(response.isSuccessful() && response.body()!=null) {
                        Log.d(TAG, call.request().url().toString());
                        AboutUsModel.this.setChanged();
                        notifyObservers(response.body());
                    }
                }

                @Override
                public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                    AboutUsModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
