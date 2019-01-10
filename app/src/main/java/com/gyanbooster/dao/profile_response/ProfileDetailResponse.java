package com.gyanbooster.dao.profile_response;

import java.io.Serializable;

public class ProfileDetailResponse implements Serializable {

    private String msg;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
