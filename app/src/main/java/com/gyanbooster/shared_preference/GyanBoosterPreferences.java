package com.gyanbooster.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.gyanbooster.dao.login_response.LoginResponse;


/**
 * Created by Dell on 1/31/2018.
 */
public class GyanBoosterPreferences {
    private static Context mContext;
    private static SharedPreferences preferences = null;
    private static SharedPreferences.Editor editor;
    public static String PREFERENCES_NAME = "test_login_preference";
    public static String IS_SPLASH_SCREEN_SHOW = "is_splashh_screen_show";


    public static void init(Context mContext) {
        GyanBoosterPreferences.mContext = mContext;
        GyanBoosterPreferences.preferences = mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        GyanBoosterPreferences.editor = preferences.edit();
    }


    public static boolean getSplashScreenShow() {
        return preferences.getBoolean(IS_SPLASH_SCREEN_SHOW, false);
    }

    public static void setSplashScreenShow(boolean id) {
        editor.putBoolean(IS_SPLASH_SCREEN_SHOW, id);
    }

    public static String getUserId() {
        return preferences.getString(LoginResponse.KEY_USERID, "");
    }

    public static void setUserId(String id) {
        editor.putString(LoginResponse.KEY_USERID, id);
    }

    public static String getUserFrontName() {
        return preferences.getString(LoginResponse.KEY_FRONT_USER_NAME, "");
    }

    public static void setUserFrontName(String frontName) {
        editor.putString(LoginResponse.KEY_FRONT_USER_NAME, frontName);
    }

    public static String getUserEmail() {
        return preferences.getString(LoginResponse.KEY_USEREMAIL, "");
    }

    public static void setUserEmail(String email) {
        editor.putString(LoginResponse.KEY_USEREMAIL, email);
    }

    public static String getUserPhone() {
        return preferences.getString(LoginResponse.KEY_USERPHONE, "");
    }

    public static void setUserPhone(String phone) {
        editor.putString(LoginResponse.KEY_USERPHONE, phone);
    }

    public static String getUserImage() {
        return preferences.getString(LoginResponse.KEY_USER_IMAGE, "");
    }

    public static void setUserImage(String userImage) {
        editor.putString(LoginResponse.KEY_USER_IMAGE, userImage);
    }


    public static void savePreferencese() {
        editor.commit();
    }

    public static void removeValueForKey(String key) {
        if (key != null && !key.isEmpty()) {
            editor.remove(key);
            editor.commit();
        }
    }

    public static void clearPreferences() {
        editor.clear();
        savePreferencese();
    }


}
