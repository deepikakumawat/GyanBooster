package com.gyanbooster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyanbooster.R;
import com.gyanbooster.dao.profile_response.UserCourseData;
import com.gyanbooster.view_controller.fragment.profile_fragment.FragmentYourCourse;

import java.util.ArrayList;


/**
 * Created by Dell on 1/31/2018.
 */

public class YourCourseAdapter extends RecyclerView.Adapter<YourCourseAdapter.ViewHolder> {
    private Context context;
    private ArrayList<UserCourseData> subCoursesArrayList;
    private FragmentYourCourse fragmentYourCourse;

    public YourCourseAdapter(Context context, ArrayList<UserCourseData> subCoursesArrayList, FragmentYourCourse fragmentYourCourse) {
        this.context = context;
        this.subCoursesArrayList = subCoursesArrayList;
        this.fragmentYourCourse = fragmentYourCourse;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_your_course, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserCourseData data = subCoursesArrayList.get(position);
        holder.txtCourseCategory.setText(data.getCourse_name());
        holder.lyCourse.setTag(data);
        holder.lyCourse.setOnClickListener((fragmentYourCourse));


    }


    @Override
    public int getItemCount() {
        return subCoursesArrayList.size();
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
