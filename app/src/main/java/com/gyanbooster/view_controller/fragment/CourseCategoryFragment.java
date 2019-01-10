package com.gyanbooster.view_controller.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyanbooster.R;
import com.gyanbooster.adapter.CourseCategoryAdapter;
import com.gyanbooster.adapter.PopularVideoCourseCategoryAdapter;
import com.gyanbooster.dao.course_category.CourseCategoryResponse;
import com.gyanbooster.dao.course_category.FeaturesCourseData;
import com.gyanbooster.dao.course_category.FeatureCourseResponse;
import com.gyanbooster.dao.course_category.PopularVideoData;
import com.gyanbooster.dao.course_category.PopularVideoResponse;
import com.gyanbooster.dao.course_category.SubCourses;
import com.gyanbooster.dao.profile_response.UserCourseData;
import com.gyanbooster.dao.select_courses.SelectCourseData;
import com.gyanbooster.databinding.FragmentCourseCategoryBinding;
import com.gyanbooster.model.CourseCategoryModel;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;
import com.gyanbooster.utility.Util;
import com.gyanbooster.view_controller.GridSpacingItemDecoration;
import com.gyanbooster.view_controller.activities.DrawerBaseActivity;
import com.gyanbooster.view_controller.activities.LoginActivity;
import com.gyanbooster.view_controller.activities.PopularVideoActivity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class CourseCategoryFragment extends BaseFragment implements Observer, View.OnClickListener {

    View v;
    private int numberOfColumns = 2;
    private CourseCategoryAdapter courseCategoryAdapter;
    private PopularVideoCourseCategoryAdapter popularVideoCourseCategoryAdapter;
    private FragmentCourseCategoryBinding courseCategoryBinding;
    private CourseCategoryModel courseCategoryModel = new CourseCategoryModel();
    private SelectCourseData selectCourseData;
    private UserCourseData userCourseData;
    private ArrayList<SubCourses> subCoursesArrayList = new ArrayList<>();
    private ArrayList<PopularVideoData> popularDataArrayList = new ArrayList<>();
    int spacing = 30; // 50px
    boolean includeEdge = true;

    public static CourseCategoryFragment newInstance() {
        CourseCategoryFragment fragInstance = new CourseCategoryFragment();

        return fragInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        courseCategoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_course_category, container, false);
        v = courseCategoryBinding.getRoot();
        courseCategoryModel.addObserver(this);


        Bundle bundle = getArguments();
        if (bundle.containsKey(SelectCourseData.SELECTCOURSE)) {
            selectCourseData = ((SelectCourseData) bundle.getSerializable(SelectCourseData.SELECTCOURSE));

        } else if (bundle.containsKey(UserCourseData.USERCOURSEDATA)) {
            userCourseData = ((UserCourseData) bundle.getSerializable(UserCourseData.USERCOURSEDATA));

        }


        init();

        return v;
    }

    private void init() {

        if (selectCourseData != null) {
            courseCategoryBinding.txtCourseName.setText(selectCourseData.getCourse_name());

        } else if (userCourseData != null) {
            courseCategoryBinding.txtCourseName.setText(userCourseData.getCourse_name());

        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        courseCategoryBinding.rvCourseCategory.setLayoutManager(gridLayoutManager);
        courseCategoryBinding.rvCourseCategory.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns, spacing, includeEdge));


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        courseCategoryBinding.rvPopularVideos.setLayoutManager(layoutManager);

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (courseCategoryModel != null) {
                Util.showProDialog(getActivity());

                if (selectCourseData != null) {
                    courseCategoryModel.courseCategoryApi(getActivity(), Integer.parseInt(selectCourseData.getCourse_id()));

                } else if (userCourseData != null) {
                    courseCategoryModel.courseCategoryApi(getActivity(), Integer.parseInt(userCourseData.getP_course_id()));

                }


                courseCategoryModel.popularVideoApi(getActivity());
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
                if (o instanceof CourseCategoryResponse) {
                    CourseCategoryResponse courseCategoryResponse = ((CourseCategoryResponse) o);
                    subCoursesArrayList.clear();
                    subCoursesArrayList = courseCategoryResponse.getSub_courses();
                    if (!subCoursesArrayList.isEmpty()) {
                        setAdapter();
                    }
                } else if (o instanceof PopularVideoResponse) {
                    PopularVideoResponse popularVideosResponse = ((PopularVideoResponse) o);
                    popularDataArrayList.clear();
                    popularDataArrayList = popularVideosResponse.getPro_videos();
                    if (!popularDataArrayList.isEmpty()) {
                        setPopularVideoAdapter();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        courseCategoryAdapter = new CourseCategoryAdapter(getActivity(), subCoursesArrayList, this);
        courseCategoryBinding.rvCourseCategory.setAdapter(courseCategoryAdapter);
    }

    private void setPopularVideoAdapter() {
        popularVideoCourseCategoryAdapter = new PopularVideoCourseCategoryAdapter(getActivity(), popularDataArrayList, this);
        courseCategoryBinding.rvPopularVideos.setAdapter(popularVideoCourseCategoryAdapter);

    }

    @Override
    public void onClick(View view) {
        try {
            int vId = view.getId();
            switch (vId) {
                case R.id.lyCourse:
                    SubCourses subCourses = ((SubCourses) view.getTag());
                    if (subCourses != null) {
                        Fragment frag = new CourseListingFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(SubCourses.SUBCOURSEDATA, subCourses);
                        bundle.putSerializable(SelectCourseData.SELECTCOURSE, selectCourseData);
                        bundle.putSerializable(UserCourseData.USERCOURSEDATA, userCourseData);
                        frag.setArguments(bundle);
                        ((DrawerBaseActivity) getActivity()).replaceFragment(frag);
                    }
                    break;
                case R.id.txtRyVideso:
                    PopularVideoData popularData = ((PopularVideoData) view.getTag());
                    if (popularData != null) {
                        Intent intent = new Intent(getActivity(), PopularVideoActivity.class);
                        intent.putExtra(PopularVideoData.POPULARVIDEODATA, popularData);
                        startActivity(intent);
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
    protected String getActionbarTitle() {
        return getString(R.string.screen_title_category);
    }
}
