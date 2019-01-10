package com.gyanbooster.dao.profile_response;

import java.io.Serializable;

public class DashboardResponse implements Serializable {

    private UserProfileData user;
    private String status;
    private String code;

    public UserProfileData getUser() {
        return user;
    }

    public void setUser(UserProfileData user) {
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
}
