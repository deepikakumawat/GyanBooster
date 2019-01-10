package com.gyanbooster.model;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.profile_response.DashboardResponse;
import com.gyanbooster.dao.profile_response.ProfileDetailResponse;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 1/31/2018.
 */

public class ProfileDetailsModel extends Observable {

    private String TAG = ProfileDetailsModel.class.getSimpleName();

    public void profileDetailsApi(final Context context, final String fName, final String lName, final String email, final String mob, final String password, final File profileImg, final String profileFileName,final String oldImage) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);
           /* Map<String, Object> requestLogin = new HashMap<>();
            requestLogin.put("user_id", GyanBoosterPreferences.getUserId());
            requestLogin.put("fname", fName);
            requestLogin.put("lname", lName);
            requestLogin.put("email", email);
            requestLogin.put("mob", mob);
            requestLogin.put("password", password);
            requestLogin.put("profile_img", "");
            requestLogin.put("old_image", oldImage);
            Call call = apiCall.profileDetailsAPI(requestLogin);*/


            Call call = apiCall.profileDetailsAPI(
                    RequestBody.create(MediaType.parse("multipart/form-data"), GyanBoosterPreferences.getUserId()),
                    RequestBody.create(MediaType.parse("multipart/form-data"), fName),
                    RequestBody.create(MediaType.parse("multipart/form-data"), lName),
                    RequestBody.create(MediaType.parse("multipart/form-data"), email),
                    RequestBody.create(MediaType.parse("multipart/form-data"), mob),
                    RequestBody.create(MediaType.parse("multipart/form-data"), password),
                    MultipartBody.Part.createFormData("profile_img", profileFileName, RequestBody.create(MediaType.parse("image/*"), profileImg)),
                    RequestBody.create(MediaType.parse("multipart/form-data"), oldImage)
                   );

            call.enqueue(new Callback<ProfileDetailResponse>() {
                @Override
                public void onResponse(Call<ProfileDetailResponse> call, Response<ProfileDetailResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    ProfileDetailsModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<ProfileDetailResponse> call, Throwable t) {
                    ProfileDetailsModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardApi(final Context context) {
        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Call call = apiCall.dashboard(GyanBoosterPreferences.getUserId());
            call.enqueue(new Callback<DashboardResponse>() {
                @Override
                public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    ProfileDetailsModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<DashboardResponse> call, Throwable t) {
                    ProfileDetailsModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
