package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.profile_response.DashboardResponse;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardModel extends Observable {

    private String TAG = DashboardModel.class.getSimpleName();

    public void dashboardApi(final Context context) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Call call = apiCall.dashboard(GyanBoosterPreferences.getUserId());
            call.enqueue(new Callback<DashboardResponse>() {
                @Override
                public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    DashboardModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<DashboardResponse> call, Throwable t) {
                    DashboardModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
