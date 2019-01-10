package com.gyanbooster.view_controller.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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
import com.gyanbooster.DownloadVideoService;
import com.gyanbooster.R;
import com.gyanbooster.adapter.AllVideosAdapter;
import com.gyanbooster.adapter.VideoAdapter;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.course_category.CourseCategoryResponse;
import com.gyanbooster.dao.course_category.PopularVideoData;
import com.gyanbooster.dao.course_category.SubCourses;
import com.gyanbooster.dao.profile_response.UserCourseData;
import com.gyanbooster.dao.select_courses.SelectCourseData;
import com.gyanbooster.dao.video.AddQueryResponse;
import com.gyanbooster.dao.video.AllVideos;
import com.gyanbooster.dao.video.OneVideo;
import com.gyanbooster.dao.video.Queries;
import com.gyanbooster.dao.video.Topics;
import com.gyanbooster.dao.video.VideoResponse;
import com.gyanbooster.databinding.ActivityVideoBinding;
import com.gyanbooster.model.VideoModel;
import com.gyanbooster.utility.Util;
import com.gyanbooster.youtube_video.Config;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, Observer, View.OnClickListener {

    private static final int RECOVERY_REQUEST = 1;
    private VideoAdapter videoAdapter;
    private VideoModel videoModel = new VideoModel();
    private ArrayList<Queries> queriesArrayList = new ArrayList<>();
    private ActivityVideoBinding activityVideoBinding;
    private OneVideo oneVideo;
    private ArrayList<AllVideos> allVideosArrayList = new ArrayList<>();
    private MyPlayerStateChangeListener playerStateChangeListener;
    private MyPlaybackEventListener playbackEventListener;
    private com.gyanbooster.dao.course_listing.Topics topic;
    private AllVideosAdapter allVideosAdapter;
    private AllVideos allVideos;
    private YouTubePlayer youtubeplayer;
    private String videoPlayURL = "";
    private SelectCourseData selectCourseData;
    private UserCourseData userCourseData;
    private SubCourses subCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        activityVideoBinding.setClick(this);
        videoModel.addObserver(this);
        init();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            topic = (com.gyanbooster.dao.course_listing.Topics) getIntent().getSerializableExtra(com.gyanbooster.dao.course_listing.Topics.TOPICS); //Obtaining data
            if (extras.containsKey(SelectCourseData.SELECTCOURSE)) {
                selectCourseData = ((SelectCourseData) getIntent().getSerializableExtra(SelectCourseData.SELECTCOURSE));
            } else if (extras.containsKey(UserCourseData.USERCOURSEDATA)) {
                userCourseData = ((UserCourseData) getIntent().getSerializableExtra(UserCourseData.USERCOURSEDATA));
            }

            if (extras.containsKey(SubCourses.SUBCOURSEDATA)) {
                subCourses = ((SubCourses) getIntent().getSerializableExtra(SubCourses.SUBCOURSEDATA));

            }

        }
        getVideoDataFromAPI();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activityVideoBinding.rvCourseVideo.setLayoutManager(layoutManager);
        activityVideoBinding.imgBack.setOnClickListener(this);

        LinearLayoutManager layoutManagerAllVideo = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        activityVideoBinding.rvAllVideo.setLayoutManager(layoutManagerAllVideo);

        if (selectCourseData != null) {
            activityVideoBinding.txtCourse.setText(selectCourseData.getCourse_name());
        }else if(userCourseData !=null){
            activityVideoBinding.txtCourse.setText(userCourseData.getCourse_name());

        }

        activityVideoBinding.txtSubCourse.setText(subCourses.getSub_course_name());


    }

    private void getVideoDataFromAPI() {
        Util.showProDialog(this);
        videoModel.videoApi(this, topic.getTopic_id());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return activityVideoBinding.youtubeView;
    }

    @Override
    public void update(Observable observable, Object o) {
        Util.dismissProDialog();
        try {
            if (o != null) {
                if (o instanceof VideoResponse) {
                    VideoResponse videoResponse = ((VideoResponse) o);
                    queriesArrayList.clear();
                    queriesArrayList = videoResponse.getQueries();
                    if (!queriesArrayList.isEmpty()) {
                        setAdapter();
                    }
                    if (videoResponse.getOne_video() != null) {
                        oneVideo = videoResponse.getOne_video();
                        activityVideoBinding.youtubeView.initialize(Config.YOUTUBE_API_KEY, this);
                        playerStateChangeListener = new MyPlayerStateChangeListener();
                        playbackEventListener = new MyPlaybackEventListener();

                    }
                    if (videoResponse.getAll_videos() != null) {
                        allVideosArrayList.clear();
                        allVideosArrayList = videoResponse.getAll_videos();
                        setAllVideoAdapter();

                    }
                } else if (o instanceof AddQueryResponse) {
                    AddQueryResponse addQueryResponse = ((AddQueryResponse) o);
                    if (Constants.STATUS.equalsIgnoreCase(addQueryResponse.getStatus())) {

                        getVideoDataFromAPI();
                        Util.showCenteredToast(this, addQueryResponse.getMessage());
                        if (!TextUtils.isEmpty(activityVideoBinding.etQuery.getText().toString())) {
                            activityVideoBinding.etQuery.getText().clear();
                        }

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        if (!wasRestored) {

            player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

            if (oneVideo != null) {
                videoPlayURL = oneVideo.getYoutube_url();
                player.cueVideo(oneVideo.getYoutube_url()); // loadVideo() will auto play video,                    // Use cueVideo() method, if you don't want to play it automatically
            } else if (allVideos != null) {
                videoPlayURL = allVideos.getYoutube_url();

                player.cueVideo(allVideos.getYoutube_url());
            }
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

    private void setAllVideoAdapter() {
        allVideosAdapter = new AllVideosAdapter(this, allVideosArrayList, VideoActivity.this);
        activityVideoBinding.rvAllVideo.setAdapter(allVideosAdapter);
    }

    private void setAdapter() {
        videoAdapter = new VideoAdapter(this, queriesArrayList);
        activityVideoBinding.rvCourseVideo.setAdapter(videoAdapter);
    }

    @Override
    public void onClick(View view) {
        try {
            int vId = view.getId();
            switch (vId) {
                case R.id.imgBack:
                    onBackPressed();
                    break;
                case R.id.imgSendIcon:
                    String question = activityVideoBinding.etQuery.getText().toString();
                    if (!TextUtils.isEmpty(question)) {
                        Util.showProDialog(this);
                        videoModel.setReviewAPI(this, oneVideo.getVideo_id(), question, "", topic.getCourse_id());
                    } else {
                        Util.showCenteredToast(this, getString(R.string.query_message));
                    }


                    break;
                case R.id.txtRyVideso:
                    allVideos = ((AllVideos) view.getTag());
                    if (allVideos != null) {

                        if (youtubeplayer != null) {
                            videoPlayURL = allVideos.getYoutube_url();
                            youtubeplayer.cueVideo(allVideos.getYoutube_url());
                            youtubeplayer.play();

                        }
                    }
                    break;
                case R.id.txtDownload:

                    String youtubeLink = "http://youtube.com/watch?v=" + videoPlayURL;


                    YouTubeUriExtractor ytEx = new YouTubeUriExtractor(this) {
                        @Override
                        public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                            if (ytFiles != null) {
                                int itag = 22;
                                // Here you can get download url
                                String downloadUrl = ytFiles.get(itag).getUrl();
                                Log.d("downlodurl", downloadUrl);


                                activityVideoBinding.progress.setIndeterminate(true);
                                Intent serviceIntent = new Intent(VideoActivity.this, DownloadVideoService.class);
                                serviceIntent.putExtra("downloadUrl", downloadUrl);
                                serviceIntent.putExtra("videoTitle", videoTitle);
                                serviceIntent.putExtra("receiver", new VideoActivity.DownloadReceiver(new Handler()));

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

                activityVideoBinding.progress.setProgress(progress);
                activityVideoBinding.txtProcess.setText(progress + "%");

                if (progress == 100) {
                    activityVideoBinding.progress.setIndeterminate(false);

                    activityVideoBinding.progress.setProgress(progress);
                }
            }
        }
    }


    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {
            // Called when playback starts, either due to user action or call to play().
        }

        @Override
        public void onPaused() {
            // Called when playback is paused, either due to user action or call to pause().
        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.
        }

        @Override
        public void onBuffering(boolean b) {
            // Called when buffering starts or ends.
        }

        @Override
        public void onSeekTo(int i) {
            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.
        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.


        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Util.showCenteredToast(VideoActivity.this, error);
        }
    }
}