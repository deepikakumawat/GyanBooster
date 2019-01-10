package com.gyanbooster.dao.course_category;



import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dell on 1/31/2018.
 */

public class PopularVideoResponse implements Serializable {



    private ArrayList<PopularVideoData> pro_videos;

    public ArrayList<PopularVideoData> getPro_videos() {
        return pro_videos;
    }

    public void setPro_videos(ArrayList<PopularVideoData> pro_videos) {
        this.pro_videos = pro_videos;
    }
}
