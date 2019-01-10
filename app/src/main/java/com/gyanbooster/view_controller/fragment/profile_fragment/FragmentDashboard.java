package com.gyanbooster.view_controller.fragment.profile_fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyanbooster.R;
import com.gyanbooster.dao.profile_response.DashboardResponse;
import com.gyanbooster.dao.profile_response.UserProfileData;
import com.gyanbooster.databinding.FragmentDashboardBinding;
import com.gyanbooster.model.DashboardModel;
import com.gyanbooster.utility.Util;
import com.gyanbooster.view_controller.fragment.BaseFragment;

import java.util.Observable;
import java.util.Observer;

public class FragmentDashboard extends BaseFragment implements Observer {

    private FragmentDashboardBinding fragmentDashboardBinding;
    private View v;
    private DashboardModel dashboardModel = new DashboardModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        v = fragmentDashboardBinding.getRoot();
        dashboardModel.addObserver(this);
        Util.showProDialog(getActivity());
        dashboardModel.dashboardApi(getActivity());


        return v;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.profile);
    }

    @Override
    public void update(Observable observable, Object o) {
        Util.dismissProDialog();
        try {
            if (o != null) {
                DashboardResponse dashboardResponse = ((DashboardResponse) o);
                UserProfileData userProfileData = dashboardResponse.getUser();
                setData(userProfileData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setData(UserProfileData userProfileData) {
        fragmentDashboardBinding.txtUserName.setText(getString(R.string.hello)+" "+userProfileData.getFront_user_fname());
        fragmentDashboardBinding.txtReferralCode.setText(getString(R.string.referral_code) +" "+userProfileData.getReferral_code());
        fragmentDashboardBinding.txtWalletAmount.setText(getString(R.string.wallet_amount)+" "+userProfileData.getWallet_amount());
    }
}
