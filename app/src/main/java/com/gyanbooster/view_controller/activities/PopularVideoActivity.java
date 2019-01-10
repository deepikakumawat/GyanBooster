package com.gyanbooster.view_controller.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.gyanbooster.DownloadVideoService;
import com.gyanbooster.R;
import com.gyanbooster.adapter.PopularVideoCourseCategoryAdapter;
import com.gyanbooster.adapter.VideoAdapter;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.course_category.PopularVideoData;
import com.gyanbooster.dao.video.AllVideos;
import com.gyanbooster.dao.video.OneVideo;
import com.gyanbooster.dao.video.Queries;
import com.gyanbooster.dao.video.VideoResponse;
import com.gyanbooster.databinding.ActivityPopularVideoBinding;
import com.gyanbooster.databinding.ActivityVideoBinding;
import com.gyanbooster.model.VideoModel;
import com.gyanbooster.utility.Util;
import com.gyanbooster.youtube_video.Config;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, Observer, View.OnClickListener {

    private static final int RECOVERY_REQUEST = 1;
    private VideoAdapter videoAdapter;
    private ArrayList<Queries> queriesArrayList = new ArrayList<>();
    private ActivityPopularVideoBinding activityPopularVideoBinding;
    private PopularVideoCourseCategoryAdapter popularVideoCourseCategoryAdapter;
    private ArrayList<PopularVideoData> popularDataArrayList = new ArrayList<>();
    private YouTubePlayer youtubeplayer;


    private PopularVideoData popularVideoData;
//    private final YouTubeExtractor mExtractor = YouTubeExtractor.create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPopularVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_popular_video);
        activityPopularVideoBinding.setClick(this);
        init();
    }

    private void init() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            popularVideoData = (PopularVideoData) getIntent().getSerializableExtra(PopularVideoData.POPULARVIDEODATA); //Obtaining data
            popularDataArrayList = (ArrayList<PopularVideoData>) getIntent().getSerializableExtra(PopularVideoData.POPULARVIDEOLIST);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        activityPopularVideoBinding.rvPopularVideos.setLayoutManager(layoutManager);
        setPopularVideoAdapter();

        if (popularVideoData != null) {
            activityPopularVideoBinding.youtubeView.initialize(Config.YOUTUBE_API_KEY, this);

        }
        activityPopularVideoBinding.imgBack.setOnClickListener(this);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return activityPopularVideoBinding.youtubeView;
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            if (o != null) {


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {


        if (!wasRestored) {

            player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

            player.cueVideo(popularVideoData.getYoutube_id());
            youtubeplayer = player;
        }
    }


    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Util.showCenteredToast(this, error);
        }
    }


    @Override
    public void onClick(View view) {
        try {
            int vId = view.getId();
            switch (vId) {
                case R.id.imgBack:
                    onBackPressed();
                    break;
                case R.id.txtRyVideso:
                    popularVideoData = ((PopularVideoData) view.getTag());
                    if (popularVideoData != null) {
                        if (youtubeplayer != null) {
                            youtubeplayer.cueVideo(popularVideoData.getYoutube_id());
                            youtubeplayer.play();

                        }
                    }
                    break;
                case R.id.txtDownload:

                    String youtubeLink = "http://youtube.com/watch?v=" + popularVideoData.getYoutube_id();

                    YouTubeUriExtractor ytEx = new YouTubeUriExtractor(this) {
                        @Override
                        public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                            if (ytFiles != null) {
                                int itag = 22;
// Here you can get download url
                                String downloadUrl = ytFiles.get(itag).getUrl();
                                Log.d("downlodurl", downloadUrl);

                                activityPopularVideoBinding.progress.setIndeterminate(true);
                                Intent serviceIntent = new Intent(PopularVideoActivity.this, DownloadVideoService.class);
                                serviceIntent.putExtra("downloadUrl", downloadUrl);
                                serviceIntent.putExtra("videoTitle", videoTitle);
                                serviceIntent.putExtra("receiver", new DownloadReceiver(new Handler()));

                                startService(serviceIntent);

                            }
                        }
                    };

                    ytEx.execute(youtubeLink);
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class DownloadReceiver extends ResultReceiver {
        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == DownloadVideoService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");

                activityPopularVideoBinding.progress.setProgress(progress);
                activityPopularVideoBinding.txtProcess.setText(progress + "%");

                if (progress == 100) {
                    activityPopularVideoBinding.progress.setIndeterminate(false);

                    activityPopularVideoBinding.progress.setProgress(progress);
                }
            }
        }
    }


    private void setPopularVideoAdapter() {
        popularVideoCourseCategoryAdapter = new PopularVideoCourseCategoryAdapter(this, popularDataArrayList, PopularVideoActivity.this);
        activityPopularVideoBinding.rvPopularVideos.setAdapter(popularVideoCourseCategoryAdapter);

    }

}