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
import com.gyanbooster.databinding.FragmentOfflineVideosBinding;
import com.gyanbooster.model.AboutUsModel;
import com.gyanbooster.utility.Util;

import java.util.Observable;
import java.util.Observer;

public class OfflineVideosFragment extends BaseFragment  {

    private FragmentOfflineVideosBinding fragmentOfflineVideosBinding;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentOfflineVideosBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_offline_videos, container, false);
        v = fragmentOfflineVideosBinding.getRoot();


        return v;
    }



    @Override
    protected String getActionbarTitle() {
        return getString(R.string.offline_videos);
    }
}
