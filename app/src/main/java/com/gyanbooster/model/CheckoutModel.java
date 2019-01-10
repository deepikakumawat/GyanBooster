package com.gyanbooster.model;

import android.content.Context;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.checkout.ApplyCouponResponse;
import com.gyanbooster.dao.checkout.PaymentResponse;
import com.gyanbooster.dao.course_details.Course;
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

public class CheckoutModel extends Observable {

    private String TAG = CheckoutModel.class.getSimpleName();


    public void applyCouponApi(final Context context, final Course course,  final String wallet,  final String couponCode) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);
            Map<String, Object> requestLogin = new HashMap<>();
            requestLogin.put("user_id", GyanBoosterPreferences.getUserId());
            requestLogin.put("course_id", course.getCourse_id());
            requestLogin.put("wallet_applied_status", 0);
            requestLogin.put("coupon_code", couponCode);


            Call call = apiCall.applyCoupontAPi(requestLogin);
            call.enqueue(new Callback<ApplyCouponResponse>() {
                @Override
                public void onResponse(Call<ApplyCouponResponse> call, Response<ApplyCouponResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    CheckoutModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<ApplyCouponResponse> call, Throwable t) {
                    CheckoutModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
