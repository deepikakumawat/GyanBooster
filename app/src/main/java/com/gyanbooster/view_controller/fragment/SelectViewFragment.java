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
import com.gyanbooster.adapter.PopularVideoCourseCategoryAdapter;
import com.gyanbooster.adapter.SelectCourseAdapter;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.PermissionToViewResponse;
import com.gyanbooster.dao.course_category.PopularVideoData;
import com.gyanbooster.dao.course_category.PopularVideoResponse;
import com.gyanbooster.dao.select_courses.SelectCourseData;
import com.gyanbooster.dao.select_courses.SelectCourseResponse;
import com.gyanbooster.databinding.FragmentSelectCourseBinding;
import com.gyanbooster.model.SelectCourseModel;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;
import com.gyanbooster.utility.Util;
import com.gyanbooster.view_controller.GridSpacingItemDecoration;
import com.gyanbooster.view_controller.activities.DrawerBaseActivity;
import com.gyanbooster.view_controller.activities.LoginActivity;
import com.gyanbooster.view_controller.activities.PopularVideoActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SelectViewFragment extends BaseFragment implements View.OnClickListener, Observer {


    private SelectCourseModel selectCourseModel = new SelectCourseModel();
    private FragmentSelectCourseBinding fragmentSelectCourseBinding;
    private int numberOfColumns = 2;
    private SelectCourseAdapter selectCourseAdapter;
    private List<SelectCourseData> selectCourseDataArrayList = new ArrayList<>();
    int spacing = 30; // 50px
    boolean includeEdge = true;
    private View v;
    private SelectCourseData selectCourseData;
    private PopularVideoCourseCategoryAdapter popularVideoCourseCategoryAdapter;
    private ArrayList<PopularVideoData> popularDataArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentSelectCourseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_course, container, false);
        v = fragmentSelectCourseBinding.getRoot();
        selectCourseModel.addObserver(this);
        init();
        return v;
    }


    private void init() {
        Util.showProDialog(getActivity());
        selectCourseModel.selectApi(getActivity());
        selectCourseModel.popularVideoApi(getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        fragmentSelectCourseBinding.rvSelectCourse.setLayoutManager(gridLayoutManager);
        fragmentSelectCourseBinding.rvSelectCourse.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns, spacing, includeEdge));


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentSelectCourseBinding.rvPopularVideos.setLayoutManager(layoutManager);
    }


    @Override
    public void onClick(View view) {
        try {
            int vId = view.getId();
            switch (vId) {
                case R.id.lyCourse:
                    selectCourseData = (SelectCourseData) view.getTag();
                 /*   Intent intent = new Intent(context, DrawerBaseActivity.class);
                    intent.putExtra(SelectCourseData.SELECTCOURSE, selectCourseData);
                    context.startActivity(intent);*/

                    /**/

                    if (selectCourseData != null) {
                        selectCourseModel.permissionToViewApi(getActivity(), selectCourseData.getCourse_id());
                    }
                    break;
                case R.id.txtRyVideso:
                    PopularVideoData popularData = ((PopularVideoData) view.getTag());
                    if (popularData != null) {
                        Intent intent = new Intent(getActivity(), PopularVideoActivity.class);
                        intent.putExtra(PopularVideoData.POPULARVIDEODATA, popularData);
                        intent.putExtra(PopularVideoData.POPULARVIDEOLIST, popularDataArrayList);

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

    private void setPopularVideoAdapter() {
        popularVideoCourseCategoryAdapter = new PopularVideoCourseCategoryAdapter(getActivity(), popularDataArrayList, this);
        fragmentSelectCourseBinding.rvPopularVideos.setAdapter(popularVideoCourseCategoryAdapter);

    }

    @Override
    public void update(Observable observable, Object o) {
        Util.dismissProDialog();
        try {
            if (o != null) {
                if (o instanceof SelectCourseResponse) {
                    SelectCourseResponse selectCourseResponse = ((SelectCourseResponse) o);
                    selectCourseDataArrayList.clear();
                    selectCourseDataArrayList = selectCourseResponse.getAll_courses();
                    if (!selectCourseDataArrayList.isEmpty()) {
                        setAdapter();
                    }
                } else if (o instanceof PermissionToViewResponse) {
                    PermissionToViewResponse permissionToViewResponse = ((PermissionToViewResponse) o);
                    if (permissionToViewResponse.getStatus().equalsIgnoreCase(Constants.STATUS)) {
                        Fragment frag = new CourseCategoryFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(SelectCourseData.SELECTCOURSE, selectCourseData);
                        frag.setArguments(bundle);
                        ((DrawerBaseActivity) getActivity()).replaceFragment(frag);

                    } else {
                        Fragment frag = new CourseDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(SelectCourseData.SELECTCOURSE, selectCourseData);
                        frag.setArguments(bundle);
                        ((DrawerBaseActivity) getActivity()).replaceFragment(frag);

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
        selectCourseAdapter = new SelectCourseAdapter(getActivity(), selectCourseDataArrayList, this);
        fragmentSelectCourseBinding.rvSelectCourse.setAdapter(selectCourseAdapter);
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.select_your_course);
    }
}
