package com.gyanbooster.dao.login_response;


import java.io.Serializable;

/**
 * Created by Dell on 1/31/2018.
 */

public class LoginResponse implements Serializable {

    public static final String LOGINRESPONSE = "LoginResponse";
    public static final String KEY_USERID = "UserID";
    public static final String KEY_USEREMAIL = "UserEmail";
    public static final String KEY_USERPHONE = "UserPhone";
    public static final String KEY_FRONT_USER_NAME = "UserFrontUserName";
    public static final String KEY_USER_IMAGE = "UserImage";


    private String status;
    private String code;
    private String message;
    private UserData user;

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }



}
