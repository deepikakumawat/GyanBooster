package com.gyanbooster.view_controller.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.forgot_password.ChangePasswordResponse;
import com.gyanbooster.dao.forgot_password.ForgotPasswordOTPResponse;
import com.gyanbooster.dao.forgot_password.ForgotPasswordResponse;
import com.gyanbooster.dao.login_response.LoginResponse;
import com.gyanbooster.databinding.ActivityForgotPasswordBinding;
import com.gyanbooster.databinding.ActivityLoginBinding;
import com.gyanbooster.model.ForgotPasswordModel;
import com.gyanbooster.model.LoginModel;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;
import com.gyanbooster.utility.Util;

import java.util.Observable;
import java.util.Observer;

import static com.gyanbooster.utility.Util.isEmailValid;
import static com.gyanbooster.utility.Util.isValidMobile;
import static com.gyanbooster.utility.Util.showCenteredToast;

public class ForgotPasswordActivity extends Activity implements View.OnClickListener, Observer {


    private ActivityForgotPasswordBinding activityForgotPasswordBinding;
    private ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        activityForgotPasswordBinding.setClick(this);
        forgotPasswordModel.addObserver(this);


    }


    @Override
    public void onClick(View view) {
        try {
            int vId = view.getId();
            switch (vId) {
                case R.id.imgBack:
                    onBackPressed();
                    break;
                case R.id.txtSend:
                    if (activityForgotPasswordBinding.etEmail.getVisibility() == View.VISIBLE) {
                        String emailPhone = activityForgotPasswordBinding.etEmail.getText().toString();
                        if (isValid(emailPhone)) {
                            forgotPasswordModel.forgorPassword(this, emailPhone);
                        }
                    } else if (activityForgotPasswordBinding.etOTP.getVisibility() == View.VISIBLE) {
                        String otp = activityForgotPasswordBinding.etOTP.getText().toString();
                        if (!TextUtils.isEmpty(otp)) {
                            forgotPasswordModel.forgorOTPPassword(this, otp, userId);
                        } else {
                            showCenteredToast(this, getString(R.string.enter_otp_forgot));
                        }
                    } else if (activityForgotPasswordBinding.etNewPassword.getVisibility() == View.VISIBLE) {
                        String newPassword = activityForgotPasswordBinding.etNewPassword.getText().toString();
                        String confirmPassword = activityForgotPasswordBinding.etConfirmPassword.getText().toString();
                        if (isValid(newPassword, confirmPassword)) {
                            forgotPasswordModel.chanePassword(this, newPassword, confirmPassword, userId);
                        }
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
            Log.d("result", o.toString());
            if (o != null) {

                if (o instanceof ForgotPasswordResponse) {
                    ForgotPasswordResponse forgotPasswordResponse = ((ForgotPasswordResponse) o);
                    if (Constants.STATUS.equalsIgnoreCase(forgotPasswordResponse.getStatus())) {
                        activityForgotPasswordBinding.etEmail.setVisibility(View.GONE);
                        activityForgotPasswordBinding.etOTP.setVisibility(View.VISIBLE);
                        userId = forgotPasswordResponse.getUser_id();
                    }
                } else if (o instanceof ForgotPasswordOTPResponse) {
                    ForgotPasswordOTPResponse forgotPasswordOTPResponse = ((ForgotPasswordOTPResponse) o);
                    if (Constants.STATUS.equalsIgnoreCase(forgotPasswordOTPResponse.getStatus())) {

                        Util.showCenteredToast(this, forgotPasswordOTPResponse.getMessage());
                        activityForgotPasswordBinding.etOTP.setVisibility(View.GONE);
                        activityForgotPasswordBinding.etNewPassword.setVisibility(View.VISIBLE);
                        activityForgotPasswordBinding.etConfirmPassword.setVisibility(View.VISIBLE);

                    }
                } else if (o instanceof ChangePasswordResponse) {
                    ChangePasswordResponse changePasswordResponse = ((ChangePasswordResponse) o);
                    if (Constants.STATUS.equalsIgnoreCase(changePasswordResponse.getStatus())) {
                        Util.showCenteredToast(this, changePasswordResponse.getMessage());
                        onBackPressed();
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValid(String emailPhone) throws Exception {
        boolean validation_detials_flag = false;
        if (Util.isDeviceOnline(this)) {
            if (TextUtils.isEmpty(emailPhone)) {
                showCenteredToast(this, getString(R.string.email_phone_validation_message));
                activityForgotPasswordBinding.etEmail.requestFocus();
            } else {
                if (Patterns.PHONE.matcher(emailPhone).matches()) {
                    if (!isValidMobile(emailPhone)) {
                        showCenteredToast(this, getString(R.string.email_phone_validation_message));
                        activityForgotPasswordBinding.etEmail.requestFocus();
                    } else {
                        validation_detials_flag = true;
                    }
                } else if (!Util.isEmailValid(emailPhone)) {
                    showCenteredToast(this, getString(R.string.email_phone_validation_message));
                    activityForgotPasswordBinding.etEmail.requestFocus();
                } else {
                    validation_detials_flag = true;
                }
            }
        } else {
            showCenteredToast(this, getString(R.string.network_connection));
        }
        return validation_detials_flag;
    }

    private boolean isValid(String password, String confirmPassword) throws Exception {
        boolean validation_detials_flag = false;
        if (Util.isDeviceOnline(this)) {
            if (TextUtils.isEmpty(password)) {
                showCenteredToast(this, getString(R.string.invalid_password));
                activityForgotPasswordBinding.etNewPassword.requestFocus();
            } else if (TextUtils.isEmpty(confirmPassword)) {
                showCenteredToast(this, getString(R.string.confirmed_invalid_password));
                activityForgotPasswordBinding.etConfirmPassword.requestFocus();
            } else if (!confirmPassword.equalsIgnoreCase(password)) {
                showCenteredToast(this, getString(R.string.password_confirm_password));
                activityForgotPasswordBinding.etConfirmPassword.requestFocus();

            } else {
                validation_detials_flag = true;
            }
        } else {
            showCenteredToast(this, getString(R.string.network_connection));
        }
        return validation_detials_flag;
    }

}
