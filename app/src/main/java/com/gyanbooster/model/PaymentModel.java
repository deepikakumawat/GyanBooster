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

public class PaymentModel extends Observable {

    private String TAG = PaymentModel.class.getSimpleName();

    public void paymentApi(final Context context, final Course course, final String txnid, final String status, final String wallet, final String gstNum, final String couponCode) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);
            Map<String, Object> requestLogin = new HashMap<>();
            requestLogin.put("user_id", GyanBoosterPreferences.getUserId());
            requestLogin.put("course_id", course.getCourse_id());
            requestLogin.put("txnid", "34234");
            requestLogin.put("amount", course.getCourse_price());
            requestLogin.put("status", status);
            requestLogin.put("wallet", wallet);
            requestLogin.put("gst_num", gstNum);
            requestLogin.put("coupon_code", couponCode);


            Call call = apiCall.paymentAPi(requestLogin);
            call.enqueue(new Callback<PaymentResponse>() {
                @Override
                public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    PaymentModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<PaymentResponse> call, Throwable t) {
                    PaymentModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
