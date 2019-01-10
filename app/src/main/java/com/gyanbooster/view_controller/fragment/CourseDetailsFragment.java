package com.gyanbooster.view_controller.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyanbooster.R;
import com.gyanbooster.adapter.CourseDetailsAdapter;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.course_category.SubCourses;
import com.gyanbooster.dao.course_details.AddReviewResponse;
import com.gyanbooster.dao.course_details.Course;
import com.gyanbooster.dao.course_details.CourseDetailsResponse;
import com.gyanbooster.dao.course_details.CourseReviewsData;
import com.gyanbooster.dao.course_listing.Topics;
import com.gyanbooster.dao.select_courses.SelectCourseData;
import com.gyanbooster.databinding.FragmentCourseDetailsBinding;
import com.gyanbooster.model.CourseDetailsModel;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;
import com.gyanbooster.utility.Util;
import com.gyanbooster.view_controller.activities.CheckoutActivity;
import com.gyanbooster.view_controller.activities.DrawerBaseActivity;
import com.gyanbooster.view_controller.activities.LoginActivity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static com.gyanbooster.utility.Util.showCenteredToast;


public class CourseDetailsFragment extends BaseFragment implements View.OnClickListener, Observer {

    View v;
    private CourseDetailsAdapter courseDetailsAdapter;
    private TextView txtBuyNow;
    private FragmentCourseDetailsBinding fragmentCourseDetailsBinding;
    private CourseDetailsModel courseDetailsModel = new CourseDetailsModel();
    private SelectCourseData selectCourseData;
    private Course course;
    private ArrayList<CourseReviewsData> courseReviewsDataArrayList = new ArrayList<>();
    private TextView txtSubmit;


    public static CourseDetailsFragment newInstance() {
        CourseDetailsFragment fragInstance = new CourseDetailsFragment();
        return fragInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentCourseDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_course_details, container, false);
        v = fragmentCourseDetailsBinding.getRoot();
        courseDetailsModel.addObserver(this);

        Bundle bundle = getArguments();

        selectCourseData = ((SelectCourseData) bundle.getSerializable(SelectCourseData.SELECTCOURSE));


        init();

        return v;
    }

    private void init() {
       getCourseDataFromAPI();

        txtBuyNow = v.findViewById(R.id.txtBuyNow);
        txtSubmit = v.findViewById(R.id.txtSubmit);
        txtBuyNow.setOnClickListener(this);
        txtSubmit.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        fragmentCourseDetailsBinding.rvCourseQuery.setLayoutManager(layoutManager);


    }

    private void getCourseDataFromAPI() {
        Util.showProDialog(getActivity());
        courseDetailsModel.courseDetailsApi(getActivity(), selectCourseData.getCourse_id());
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        try {
            int vId = view.getId();
            switch (vId) {
                case R.id.txtBuyNow:
                    if (TextUtils.isEmpty(GyanBoosterPreferences.getUserId())) {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), CheckoutActivity.class);
                        intent.putExtra(Course.COURSE, course);
                        startActivity(intent);
                    }

                    break;
                case R.id.txtSubmit:
                    String reviewMsg = fragmentCourseDetailsBinding.etxtReview.getText().toString();
                    float reviewRating = fragmentCourseDetailsBinding.rbSetRating.getRating() ;


                    if (isValid(reviewMsg,reviewRating)) {
                        courseDetailsModel.setReviewAPI(getActivity(), reviewMsg, String.valueOf(reviewRating), course);

                    }

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Util.dismissProDialog();
        try {
            if (o != null) {
                if (o instanceof CourseDetailsResponse) {
                    CourseDetailsResponse courseDetailsResponse = ((CourseDetailsResponse) o);
                    if (courseDetailsResponse != null) {
                        course = courseDetailsResponse.getCourses();
                        setData(courseDetailsResponse);
                        if (!courseDetailsResponse.getReviews().isEmpty()) {
                            courseReviewsDataArrayList.clear();
                            courseReviewsDataArrayList = courseDetailsResponse.getReviews();
                            setAdapter();
                        }
                    }

                } else if (o instanceof AddReviewResponse) {
                    AddReviewResponse addReviewResponse = ((AddReviewResponse) o);
                    if (Constants.STATUS.equalsIgnoreCase(addReviewResponse.getStatus())) {
                        Util.showCenteredToast(getActivity(), addReviewResponse.getMessage());
                        fragmentCourseDetailsBinding.etxtReview.getText().clear();
                        fragmentCourseDetailsBinding.rbSetRating.setRating(0);
                        getCourseDataFromAPI();
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        courseDetailsAdapter = new CourseDetailsAdapter(getActivity(), courseReviewsDataArrayList);
        fragmentCourseDetailsBinding.rvCourseQuery.setAdapter(courseDetailsAdapter);
    }

    private void setData(CourseDetailsResponse courseDetailsResponse) {
        fragmentCourseDetailsBinding.txtCourseName.setText(courseDetailsResponse.getCourses().getCourse_name());
        fragmentCourseDetailsBinding.txtOldPrice.setText(getString(R.string.Rs) + courseDetailsResponse.getCourses().getCourse_old_price());
        fragmentCourseDetailsBinding.txtOldPrice.setPaintFlags(fragmentCourseDetailsBinding.txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        fragmentCourseDetailsBinding.txtNewPrice.setText(courseDetailsResponse.getCourses().getCourse_price());
        fragmentCourseDetailsBinding.txtCourseDesc.setText(Html.fromHtml(courseDetailsResponse.getCourses().getCourse_desc()).toString());
        fragmentCourseDetailsBinding.rbCourse.setRating(Float.parseFloat(courseDetailsResponse.getAvg_review().getAVG_RATING()));

        String thumbnail = Constants.THUMBNAIL_BASE_URL + courseDetailsResponse.getCourses().getCourse_thumbnail();
        Glide.with(getActivity()).load(thumbnail).dontAnimate().into(fragmentCourseDetailsBinding.header);
    }

    @Override
    protected String getActionbarTitle() {
        return selectCourseData.getCourse_name();
    }

    private boolean isValid(String reviewMsg, float reviewRating) throws Exception {
        boolean validation_detials_flag = false;
        if (Util.isDeviceOnline(getActivity())) {
            if (TextUtils.isEmpty(reviewMsg)) {
                showCenteredToast(getActivity(), getString(R.string.review_msg_blank));
                fragmentCourseDetailsBinding.etxtReview.requestFocus();
            } else if (0==reviewRating) {
                showCenteredToast(getActivity(), getString(R.string.review_rating_blank));
            } else {
                validation_detials_flag = true;
            }
        } else {
            showCenteredToast(getActivity(), getString(R.string.network_connection));
        }
        return validation_detials_flag;
    }

}
