package com.gyanbooster.dao;


import java.io.Serializable;

/**
 * Created by Dell on 1/31/2018.
 */

public class OTPValidateResponse implements Serializable {



    private String status;
    private String code;
    private String message;


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
