package com.gyanbooster.adapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyanbooster.R;
import com.gyanbooster.dao.course_listing.Topics;
import com.gyanbooster.view_controller.activities.DrawerBaseActivity;
import com.gyanbooster.view_controller.fragment.CourseDetailsFragment;
import com.gyanbooster.view_controller.fragment.CourseListingFragment;

import java.util.ArrayList;


/**
 * Created by Dell on 1/31/2018.
 */

public class CourseListingAdapter extends RecyclerView.Adapter<CourseListingAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Topics> topicsArrayList;
    private CourseListingFragment courseListingFragment;

    public CourseListingAdapter(Context context, ArrayList<Topics> topicsArrayList, CourseListingFragment courseListingFragment) {
        this.context = context;
        this.topicsArrayList = topicsArrayList;
        this.courseListingFragment = courseListingFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_listing, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Topics data = topicsArrayList.get(position);
        holder.txtCourseCategory.setText(data.getTopic_name());
        holder.lyCourse.setTag(data);
        holder.lyCourse.setOnClickListener((courseListingFragment));


    }


    @Override
    public int getItemCount() {
        return topicsArrayList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCourseCategory;
        private LinearLayout lyCourse;


        public ViewHolder(View view) {
            super(view);
            txtCourseCategory = view.findViewById(R.id.txtCourseCategory);
            lyCourse = view.findViewById(R.id.lyCourse);


        }
    }
}
