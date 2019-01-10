package com.gyanbooster.view_controller.fragment.profile_fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyanbooster.R;
import com.gyanbooster.adapter.YourCourseAdapter;
import com.gyanbooster.dao.course_category.SubCourses;
import com.gyanbooster.dao.profile_response.UserCourseData;
import com.gyanbooster.dao.profile_response.YourCourseResponse;
import com.gyanbooster.dao.select_courses.SelectCourseData;
import com.gyanbooster.databinding.FragmentYourCoursesBinding;
import com.gyanbooster.model.YourCourseModel;
import com.gyanbooster.utility.Util;
import com.gyanbooster.view_controller.GridSpacingItemDecoration;
import com.gyanbooster.view_controller.activities.DrawerBaseActivity;
import com.gyanbooster.view_controller.fragment.BaseFragment;
import com.gyanbooster.view_controller.fragment.CourseCategoryFragment;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class FragmentYourCourse extends BaseFragment implements Observer, View.OnClickListener {

    private FragmentYourCoursesBinding fragmentYourCoursesBinding;
    private View v;
    private YourCourseModel yourCourseModel = new YourCourseModel();
    private YourCourseAdapter yourCourseAdapter;
    private ArrayList<UserCourseData> userCourseDataArrayList = new ArrayList<>();
    int spacing = 30; // 50px
    boolean includeEdge = true;
    private int numberOfColumns = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentYourCoursesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_your_courses, container, false);
        v = fragmentYourCoursesBinding.getRoot();
        yourCourseModel.addObserver(this);

        Util.showProDialog(getActivity());
        yourCourseModel.yourCourseApi(getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        fragmentYourCoursesBinding.rvYourCourse.setLayoutManager(gridLayoutManager);
        fragmentYourCoursesBinding.rvYourCourse.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns, spacing, includeEdge));

        return v;
    }


    @Override
    public void update(Observable observable, Object o) {
        Util.dismissProDialog();
        try {
            if (o != null) {
                YourCourseResponse yourCourseResponse = ((YourCourseResponse) o);
                userCourseDataArrayList.clear();
                userCourseDataArrayList = yourCourseResponse.getUser_courses();
                if (!userCourseDataArrayList.isEmpty()) {
                    setAdapter();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        yourCourseAdapter = new YourCourseAdapter(getActivity(), userCourseDataArrayList, this);
        fragmentYourCoursesBinding.rvYourCourse.setAdapter(yourCourseAdapter);
    }

    @Override
    public void onClick(View v) {
        try {
            int vId = v.getId();
            switch (vId) {
                case R.id.lyCourse:
                    UserCourseData userCourseData = ((UserCourseData) v.getTag());
                    if (userCourseData != null) {
                        Fragment frag = new CourseCategoryFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(UserCourseData.USERCOURSEDATA, userCourseData);
                        frag.setArguments(bundle);
                        ((DrawerBaseActivity) getActivity()).replaceFragment(frag);
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
        return getString(R.string.profile);
    }
}
