package com.gyanbooster.view_controller.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.about_us.AboutUsResponse;
import com.gyanbooster.databinding.FragmentAboutUsBinding;
import com.gyanbooster.databinding.FragmentCompanyInfoBinding;
import com.gyanbooster.model.AboutUsModel;
import com.gyanbooster.utility.Util;

import java.util.Observable;
import java.util.Observer;

public class AboutUsFragment extends BaseFragment implements Observer {

    private FragmentAboutUsBinding fragmentAboutUsBinding;
    private View v;
    private AboutUsModel aboutUsModel = new AboutUsModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentAboutUsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container, false);
        v = fragmentAboutUsBinding.getRoot();
        aboutUsModel.addObserver(this);
        Util.showProDialog(getActivity());
        aboutUsModel.aboutUsApi(getActivity());

        return v;
    }

    @Override
    public void update(Observable observable, Object o) {
        Util.dismissProDialog();
        try {
            if (o != null) {
                AboutUsResponse aboutUsResponse = ((AboutUsResponse) o);
                if (aboutUsResponse != null) {
                    fragmentAboutUsBinding.txtAboutUs.setText(Html.fromHtml(aboutUsResponse.getAbout().getAbout_desc()).toString());
                    String thumbnail = Constants.THUMBNAIL_BASE_URL + aboutUsResponse.getAbout().getAbout_file();
                    Glide.with(getActivity()).load(thumbnail).dontAnimate().into(fragmentAboutUsBinding.imgAbout);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.about_us);
    }
}
