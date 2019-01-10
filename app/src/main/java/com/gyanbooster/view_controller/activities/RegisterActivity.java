package com.gyanbooster.view_controller.activities;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.OTPResendResponse;
import com.gyanbooster.dao.OTPValidateResponse;
import com.gyanbooster.dao.RegisterResponse;
import com.gyanbooster.databinding.ActivityRegisterBinding;
import com.gyanbooster.model.RegisterModel;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;
import com.gyanbooster.utility.Util;

import java.util.Observable;
import java.util.Observer;

import static com.gyanbooster.utility.Util.isEmailValid;
import static com.gyanbooster.utility.Util.isValidMobile;
import static com.gyanbooster.utility.Util.showCenteredToast;

public class RegisterActivity extends Activity implements View.OnClickListener, Observer {


    private RegisterModel registerModel = new RegisterModel();
    private ActivityRegisterBinding activityRegisterBinding;
    private int otpType = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerModel.addObserver(this);
        activityRegisterBinding.txtRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            int vId = view.getId();
            switch (vId) {
                case R.id.txtRegister:
                    String fullName = activityRegisterBinding.etFullName.getText().toString().trim();
                    String email = activityRegisterBinding.etEmail.getText().toString().trim();
                    String password = activityRegisterBinding.etPassword.getText().toString().trim();
                    String confirmPassword = activityRegisterBinding.etConfirmPassword.getText().toString().trim();
                    String mobileNumber = activityRegisterBinding.etMobile.getText().toString().trim();
                    if (isValid(fullName, email, password, confirmPassword, mobileNumber)) {
                        Util.showProDialog(this);
                        registerModel.registerApi(this, fullName, email, password, confirmPassword, mobileNumber);
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
                if (o instanceof RegisterResponse) {
                    RegisterResponse registerResponse = ((RegisterResponse) o);
                    String status = registerResponse.getStatus();
                    if (status.equalsIgnoreCase(Constants.STATUS)) {
                        String userId = registerResponse.getUser_id();
                        if (!TextUtils.isEmpty(userId)) {
                            showOTPDialog(this, userId);
                        }
                    } else {
                        Util.showCenteredToast(this, registerResponse.getMessage());
                    }
                } else if (o instanceof OTPValidateResponse) {
                    OTPValidateResponse otpValidateResponse = ((OTPValidateResponse) o);
                    String status = otpValidateResponse.getStatus();
                    if (status.equalsIgnoreCase(Constants.STATUS)) {
                        onBackPressed();
                    } else {
                        Util.showCenteredToast(this, otpValidateResponse.getMsg());
                    }
                } else if (o instanceof OTPResendResponse) {
                    OTPResendResponse otpResendResponse = ((OTPResendResponse) o);
                    Util.showCenteredToast(this, otpResendResponse.getMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValid(String fullName, String email, String password, String confirmPassword, String mobileNo) throws Exception {
        boolean validation_detials_flag = false;
        if (Util.isDeviceOnline(this)) {
            if (TextUtils.isEmpty(fullName)) {
                showCenteredToast(this, getString(R.string.name_validation_message));
                activityRegisterBinding.etFullName.requestFocus();
            } else if (TextUtils.isEmpty(email)) {
                showCenteredToast(this, getString(R.string.email_validation_message));
                activityRegisterBinding.etEmail.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                showCenteredToast(this, getString(R.string.invalid_password));
                activityRegisterBinding.etPassword.requestFocus();
            } else if (TextUtils.isEmpty(confirmPassword)) {
                showCenteredToast(this, getString(R.string.confirmed_invalid_password));
                activityRegisterBinding.etConfirmPassword.requestFocus();
            } else if (TextUtils.isEmpty(mobileNo)) {
                showCenteredToast(this, getString(R.string.invalid_mobile_number));
                activityRegisterBinding.etMobile.requestFocus();
            } else if (!isEmailValid(email)) {
                showCenteredToast(this, getString(R.string.invalid_email));
                activityRegisterBinding.etEmail.requestFocus();
            } else if (!confirmPassword.equalsIgnoreCase(password)) {
                showCenteredToast(this, getString(R.string.password_confirm_password));
                activityRegisterBinding.etConfirmPassword.requestFocus();

            } else if (!isValidMobile(mobileNo)) {
                showCenteredToast(this, getString(R.string.mobile_number));
                activityRegisterBinding.etMobile.requestFocus();
            } else {
                validation_detials_flag = true;
            }
        } else {
            showCenteredToast(this, getString(R.string.network_connection));
        }
        return validation_detials_flag;
    }



    public void showOTPDialog(final Activity activity, final String userId) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_otp);


        final EditText etOTP = dialog.findViewById(R.id.etOTP);
        final RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
        TextView txtResend = dialog.findViewById(R.id.txtResend);
        TextView txtValidate = dialog.findViewById(R.id.txtValidate);
        TextView txtBack = dialog.findViewById(R.id.txtBack);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbEmail) {
                    otpType = 0;
                } else if (checkedId == R.id.rbSMS) {
                    otpType = 1;
                } else if (checkedId == R.id.rbBoth) {
                    otpType = 2;
                }
            }
        });

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroup.setVisibility(View.VISIBLE);
                if (otpType == -1) {
                    Util.showCenteredToast(activity, getString(R.string.please_select_otp_type));
                } else {
                    registerModel.resntOTPApi(activity, userId, otpType + "");
                }
            }
        });

        txtValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = etOTP.getText().toString();
                if (!TextUtils.isEmpty(otp)) {
                    registerModel.validateOTPApi(activity, userId, etOTP.getText().toString());
                } else {
                    Util.showCenteredToast(activity, getString(R.string.enter_otp));
                }
            }
        });

        dialog.show();

    }
}
