package com.gyanbooster.view_controller.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;

import java.util.Observable;
import java.util.Observer;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;

import com.gyanbooster.dao.login_response.LoginResponse;
import com.gyanbooster.databinding.ActivityLoginBinding;
import com.gyanbooster.model.LoginModel;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;
import com.gyanbooster.utility.Util;

import static com.gyanbooster.utility.Util.showCenteredToast;

public class LoginActivity extends Activity implements View.OnClickListener, Observer {


    private TextView txtLogin;
    private TextView txtCreateAccount;
    private TextView txtForgotPassword;
    private ActivityLoginBinding activityLoginBinding;
    private LoginModel loginModel = new LoginModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginModel.addObserver(this);
        init();

    }

    private void init() {
        txtLogin = findViewById(R.id.txtLogin);
        txtCreateAccount = findViewById(R.id.txtCreateAccount);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtLogin.setOnClickListener(this);
        txtCreateAccount.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            Intent intent;
            int vId = view.getId();
            switch (vId) {
                case R.id.txtLogin:
                    String email = activityLoginBinding.etEmail.getText().toString().trim();
                    String password = activityLoginBinding.etPassword.getText().toString().trim();
                    if (isValid(email, password)) {
                        Util.showProDialog(this);
                        loginModel.loginApi(this, email, password);
                    }
                    break;
                case R.id.txtCreateAccount:
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.txtForgotPassword:
//                    showDialog(this);

                    intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_forgot_password);


        TextView txtBack = dialog.findViewById(R.id.txtBack);
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    @Override
    public void update(Observable observable, Object o) {
        Util.dismissProDialog();
        try {
            Log.d("result", o.toString());
            if (o != null) {
                LoginResponse loginResponse = ((LoginResponse) o);
                String status = loginResponse.getStatus();
                if (status.equalsIgnoreCase(Constants.STATUS)) {
                    GyanBoosterPreferences.setUserId(loginResponse.getUser().getFront_user_id());
                    GyanBoosterPreferences.setUserFrontName(loginResponse.getUser().getFront_user_fname());
                    GyanBoosterPreferences.setUserEmail(loginResponse.getUser().getFront_user_email());
                    GyanBoosterPreferences.setUserPhone(loginResponse.getUser().getFront_user_phn());
                    GyanBoosterPreferences.setUserImage(loginResponse.getUser().getUser_image());
                    GyanBoosterPreferences.savePreferencese();
                    Intent data = new Intent();
                    setResult(Activity.RESULT_OK, data);
                    finish();
                } else {
                    showCenteredToast(this, loginResponse.getMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValid(String email, String password) throws Exception {
        boolean validation_detials_flag = false;
        if (Util.isDeviceOnline(this)) {
            if (TextUtils.isEmpty(email)) {
                showCenteredToast(this, getString(R.string.email_validation_message));
                activityLoginBinding.etEmail.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                showCenteredToast(this, getString(R.string.invalid_password));
                activityLoginBinding.etPassword.requestFocus();
            } else {
                if (Patterns.PHONE.matcher(email).matches()) {
                    if (email.length() < 6 || email.length() > 15) {
                        Util.showCenteredToast(null, this.getResources().getString(R.string.invalid_email));
                        activityLoginBinding.etEmail.requestFocus();
                    } else {
                        validation_detials_flag = true;
                    }
                } else if (!Util.isEmailValid(email)) {
                    Util.showCenteredToast(null, this.getResources().getString(R.string.invalid_email));
                    activityLoginBinding.etEmail.requestFocus();
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
