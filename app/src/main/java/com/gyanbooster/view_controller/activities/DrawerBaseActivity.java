package com.gyanbooster.view_controller.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyanbooster.dao.login_response.LoginResponse;
import com.gyanbooster.dao.select_courses.SelectCourseData;
import com.gyanbooster.interfaces.IFragmentListener;
import com.gyanbooster.interfaces.ISuccessListener;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;
import com.gyanbooster.R;
import com.gyanbooster.view_controller.fragment.AboutUsFragment;
import com.gyanbooster.view_controller.fragment.CompanyInfoFragment;
import com.gyanbooster.view_controller.fragment.FragmentContact;
import com.gyanbooster.view_controller.fragment.OfflineVideosFragment;
import com.gyanbooster.view_controller.fragment.profile_fragment.FragmentProfile;
import com.gyanbooster.view_controller.fragment.SelectViewFragment;


public class DrawerBaseActivity extends Activity implements View.OnClickListener, IFragmentListener, ISuccessListener {

    private static final int LOGIN_ACTION = 101;
    private DrawerLayout mDrawerLayout;

    private TextView tvCourses;
    private TextView tv_screen_title;
    long back_pressed = 0;
    private LinearLayout layout_main;
    private TextView txtLogin;
    private TextView txtRegister;
    private ImageView drawerSlideIcon;
    private SelectCourseData selectCourseData;
    private TextView tvPhone;
    private TextView tvEmail;
    private LinearLayout login_signup_buttons;
    private TextView tvLogout;
    private TextView tvAboutUs;
    private TextView tvCompanyInfo;
    private TextView tvProfile;
    private TextView tvName;
    private TextView tvContactUs;
    private TextView tvOfflineVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerbase);
        init();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectCourseData = (SelectCourseData) getIntent().getSerializableExtra(SelectCourseData.SELECTCOURSE); //Obtaining data
        }

        Fragment frag = new SelectViewFragment();
        mDrawerLayout.closeDrawer(layout_main);
        replaceFragment(frag);


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onClick(View view) {
        Fragment frag;
        Intent intent;
        switch (view.getId()) {
            case R.id.drawerSlideIcon:
                setEmailPhone();
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.tvProfile:
                frag = new FragmentProfile();
                mDrawerLayout.closeDrawer(layout_main);
                replaceFragment(frag);
                break;
            case R.id.tvContactUs:
                frag = new FragmentContact();
                mDrawerLayout.closeDrawer(layout_main);
                replaceFragment(frag);
                break;
            case R.id.tvAboutUs:
                frag = new AboutUsFragment();
                mDrawerLayout.closeDrawer(layout_main);
                replaceFragment(frag);
                break;
            case R.id.tvCompanyInfo:
                frag = new CompanyInfoFragment();
                mDrawerLayout.closeDrawer(layout_main);
                replaceFragment(frag);
                break;
            case R.id.tvLogout:
                mDrawerLayout.closeDrawer(layout_main);
                logout();
                break;
            case R.id.tvCourses:
                frag = new SelectViewFragment();
                mDrawerLayout.closeDrawer(layout_main);
                replaceFragment(frag);
                break;
            case R.id.txtLogin:
                intent = new Intent(DrawerBaseActivity.this, LoginActivity.class);
                startActivityForResult(intent, LOGIN_ACTION);
                mDrawerLayout.closeDrawer(layout_main);
                break;
            case R.id.txtRegister:
                intent = new Intent(DrawerBaseActivity.this, RegisterActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawer(layout_main);
                break;
            case R.id.tvOfflineVideos:
                frag = new OfflineVideosFragment();
                mDrawerLayout.closeDrawer(layout_main);
                replaceFragment(frag);
                break;

            default:
                break;
        }
    }


    private void init() {
        //-----------------------------------------------

        login_signup_buttons = findViewById(R.id.login_signup_buttons);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        layout_main = findViewById(R.id.layout_main);
        tv_screen_title = findViewById(R.id.tv_screen_title);
        tvCourses = findViewById(R.id.tvCourses);
        tvAboutUs = findViewById(R.id.tvAboutUs);
        tvCompanyInfo = findViewById(R.id.tvCompanyInfo);
        tvLogout = findViewById(R.id.tvLogout);
        drawerSlideIcon = findViewById(R.id.drawerSlideIcon);
        txtLogin = findViewById(R.id.txtLogin);
        txtRegister = findViewById(R.id.txtRegister);
        tvContactUs = findViewById(R.id.tvContactUs);
        tvPhone = findViewById(R.id.tvPhone);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvProfile = findViewById(R.id.tvProfile);
        tvOfflineVideos = findViewById(R.id.tvOfflineVideos);
        tvCourses.setOnClickListener(this);
        drawerSlideIcon.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        tvAboutUs.setOnClickListener(this);
        tvCompanyInfo.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        tvProfile.setOnClickListener(this);
        tvContactUs.setOnClickListener(this);
        tvOfflineVideos.setOnClickListener(this);

        setEmailPhone();

    }

    public void setEmailPhone() {
        if (!TextUtils.isEmpty(GyanBoosterPreferences.getUserPhone())) {
            tvPhone.setVisibility(View.VISIBLE);
            login_signup_buttons.setVisibility(View.GONE);
            tvPhone.setText(getString(R.string.phone) + GyanBoosterPreferences.getUserPhone());
        } else {
            tvPhone.setVisibility(View.GONE);
            login_signup_buttons.setVisibility(View.VISIBLE);

        }
        if (!TextUtils.isEmpty(GyanBoosterPreferences.getUserEmail())) {
            tvEmail.setVisibility(View.VISIBLE);
            login_signup_buttons.setVisibility(View.GONE);

            tvEmail.setText(getString(R.string.email_show) + GyanBoosterPreferences.getUserEmail());
        } else {
            tvEmail.setVisibility(View.GONE);
            login_signup_buttons.setVisibility(View.VISIBLE);

        }
        if (!TextUtils.isEmpty(GyanBoosterPreferences.getUserFrontName())) {
            tvName.setVisibility(View.VISIBLE);
            login_signup_buttons.setVisibility(View.GONE);

            tvName.setText(getString(R.string.hello) + " " + GyanBoosterPreferences.getUserFrontName());
        } else {
            tvName.setVisibility(View.GONE);
            login_signup_buttons.setVisibility(View.VISIBLE);

        }

        if (TextUtils.isEmpty(GyanBoosterPreferences.getUserId())) {
            login_signup_buttons.setVisibility(View.VISIBLE);
            tvLogout.setVisibility(View.GONE);
            tvProfile.setVisibility(View.GONE);
        } else {
            login_signup_buttons.setVisibility(View.GONE);
            tvLogout.setVisibility(View.VISIBLE);
            tvProfile.setVisibility(View.VISIBLE);

        }
    }


    /**
     * Diplaying fragment view for selected nav drawer list item
     */


    public void replaceFragment(Fragment fragment) {


        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }


        String backStateName = fragment.getClass().getName();
        FragmentManager fragmentManager = getFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && fragmentManager.findFragmentByTag(backStateName) == null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment, backStateName);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
            fragmentTransaction.addToBackStack(backStateName);
            fragmentTransaction.commit();

        }
    }


    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(layout_main)) {
            mDrawerLayout.closeDrawer(layout_main);
        } else {

            if (getFragmentManager().getBackStackEntryCount() == 1) {

                if (back_pressed + 2000 > System.currentTimeMillis()) {


                    finish();
                } else
                    Toast.makeText(getBaseContext(), "Press once again to exit!",
                            Toast.LENGTH_SHORT).show();
                back_pressed = System.currentTimeMillis();
                //finish();
            } else {


                super.onBackPressed();


            }
        }

    }


    @Override
    public void setActionBarTitle(String title) {
        tv_screen_title.setText(title);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_ACTION) {
            if (resultCode == Activity.RESULT_OK) {
                setEmailPhone();
                onClick(tvProfile);
            }
        }
    }

    private void logout() {
        try {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.do_you_want_to_logout))
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            GyanBoosterPreferences.removeValueForKey(LoginResponse.KEY_USERID);
                            GyanBoosterPreferences.removeValueForKey(LoginResponse.KEY_USEREMAIL);
                            GyanBoosterPreferences.removeValueForKey(LoginResponse.KEY_USERPHONE);
                            GyanBoosterPreferences.removeValueForKey(LoginResponse.KEY_FRONT_USER_NAME);
                            GyanBoosterPreferences.removeValueForKey(LoginResponse.KEY_USER_IMAGE);
                            tvEmail.setVisibility(View.GONE);
                            login_signup_buttons.setVisibility(View.VISIBLE);
                            tvPhone.setVisibility(View.GONE);
                            tvName.setVisibility(View.GONE);
                            tvLogout.setVisibility(View.GONE);
                            tvProfile.setVisibility(View.GONE);

                            Fragment f = getFragmentManager().findFragmentById(R.id.frame_container);
                            if (f instanceof FragmentProfile) {
                                getFragmentManager().popBackStack();
                            }

                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paymentSuccess() {
        onClick(tvProfile);
    }
}

