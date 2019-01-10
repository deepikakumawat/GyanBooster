package com.gyanbooster.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gyanbooster.R;
import com.gyanbooster.dao.course_details.CourseReviewsData;

import java.util.ArrayList;


/**
 * Created by Dell on 1/31/2018.
 */

public class CourseDetailsAdapter extends RecyclerView.Adapter<CourseDetailsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CourseReviewsData> courseReviewsDataArrayList;


    public CourseDetailsAdapter(Context context, ArrayList<CourseReviewsData> courseReviewsDataArrayList) {
        this.context = context;
        this.courseReviewsDataArrayList = courseReviewsDataArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_course_details_query, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CourseReviewsData data = courseReviewsDataArrayList.get(position);
        holder.txtName.setText(data.getFront_user_fname());
        holder.txtReviewMsg.setText(data.getReview_msg());
        holder.txtDate.setText(data.getReview_on());
        holder.rbReviewRating.setRating(Float.parseFloat(data.getRating()));

    }


    @Override
    public int getItemCount() {
        return courseReviewsDataArrayList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtReviewMsg;
        private TextView txtDate;
        private RatingBar rbReviewRating;


        public ViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            txtReviewMsg = view.findViewById(R.id.txtReviewMsg);
            txtDate = view.findViewById(R.id.txtDate);
            rbReviewRating = view.findViewById(R.id.rbReviewRating);


        }
    }
}
