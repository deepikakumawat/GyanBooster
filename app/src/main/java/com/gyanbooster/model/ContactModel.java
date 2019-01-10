package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.about_us.ContactUsResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 1/31/2018.
 */

public class ContactModel extends Observable {

    private String TAG = ContactModel.class.getSimpleName();

    public void contactApi(final Context context, final String name, final String email, final String sub, final String phone, final String msg) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);
            Map<String, Object> requestLogin = new HashMap<>();
            requestLogin.put("name", name);
            requestLogin.put("email", email);
            requestLogin.put("sub", sub);
            requestLogin.put("phone", phone);
            requestLogin.put("msg", msg);
            Call call = apiCall.contactUs(requestLogin);
            call.enqueue(new Callback<ContactUsResponse>() {
                @Override
                public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    ContactModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                    ContactModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
