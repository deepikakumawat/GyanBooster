package com.gyanbooster.utility;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.gyanbooster.R;


/**
 * Created by Dell on 1/31/2018.
 */

public class Util {

    private static ProgressDialog progressDialog;

    public static Dialog showProDialog(Context context) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = new ProgressDialog(context, R.style.NewDialog);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return progressDialog;
    }

    public static Dialog dismissProDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return progressDialog;
    }

    public static boolean isDeviceOnline(Context context) {
        boolean isDeviceOnLine = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            isDeviceOnLine = netInfo != null && netInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeviceOnLine;
    }

    public static void showCenteredToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void openKeyBoard(final Context context, View textView) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(textView, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyBoardMethod(final Context con, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public static void popFragment(Context context) {
        try {
            AppCompatActivity activity = (AppCompatActivity) context;
            if (activity != null) {
                FragmentManager manager = activity.getSupportFragmentManager();
                manager.popBackStack();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
