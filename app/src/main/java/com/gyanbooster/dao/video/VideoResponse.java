package com.gyanbooster.dao.video;



import java.util.ArrayList;

public class VideoResponse {

    private Topics topic;
    private OneVideo one_video;
    private ArrayList<AllVideos> all_videos;
    private ArrayList<Queries> queries;

    public Topics getTopic() {
        return topic;
    }

    public void setTopic(Topics topic) {
        this.topic = topic;
    }

    public OneVideo getOne_video() {
        return one_video;
    }

    public void setOne_video(OneVideo one_video) {
        this.one_video = one_video;
    }

    public ArrayList<AllVideos> getAll_videos() {
        return all_videos;
    }

    public void setAll_videos(ArrayList<AllVideos> all_videos) {
        this.all_videos = all_videos;
    }

    public ArrayList<Queries> getQueries() {
        return queries;
    }

    public void setQueries(ArrayList<Queries> queries) {
        this.queries = queries;
    }
}
