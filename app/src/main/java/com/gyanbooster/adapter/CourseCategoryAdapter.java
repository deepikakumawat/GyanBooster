package com.gyanbooster.adapter;

import android.content.Context;
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
import com.gyanbooster.dao.course_category.SubCourses;
import com.gyanbooster.view_controller.fragment.CourseCategoryFragment;

import java.util.ArrayList;


/**
 * Created by Dell on 1/31/2018.
 */

public class CourseCategoryAdapter extends RecyclerView.Adapter<CourseCategoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SubCourses> subCoursesArrayList;
    private CourseCategoryFragment courseCategoryFragment;

    public CourseCategoryAdapter(Context context, ArrayList<SubCourses> subCoursesArrayList, CourseCategoryFragment courseCategoryFragment) {
        this.context = context;
        this.subCoursesArrayList = subCoursesArrayList;
        this.courseCategoryFragment = courseCategoryFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_category, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubCourses data = subCoursesArrayList.get(position);
        holder.txtCourseCategory.setText(data.getSub_course_name());

       /* String thumbnail = Constants.THUMBNAIL_BASE_URL + data.getSub_course_banner();
        Glide.with(context).load(thumbnail).placeholder(R.mipmap.logo).dontAnimate().into(holder.imgCourseCategory);
*/

        holder.lyCourse.setTag(data);
        holder.lyCourse.setOnClickListener((courseCategoryFragment));


    }


    @Override
    public int getItemCount() {
        return subCoursesArrayList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCourseCategory;
        private ImageView imgCourseCategory;
        private LinearLayout lyCourse;


        public ViewHolder(View view) {
            super(view);
            txtCourseCategory = view.findViewById(R.id.txtCourseCategory);
            imgCourseCategory = view.findViewById(R.id.imgCourseCategory);
            lyCourse = view.findViewById(R.id.lyCourse);


        }
    }
}
