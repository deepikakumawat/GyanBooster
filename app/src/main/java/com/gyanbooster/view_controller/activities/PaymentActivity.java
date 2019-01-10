package com.gyanbooster.view_controller.activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.gyanbooster.R;
import com.gyanbooster.dao.course_details.Course;
import com.gyanbooster.databinding.ActivityPaymentBinding;
import com.gyanbooster.utility.Util;

import static com.gyanbooster.utility.Util.isValidMobile;
import static com.gyanbooster.utility.Util.showCenteredToast;


public class PaymentActivity extends Activity implements View.OnClickListener {

    private ActivityPaymentBinding activityPaymentBinding;
    private Course course;


    public static PaymentActivity newInstance() {
        PaymentActivity fragInstance = new PaymentActivity();

        return fragInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPaymentBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        activityPaymentBinding.setClick(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            course = (Course) getIntent().getSerializableExtra(Course.COURSE); //Obtaining data
        }

    }


    @Override
    public void onClick(View v) {
        try {
            int vId = v.getId();
            switch (vId) {
                case R.id.txtPay:

                    String name = activityPaymentBinding.etName.getText().toString().trim();
                    String phone = activityPaymentBinding.etPhone.getText().toString().trim();
                    String email = activityPaymentBinding.etEmail.getText().toString().trim();
                    String amount = activityPaymentBinding.etAmount.getText().toString().trim();

                    if (isValid(name, email, phone, amount)) {
                        Intent intent = new Intent(PaymentActivity.this, PayMentGateWay.class);
                        intent.putExtra("FIRST_NAME", name);
                        intent.putExtra("PHONE_NUMBER", phone);
                        intent.putExtra("EMAIL_ADDRESS", email);
                        intent.putExtra("RECHARGE_AMT", amount);
                        intent.putExtra(Course.COURSE, course);

                        startActivity(intent);

                    }

                    break;
                case R.id.txtBack:
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

    private boolean isValid(String name, String email, String phone, String amount) throws Exception {
        boolean validation_detials_flag = false;
        if (Util.isDeviceOnline(this)) {
            if (TextUtils.isEmpty(name)) {
                showCenteredToast(this, getString(R.string.name_validation_message));
                activityPaymentBinding.etName.requestFocus();
            } else if (TextUtils.isEmpty(email)) {
                showCenteredToast(this, getString(R.string.email_validation_message));
                activityPaymentBinding.etEmail.requestFocus();
            } else if (TextUtils.isEmpty(phone)) {
                showCenteredToast(this, getString(R.string.invalid_mobile_number));
                activityPaymentBinding.etPhone.requestFocus();
            } else if (TextUtils.isEmpty(amount)) {
                showCenteredToast(this, getString(R.string.enter_amount));
                activityPaymentBinding.etAmount.requestFocus();
            } else if (!Util.isEmailValid(email)) {
                showCenteredToast(this, getString(R.string.invalid_email));
                activityPaymentBinding.etEmail.requestFocus();
            } else {
                if (!isValidMobile(phone)) {
                    showCenteredToast(this, getString(R.string.mobile_number));
                    activityPaymentBinding.etPhone.requestFocus();
                } else {
                    validation_detials_flag = true;
                }

            }
        } else {
            showCenteredToast(this, getString(R.string.network_connection));
        }
        return validation_detials_flag;
    }

}
