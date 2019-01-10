package com.gyanbooster.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.gyanbooster.R;
import com.gyanbooster.api_calling.APIClient;
import com.gyanbooster.api_calling.ApiInterface;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.course_details.Course;
import com.gyanbooster.dao.video.AddQueryResponse;
import com.gyanbooster.dao.video.VideoResponse;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;

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

public class VideoModel extends Observable {

    private String TAG = VideoModel.class.getSimpleName();

    public void videoApi(final Context context, String topicId) {

        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);

            Call call = apiCall.video(topicId);
            call.enqueue(new Callback<VideoResponse>() {
                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    VideoModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {
                    VideoModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setReviewAPI(final Context context, final String videoId, final String question, final String userFile,final String courseId) {


        try {
            ApiInterface apiCall = APIClient.getClient(URLs.BASE_URL).create(ApiInterface.class);
            Map<String, Object> requestLogin = new HashMap<>();
            requestLogin.put("user_id", GyanBoosterPreferences.getUserId());
            requestLogin.put("course_id", courseId);
            requestLogin.put("video_id", videoId);
            requestLogin.put("qus", question);
            requestLogin.put("userfile", userFile);
            Call call = apiCall.addQuery(requestLogin);
            call.enqueue(new Callback<AddQueryResponse>() {
                @Override
                public void onResponse(Call<AddQueryResponse> call, Response<AddQueryResponse> response) {
                    Log.d(TAG, call.request().url().toString());
                    VideoModel.this.setChanged();
                    notifyObservers(response.body());
                }

                @Override
                public void onFailure(Call<AddQueryResponse> call, Throwable t) {
                    VideoModel.this.setChanged();
                    notifyObservers(context.getResources().getString(R.string.somethingWentWrong));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
