package com.gyanbooster.view_controller.fragment.profile_fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyanbooster.R;
import com.gyanbooster.databinding.FragmentProfileBinding;
import com.gyanbooster.utility.Util;
import com.gyanbooster.view_controller.fragment.BaseFragment;

public class FragmentProfile extends BaseFragment implements View.OnClickListener {

    public FragmentProfileBinding fragmentProfileBinding;
    private View v;
    private Fragment frag;
    FragmentTransaction ft;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        v = fragmentProfileBinding.getRoot();
        fragmentProfileBinding.setClick(this);
        onClick(fragmentProfileBinding.txtDashboard);
        return v;
    }

    @Override
    public void onClick(View v) {
        try {
            int vId = v.getId();
            switch (vId) {
                case R.id.txtDashboard:
                    fragmentProfileBinding.txtDashboard.setBackgroundResource(R.color.txt_orange);
                    fragmentProfileBinding.txtYourCourse.setBackgroundResource(R.color.gray);
                    fragmentProfileBinding.txtProfileDetails.setBackgroundResource(R.color.gray);
                    goToDashboard();
                    break;
                case R.id.txtYourCourse:

                    fragmentProfileBinding.txtDashboard.setBackgroundResource(R.color.gray);
                    fragmentProfileBinding.txtYourCourse.setBackgroundResource(R.color.txt_orange);
                    fragmentProfileBinding.txtProfileDetails.setBackgroundResource(R.color.gray);

                    frag = new FragmentYourCourse();
                    ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.flProfile, frag, "NewFragmentTag");
                    ft.commit();


                    break;
                case R.id.txtProfileDetails:
                    fragmentProfileBinding.txtDashboard.setBackgroundResource(R.color.gray);
                    fragmentProfileBinding.txtYourCourse.setBackgroundResource(R.color.gray);
                    fragmentProfileBinding.txtProfileDetails.setBackgroundResource(R.color.txt_orange);

                    frag = new FragmentProfileDetails();
                    ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.flProfile, frag, "NewFragmentTag");
                    ft.commit();


                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToDashboard() {
        frag = new FragmentDashboard();
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.flProfile, frag, "NewFragmentTag");
        ft.commit();


    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.profile);
    }
}
