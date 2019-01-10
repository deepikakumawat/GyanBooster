package com.gyanbooster.view_controller.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.checkout.ApplyCouponResponse;
import com.gyanbooster.dao.checkout.PayData;
import com.gyanbooster.dao.course_details.Course;
import com.gyanbooster.dao.select_courses.SelectCourseData;
import com.gyanbooster.databinding.ActivityCheckoutBinding;
import com.gyanbooster.model.CheckoutModel;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;
import com.gyanbooster.utility.Util;

import java.util.Observable;
import java.util.Observer;


public class CheckoutActivity extends Activity implements View.OnClickListener, Observer {

    View v;
    private ActivityCheckoutBinding activityCheckoutBinding;
    private Course course;
    private CheckoutModel checkoutModel = new CheckoutModel();
    private int sendAmount;


    public static CheckoutActivity newInstance() {
        CheckoutActivity fragInstance = new CheckoutActivity();

        return fragInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCheckoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);
        activityCheckoutBinding.setClick(this);
        checkoutModel.addObserver(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            course = (Course) getIntent().getSerializableExtra(Course.COURSE); //Obtaining data
        }
        if (course != null) {
            activityCheckoutBinding.txtCourse.setText(course.getCourse_name());
            activityCheckoutBinding.txtAmount.setText(getString(R.string.Rs) + course.getCourse_price());
            activityCheckoutBinding.txtTotalPrice.setText(getString(R.string.total_price) + course.getCourse_old_price());
            int discountPrice = Integer.parseInt(course.getCourse_old_price()) - Integer.parseInt(course.getCourse_price());
            activityCheckoutBinding.txtDiscountPrice.setText(getString(R.string.discount_price) + discountPrice);
            sendAmount = Integer.parseInt(course.getCourse_price());
            activityCheckoutBinding.txtPayablePrice.setText(getString(R.string.payable_price) + course.getCourse_price());
        }
    }


    @Override
    public void onClick(View view) {
        try {
            int vId = view.getId();
            switch (vId) {
                case R.id.txtPayNow:
                    gotoPaymentGateWay();
                    break;
                case R.id.txtApplyCoupon:
                    String coupon = activityCheckoutBinding.etxtCoupon.getText().toString();
                    if (coupon != null) {
                        Util.showProDialog(this);
                        checkoutModel.applyCouponApi(this, course, "0", coupon);
                    }
                    break;
                case R.id.imgBack:
                    onBackPressed();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gotoPaymentGateWay() {
        String name = GyanBoosterPreferences.getUserFrontName();
        String phone = GyanBoosterPreferences.getUserPhone();
        String email = GyanBoosterPreferences.getUserEmail();
        String amount = String.valueOf(sendAmount);

        Intent intent = new Intent(CheckoutActivity.this, PayMentGateWay.class);
        intent.putExtra("FIRST_NAME", name);
        intent.putExtra("PHONE_NUMBER", phone);
        intent.putExtra("EMAIL_ADDRESS", email);
        intent.putExtra("RECHARGE_AMT", amount);
        intent.putExtra(Course.COURSE, course);

        startActivity(intent);


    }

    @Override
    public void update(Observable observable, Object o) {
        Util.dismissProDialog();
        try {

            if (o != null) {
                ApplyCouponResponse applyCouponResponse = ((ApplyCouponResponse) o);
                if (applyCouponResponse != null) {
                    if (applyCouponResponse.getStatus().equalsIgnoreCase(Constants.STATUS)) {
                        Util.showCenteredToast(this, applyCouponResponse.getMessage());


                        activityCheckoutBinding.txtAmount.setText(getString(R.string.Rs) + applyCouponResponse.getTotalprice());
                        activityCheckoutBinding.txtTotalPrice.setText(getString(R.string.total_price) + applyCouponResponse.getTotalprice());
                        activityCheckoutBinding.txtDiscountPrice.setText(getString(R.string.discount_price) + applyCouponResponse.getDis_price());

                        int payablePrice = Integer.parseInt(applyCouponResponse.getTotalprice()) - Integer.parseInt(applyCouponResponse.getDis_price());

sendAmount = payablePrice;
                        activityCheckoutBinding.txtPayablePrice.setText(getString(R.string.payable_price) + payablePrice);

                        activityCheckoutBinding.etxtCoupon.getText().clear();

                        gotoPaymentGateWay();
                    }else{
                        Util.showCenteredToast(this, applyCouponResponse.getMessage());

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
