package com.gyanbooster.view_controller.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.about_us.ContactUsResponse;
import com.gyanbooster.dao.course_category.SubCourses;
import com.gyanbooster.databinding.FragmentContactBinding;
import com.gyanbooster.model.ContactModel;
import com.gyanbooster.utility.Util;

import java.util.Observable;
import java.util.Observer;

import static com.gyanbooster.utility.Util.isEmailValid;
import static com.gyanbooster.utility.Util.isValidMobile;
import static com.gyanbooster.utility.Util.popFragment;
import static com.gyanbooster.utility.Util.showCenteredToast;

public class FragmentContact extends BaseFragment implements View.OnClickListener, Observer {

    private final String url = "http://gyanbooster.com/contact-us";
    private FragmentContactBinding fragmentContactBinding;
    private View v;
    private ContactModel contactModel = new ContactModel();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentContactBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        v = fragmentContactBinding.getRoot();
        fragmentContactBinding.setClick(this);
        contactModel.addObserver(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        try {
            int vId = v.getId();
            switch (vId) {
                case R.id.txtSubmit:
                    String fName = fragmentContactBinding.etName.getText().toString();
                    String email = fragmentContactBinding.etEmail.getText().toString();
                    String phone = fragmentContactBinding.etPhone.getText().toString();
                    String sub = fragmentContactBinding.etsub.getText().toString();
                    String message = fragmentContactBinding.etMessage.getText().toString();
                    if (isValid(fName, email, sub, phone)) {
                        Util.showProDialog(getActivity());
                        contactModel.contactApi(getActivity(), fName, email, sub, phone, message);
                    }
                    break;
                case R.id.lyCallUs:
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + fragmentContactBinding.txtCallingNo.getText().toString()));
                        startActivity(intent);
                        break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean isValid(String name, String email, String sub, String phone) throws Exception {
        boolean validation_detials_flag = false;
        if (Util.isDeviceOnline(getActivity())) {
            if (TextUtils.isEmpty(name)) {
                showCenteredToast(getActivity(), getString(R.string.name_validation_message));
                fragmentContactBinding.etName.requestFocus();
            } else if (TextUtils.isEmpty(email)) {
                showCenteredToast(getActivity(), getString(R.string.email_validation_message));
                fragmentContactBinding.etEmail.requestFocus();
            } else if (!TextUtils.isEmpty(phone) && !isValidMobile(phone)) {
                showCenteredToast(getActivity(), getString(R.string.mobile_number));
                fragmentContactBinding.etPhone.requestFocus();
            } else if (TextUtils.isEmpty(sub)) {
                showCenteredToast(getActivity(), getString(R.string.invalid_sub));
                fragmentContactBinding.etsub.requestFocus();
            } else {
                if (!Util.isEmailValid(email)) {
                    showCenteredToast(getActivity(), getString(R.string.invalid_email));
                    fragmentContactBinding.etEmail.requestFocus();
                } else {
                    validation_detials_flag = true;
                }
            }
        } else {
            showCenteredToast(getActivity(), getString(R.string.network_connection));
        }
        return validation_detials_flag;
    }


    @Override
    protected String getActionbarTitle() {
        return getString(R.string.contact_us);
    }

    @Override
    public void update(Observable observable, Object o) {
        Util.dismissProDialog();
        try {
            if (o != null) {
                ContactUsResponse contactUsResponse = ((ContactUsResponse) o);
                if (contactUsResponse != null) {
                    if (Constants.STATUS.equalsIgnoreCase(contactUsResponse.getStatus())) {
                        Util.showCenteredToast(getActivity(), contactUsResponse.getMessage());
                        if (!TextUtils.isEmpty(fragmentContactBinding.etName.getText().toString())) {
                            fragmentContactBinding.etName.getText().clear();
                        }
                        if (!TextUtils.isEmpty(fragmentContactBinding.etEmail.getText().toString())) {
                            fragmentContactBinding.etEmail.getText().clear();
                        }
                        if (!TextUtils.isEmpty(fragmentContactBinding.etPhone.getText().toString())) {
                            fragmentContactBinding.etPhone.getText().clear();
                        }
                        if (!TextUtils.isEmpty(fragmentContactBinding.etsub.getText().toString())) {
                            fragmentContactBinding.etsub.getText().clear();
                        }
                        if (!TextUtils.isEmpty(fragmentContactBinding.etMessage.getText().toString())) {
                            fragmentContactBinding.etMessage.getText().clear();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
