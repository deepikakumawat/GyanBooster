package com.gyanbooster.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.course_listing.Topics;
import com.gyanbooster.dao.select_courses.SelectCourseData;
import com.gyanbooster.view_controller.activities.DrawerBaseActivity;
import com.gyanbooster.view_controller.fragment.CourseCategoryFragment;
import com.gyanbooster.view_controller.fragment.CourseDetailsFragment;
import com.gyanbooster.view_controller.fragment.SelectViewFragment;

import java.util.List;


/**
 * Created by Dell on 1/31/2018.
 */

public class SelectCourseAdapter extends RecyclerView.Adapter<SelectCourseAdapter.ViewHolder> {
    private Context context;
    private List<SelectCourseData> selectCourseDataArrayList;
    private SelectViewFragment selectViewFragment;

    public SelectCourseAdapter(Context context, List<SelectCourseData> selectCourseDataArrayList, SelectViewFragment selectViewFragment) {
        this.context = context;
        this.selectCourseDataArrayList = selectCourseDataArrayList;
        this.selectViewFragment = selectViewFragment;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_select_course_items, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SelectCourseData data = selectCourseDataArrayList.get(position);
        holder.txtCourse.setText(data.getCourse_name());

        String thumbnail = Constants.THUMBNAIL_BASE_URL + data.getCourse_thumbnail();
        Glide.with(context).load(thumbnail).placeholder(R.mipmap.logo).dontAnimate().into(holder.imgCourse);

        holder.lyCourse.setTag(data);
        holder.lyCourse.setOnClickListener((selectViewFragment));

    }


    @Override
    public int getItemCount() {
        return selectCourseDataArrayList.size();

    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCourse;
        private LinearLayout lyCourse;
        private ImageView imgCourse;


        public ViewHolder(View view) {
            super(view);
            txtCourse = view.findViewById(R.id.txtCourse);
            lyCourse = view.findViewById(R.id.lyCourse);
            imgCourse = view.findViewById(R.id.imgCourse);



        }
    }
}
