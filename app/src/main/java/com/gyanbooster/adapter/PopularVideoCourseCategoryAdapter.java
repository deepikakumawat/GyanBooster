package com.gyanbooster.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.course_category.FeaturesCourseData;
import com.gyanbooster.dao.course_category.PopularVideoData;
import com.gyanbooster.view_controller.activities.PopularVideoActivity;
import com.gyanbooster.view_controller.fragment.CourseCategoryFragment;
import com.gyanbooster.view_controller.fragment.SelectViewFragment;

import java.util.ArrayList;


/**
 * Created by Dell on 1/31/2018.
 */

public class PopularVideoCourseCategoryAdapter extends RecyclerView.Adapter<PopularVideoCourseCategoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PopularVideoData> popularDataArrayList;
    private CourseCategoryFragment courseCategoryFragment;
    private SelectViewFragment selectViewFragment;
    private PopularVideoActivity popularVideoActivity;


    public PopularVideoCourseCategoryAdapter(Context context, ArrayList<PopularVideoData> popularDataArrayList, CourseCategoryFragment courseCategoryFragment) {
        this.context = context;
        this.popularDataArrayList = popularDataArrayList;
        this.courseCategoryFragment = courseCategoryFragment;

    }

    public PopularVideoCourseCategoryAdapter(Context context, ArrayList<PopularVideoData> popularDataArrayList, SelectViewFragment selectViewFragment) {
        this.context = context;
        this.popularDataArrayList = popularDataArrayList;
        this.selectViewFragment = selectViewFragment;
    }

    public PopularVideoCourseCategoryAdapter(Context context, ArrayList<PopularVideoData> popularDataArrayList, PopularVideoActivity popularVideoActivity) {
        this.context = context;
        this.popularDataArrayList = popularDataArrayList;
        this.popularVideoActivity = popularVideoActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_popular_video_course_category, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PopularVideoData data = popularDataArrayList.get(position);
        if (data != null) {
            String thumbnail = Constants.THUMBNAIL_BASE_URL + data.getImg_url();
            Glide.with(context).load(thumbnail).dontAnimate().into(holder.imgVideo);

            holder.txtRyVideso.setTag(data);
            if (selectViewFragment != null) {
                holder.txtRyVideso.setOnClickListener((selectViewFragment));
            } else if (courseCategoryFragment != null) {
                holder.txtRyVideso.setOnClickListener((courseCategoryFragment));
            } else if (popularVideoActivity != null) {
                holder.txtRyVideso.setOnClickListener((popularVideoActivity));
            }
        }
    }


    @Override
    public int getItemCount() {
        return popularDataArrayList.size();
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
