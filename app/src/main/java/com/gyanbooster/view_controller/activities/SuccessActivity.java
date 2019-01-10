package com.gyanbooster.view_controller.activities;

import android.app.Activity;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.gyanbooster.R;
import com.gyanbooster.dao.checkout.PayData;
import com.gyanbooster.dao.course_details.Course;
import com.gyanbooster.dao.select_courses.SelectCourseData;
import com.gyanbooster.databinding.ActivitySucsessBinding;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;
import com.gyanbooster.view_controller.fragment.CourseCategoryFragment;
import com.gyanbooster.view_controller.fragment.profile_fragment.FragmentProfile;
import com.gyanbooster.view_controller.fragment.profile_fragment.FragmentProfileDetails;

public class SuccessActivity extends Activity implements View.OnClickListener {


    private ActivitySucsessBinding activitySucsessBinding;
    private PayData payData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySucsessBinding = DataBindingUtil.setContentView(this, R.layout.activity_sucsess);
        activitySucsessBinding.setClick(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            payData = (PayData) getIntent().getSerializableExtra(PayData.PAYDATA); //Obtaining data
            if (payData != null) {
                activitySucsessBinding.txtTransactionId.setText(getString(R.string.transaction_id) + " " + payData.getP_txn_id());
                activitySucsessBinding.txtUserName.setText(getString(R.string.user_name) + " " + GyanBoosterPreferences.getUserFrontName());
                activitySucsessBinding.txtCourse.setText(getString(R.string.course) + " : " + payData.getCourse_name());
                activitySucsessBinding.txtAmount.setText(getString(R.string.amount) + " : " + payData.getCourse_price());
                activitySucsessBinding.txtVisitCourse.setPaintFlags(activitySucsessBinding.txtVisitCourse.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

            }
        }

    }


    @Override
    public void onClick(View view) {
        try {
            int vId = view.getId();
            switch (vId) {
                case R.id.imgBack:
                    onBackPressed();

                    break;
                case R.id.txtVisitCourse:


                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
