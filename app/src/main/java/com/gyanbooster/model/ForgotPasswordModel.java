package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.forgot_password.ChangePasswordResponse;
import com.gyanbooster.dao.forgot_password.ForgotPasswordOTPResponse;
import com.gyanbooster.dao.forgot_password.ForgotPasswordResponse;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordModel extends Observable {

    private String TAG = ForgotPasswordModel.class.getSimpleName();

    public void forgorPassword(final Context context, final String emailPhone) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Call call = apiCall.forgotPassword(emailPhone);
            call.enqueue(new Callback<ForgotPasswordResponse>() {
                @Override
                public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    ForgotPasswordModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                    ForgotPasswordModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void forgorOTPPassword(final Context context, String otp, final String userId) {

               
                
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);
            Map<String, Object> requestLogin = new HashMap<>();
            requestLogin.put("otp", otp);
            requestLogin.put("user_id", userId);
            Call call = apiCall.forgotOTPVerification(requestLogin);
            call.enqueue(new Callback<ForgotPasswordOTPResponse>() {
                @Override
                public void onResponse(Call<ForgotPasswordOTPResponse> call, Response<ForgotPasswordOTPResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    ForgotPasswordModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<ForgotPasswordOTPResponse> call, Throwable t) {
                    ForgotPasswordModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void chanePassword(final Context context, final String password,final String confirmPassword, String userId) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);
            Map<String, Object> requestLogin = new HashMap<>();
            requestLogin.put("user_id", userId);
            requestLogin.put("password", password);
            requestLogin.put("conpass", confirmPassword);

            Call call = apiCall.changePassword(requestLogin);
            call.enqueue(new Callback<ChangePasswordResponse>() {
                @Override
                public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    ForgotPasswordModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                    ForgotPasswordModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
