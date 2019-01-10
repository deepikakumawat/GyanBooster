package com.gyanbooster.dao.checkout;


import java.io.Serializable;

/**
 * Created by Dell on 1/31/2018.
 */

public class PaymentResponse implements Serializable {



    private String status;
    private String code;
    private String message;
    private PayData pay;

    public PayData getPay() {
        return pay;
    }

    public void setPay(PayData pay) {
        this.pay = pay;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
