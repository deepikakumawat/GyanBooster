package com.gyanbooster.view_controller.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.CompanyInfoResponse.CompanyInfoResponse;
import com.gyanbooster.databinding.FragmentCompanyInfoBinding;
import com.gyanbooster.model.CompanyInfoModel;
import com.gyanbooster.utility.Util;
import com.gyanbooster.view_controller.fragment.BaseFragment;

import java.util.Observable;
import java.util.Observer;

public class CompanyInfoFragment extends BaseFragment implements Observer {

    private FragmentCompanyInfoBinding fragmentCompanyInfoBinding;
    private View v;
    private CompanyInfoModel companyInfoModel = new CompanyInfoModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentCompanyInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_company_info, container, false);
        v = fragmentCompanyInfoBinding.getRoot();
        companyInfoModel.addObserver(this);
        Util.showProDialog(getActivity());
        companyInfoModel.companyInfoApi(getActivity());


        return v;
    }

    @Override
    public void update(Observable observable, Object o) {
        Util.dismissProDialog();
        try{
            if (o != null) {
                CompanyInfoResponse companyInfoResponse = ((CompanyInfoResponse) o);
                if (companyInfoResponse != null) {
                    String thumbnail = Constants.THUMBNAIL_BASE_URL + companyInfoResponse.getCompany_details().getCompany_logo();
                    Glide.with(getActivity()).load(thumbnail).dontAnimate().into(fragmentCompanyInfoBinding.imgCompanyLogo);
                    fragmentCompanyInfoBinding.txtAddress.setText(companyInfoResponse.getCompany_details().getCompany_address());
                    fragmentCompanyInfoBinding.txtEmail.setText(companyInfoResponse.getCompany_details().getCompany_email());
                    fragmentCompanyInfoBinding.txtPhone1.setText(companyInfoResponse.getCompany_details().getCompany_phone1());
                    fragmentCompanyInfoBinding.txtPhone2.setText(companyInfoResponse.getCompany_details().getCompany_phone2());
                    fragmentCompanyInfoBinding.txtGSTNo.setText(companyInfoResponse.getCompany_details().getCompany_gst_no());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected String getActionbarTitle() {
        return  getString(R.string.conpany_info);
    }
}
