package com.gyanbooster.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.login_response.LoginResponse;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 1/31/2018.
 */

public class LoginModel extends Observable {

    private String TAG = LoginModel.class.getSimpleName();

    public void loginApi(final Context context, final String userName, final String password) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);
            Map<String, Object> requestLogin = new HashMap<>();
            requestLogin.put("user", userName);
            requestLogin.put("password", password);
            Call call = apiCall.login(requestLogin);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    LoginModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    LoginModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
