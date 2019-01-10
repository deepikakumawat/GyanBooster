package com.gyanbooster.dao.forgot_password;

import java.io.Serializable;

public class ForgotPasswordResponse implements Serializable {

    private String status;
    private String code;
    private String otp_type;
    private String user_id;

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

    public String getOtp_type() {
        return otp_type;
    }

    public void setOtp_type(String otp_type) {
        this.otp_type = otp_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
