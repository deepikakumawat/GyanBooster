package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.OTPResendResponse;
import com.gyanbooster.dao.OTPValidateResponse;
import com.gyanbooster.dao.RegisterResponse;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 1/31/2018.
 */

public class RegisterModel extends Observable {

    private String TAG = RegisterModel.class.getSimpleName();

    public void registerApi(final Context context, final String fullName, final String email, final String password, String confirmPassword, String mobileNumber) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Map<String, Object> requestRegister = new HashMap<>();
            requestRegister.put("name", fullName);
            requestRegister.put("email", email);
            requestRegister.put("phoneno", mobileNumber);
            requestRegister.put("password", password);
            requestRegister.put("cnfpsw", confirmPassword);

            Call call = apiCall.register(requestRegister);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    RegisterModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    RegisterModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validateOTPApi(final Context context, final String userId, final String otp) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Map<String, Object> requestOTPValidate = new HashMap<>();
            requestOTPValidate.put("user_id", userId);
            requestOTPValidate.put("otp", otp);


            Call call = apiCall.otpVerification(requestOTPValidate);
            call.enqueue(new Callback<OTPValidateResponse>() {
                @Override
                public void onResponse(Call<OTPValidateResponse> call, Response<OTPValidateResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    RegisterModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<OTPValidateResponse> call, Throwable t) {
                    RegisterModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resntOTPApi(final Context context, final String userId, final String otpType) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Map<String, Object> requestOTPResend = new HashMap<>();
            requestOTPResend.put("user_id", userId);
            requestOTPResend.put("otp_type", otpType);

            Call call = apiCall.resendOTP(requestOTPResend);
            call.enqueue(new Callback<OTPResendResponse>() {
                @Override
                public void onResponse(Call<OTPResendResponse> call, Response<OTPResendResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    RegisterModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<OTPResendResponse> call, Throwable t) {
                    RegisterModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
