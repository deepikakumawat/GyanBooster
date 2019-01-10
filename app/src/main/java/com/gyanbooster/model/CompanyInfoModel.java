package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.CompanyInfoResponse.CompanyInfoResponse;
import com.gyanbooster.dao.about_us.AboutUsResponse;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 1/31/2018.
 */

public class CompanyInfoModel extends Observable {

    private String TAG = CompanyInfoModel.class.getSimpleName();

    public void companyInfoApi(final Context context) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Call call = apiCall.companyInfo();
            call.enqueue(new Callback<CompanyInfoResponse>() {
                @Override
                public void onResponse(Call<CompanyInfoResponse> call, Response<CompanyInfoResponse> response) {
                    if(response.isSuccessful() && response.body()!=null) {
                        Log.d(TAG, call.request().url().toString());
                        CompanyInfoModel.this.setChanged();
                        notifyObservers(response.body());
                    }
                }

                @Override
                public void onFailure(Call<CompanyInfoResponse> call, Throwable t) {
                    CompanyInfoModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
