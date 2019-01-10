package com.gyanbooster.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.course_category.PopularVideoData;
import com.gyanbooster.dao.video.AllVideos;
import com.gyanbooster.view_controller.activities.VideoActivity;
import com.gyanbooster.view_controller.fragment.CourseCategoryFragment;
import com.gyanbooster.view_controller.fragment.SelectViewFragment;
import com.gyanbooster.youtube_video.Config;

import java.util.ArrayList;


/**
 * Created by Dell on 1/31/2018.
 */

public class AllVideosAdapter extends RecyclerView.Adapter<AllVideosAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AllVideos> allVideosArrayList;
    private VideoActivity videoActivity;


    public AllVideosAdapter(Context context, ArrayList<AllVideos> allVideosArrayList, VideoActivity videoActivity) {
        this.context = context;
        this.allVideosArrayList = allVideosArrayList;
        this.videoActivity = videoActivity;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_all_video_course, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AllVideos data = allVideosArrayList.get(position);
        if (data != null) {
            String thumbnail = Constants.THUMBNAIL_BASE_URL + data.getVideo_thumb();
            Glide.with(context).load(thumbnail).dontAnimate().into(holder.imgVideo);




            holder.txtRyVideso.setTag(data);
            holder.txtRyVideso.setOnClickListener((videoActivity));

        }
    }


    @Override
    public int getItemCount() {
        return allVideosArrayList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgVideo;
        private RelativeLayout txtRyVideso;



        public ViewHolder(View view) {
            super(view);
            imgVideo = view.findViewById(R.id.imgVideo);
            txtRyVideso = view.findViewById(R.id.txtRyVideso);

        }
    }
}
