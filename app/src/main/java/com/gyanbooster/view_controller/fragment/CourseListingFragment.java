package com.gyanbooster.view_controller.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyanbooster.R;
import com.gyanbooster.adapter.CourseListingAdapter;
import com.gyanbooster.dao.course_category.SubCourses;
import com.gyanbooster.dao.course_listing.CourseListingResponse;
import com.gyanbooster.dao.course_listing.Topics;
import com.gyanbooster.dao.profile_response.UserCourseData;
import com.gyanbooster.dao.select_courses.SelectCourseData;
import com.gyanbooster.dao.select_courses.SelectCourseResponse;
import com.gyanbooster.databinding.FragmentCourseListingBinding;
import com.gyanbooster.model.CourseListingModel;
import com.gyanbooster.view_controller.GridSpacingItemDecoration;
import com.gyanbooster.view_controller.activities.DrawerBaseActivity;
import com.gyanbooster.view_controller.activities.VideoActivity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class CourseListingFragment extends BaseFragment implements Observer {

    View v;
    private int numberOfColumns = 2;
    private CourseListingAdapter courseListingAdapter;
    private CourseListingModel courseListingModel = new CourseListingModel();
    private FragmentCourseListingBinding fragmentCourseListingBinding;
    private SubCourses subCourses;
    private ArrayList<Topics> topicsArrayList = new ArrayList<>();
    int spacing = 30; // 50px
    boolean includeEdge = true;
    private SelectCourseData selectCourseData;
    private UserCourseData userCourseData;

    public static CourseListingFragment newInstance() {
        CourseListingFragment fragInstance = new CourseListingFragment();

        return fragInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentCourseListingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_course_listing, container, false);
        v = fragmentCourseListingBinding.getRoot();
        courseListingModel.addObserver(this);

        Bundle bundle = getArguments();
        if (bundle.containsKey(SelectCourseData.SELECTCOURSE)) {
            selectCourseData = ((SelectCourseData) bundle.getSerializable(SelectCourseData.SELECTCOURSE));

        } else if (bundle.containsKey(UserCourseData.USERCOURSEDATA)) {
            userCourseData = ((UserCourseData) bundle.getSerializable(UserCourseData.USERCOURSEDATA));

        }
        subCourses = ((SubCourses) bundle.getSerializable(SubCourses.SUBCOURSEDATA));

        init();
        return v;
    }

    private void init() {

        courseListingModel.courseListingyApi(getActivity(), (subCourses.getGb_sub_course_id()));
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        fragmentCourseListingBinding.rvCourseListing.setLayoutManager(layoutManager);
      /*  DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        fragmentCourseListingBinding.rvCourseListing.addItemDecoration(itemDecoration);*/
        fragmentCourseListingBinding.rvCourseListing.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns, spacing, includeEdge));


        fragmentCourseListingBinding.txtTitle.setText(!TextUtils.isEmpty(subCourses.getSub_course_name()) ? subCourses.getSub_course_name() : "");
    }


    @Override
    public void update(Observable observable, Object o) {
        try {
            if (o != null) {
                CourseListingResponse courseListingResponse = ((CourseListingResponse) o);
                topicsArrayList.clear();
                topicsArrayList = courseListingResponse.getTopics();
                if (!topicsArrayList.isEmpty()) {
                    setAdapter();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        courseListingAdapter = new CourseListingAdapter(getActivity(), topicsArrayList, this);
        fragmentCourseListingBinding.rvCourseListing.setAdapter(courseListingAdapter);
    }

    @Override
    protected String getActionbarTitle() {
        if (subCourses != null) {
            return subCourses.getSub_course_name();
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        try {
            int vId = view.getId();
            switch (vId) {
                case R.id.lyCourse:
                    Topics topics = ((Topics) view.getTag());
                    if (topics != null) {
                        Intent intent = new Intent(getActivity(), VideoActivity.class);
                        intent.putExtra(Topics.TOPICS,topics);
                        intent.putExtra(SelectCourseData.SELECTCOURSE, selectCourseData);
                        intent.putExtra(UserCourseData.USERCOURSEDATA, userCourseData);
                        intent.putExtra(SubCourses.SUBCOURSEDATA, subCourses);

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
}
