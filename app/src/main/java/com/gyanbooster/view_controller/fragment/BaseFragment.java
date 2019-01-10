package com.gyanbooster.view_controller.fragment;

import android.app.Fragment;
import android.content.Context;
import android.view.View;

import com.gyanbooster.interfaces.IFragmentListener;
import com.gyanbooster.view_controller.activities.DrawerBaseActivity;


/**
 * Created by Audetemi Inc. on 01/11/16.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    View mRootview;
    private IFragmentListener mListener;

    @Override
    public void onResume() {
        super.onResume();

        if (this.mListener != null ) {
            this.mListener.setActionBarTitle(getActionbarTitle());

        }
    }

    protected String getActionbarTitle() {
        return "";
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (DrawerBaseActivity) context;
    }




}
