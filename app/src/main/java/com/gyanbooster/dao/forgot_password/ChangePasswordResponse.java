package com.gyanbooster.dao.forgot_password;

import com.gyanbooster.dao.login_response.UserData;

import java.io.Serializable;

public class ChangePasswordResponse implements Serializable {


    private String status;
    private String code;
    private String message;
    private ChangePasswordUserData user;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChangePasswordUserData getUser() {
        return user;
    }

    public void setUser(ChangePasswordUserData user) {
        this.user = user;
    }
}
