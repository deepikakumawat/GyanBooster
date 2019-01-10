package com.gyanbooster.dao;


import java.io.Serializable;

/**
 * Created by Dell on 1/31/2018.
 */

public class RegisterResponse implements Serializable {

    public static final String LOGINRESPONSE = "REGISTERRESPONSE";


    private String status;
    private String code;
    private String user_id;
    private String otp_type;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

   public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOtp_type() {
        return otp_type;
    }

    public void setOtp_type(String otp_type) {
        this.otp_type = otp_type;
    }
}
