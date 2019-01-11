package com.gyanbooster.view_controller.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyanbooster.R;
import com.gyanbooster.databinding.ActivitySpalshBinding;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;

public class SplashActivity extends Activity implements View.OnClickListener {


    private static final int PERMISSION_ALL = 11;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView[] dots;
    private int[] layouts;
    private int[] title;
    private ActivitySpalshBinding activitySplashBinding;

    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CALL_PHONE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        if (GyanBoosterPreferences.getSplashScreenShow() == false) {

            GyanBoosterPreferences.setSplashScreenShow(true);
            GyanBoosterPreferences.savePreferencese();

            activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_spalsh);
            activitySplashBinding.setClick(this);

            layouts = new int[]{
                    R.mipmap.splace1,
                    R.mipmap.splace2,
                    R.mipmap.splace3};
            title = new int[]{
                    R.string.engineering_video_lesson,
                    R.string.unlimited_analysis,
                    R.string.personalised_learning};

            addBottomDots(0);
            changeStatusBarColor();

            myViewPagerAdapter = new MyViewPagerAdapter();
            activitySplashBinding.viewPager.setAdapter(myViewPagerAdapter);
            activitySplashBinding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        } else {
            launchHomeScreen();
        }


    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        activitySplashBinding.dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            activitySplashBinding.dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return activitySplashBinding.viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        startActivity(new Intent(SplashActivity.this, DrawerBaseActivity.class));
        finish();
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                activitySplashBinding.btnNext.setText(getString(R.string.start));
                activitySplashBinding.btnSkip.setVisibility(View.GONE);
            } else {
                activitySplashBinding.btnNext.setText(getString(R.string.next));
                activitySplashBinding.btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View view) {
        try {
            int vId = view.getId();
            switch (vId) {
                case R.id.btnNext:
                    int current = getItem(+1);
                    if (current < layouts.length) {
                        activitySplashBinding.viewPager.setCurrentItem(current);
                    } else {
                        launchHomeScreen();
                    }
                    break;
                case R.id.btnSkip:
                    launchHomeScreen();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.list_item_splash, container, false);
            ImageView imageView = view.findViewById(R.id.imgSplash);
            TextView txtTitle = view.findViewById(R.id.txtTitle);
            imageView.setBackgroundResource(layouts[position]);
            txtTitle.setText(title[position]);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
