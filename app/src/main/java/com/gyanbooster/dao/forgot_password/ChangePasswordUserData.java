package com.gyanbooster.dao.forgot_password;

import java.io.Serializable;

class ChangePasswordUserData implements Serializable{

    private String front_user_token;
    private String front_user_id;
    private String front_user_email;
    private String front_user_role;

    public String getFront_user_token() {
        return front_user_token;
    }

    public void setFront_user_token(String front_user_token) {
        this.front_user_token = front_user_token;
    }

    public String getFront_user_id() {
        return front_user_id;
    }

    public void setFront_user_id(String front_user_id) {
        this.front_user_id = front_user_id;
    }

    public String getFront_user_email() {
        return front_user_email;
    }

    public void setFront_user_email(String front_user_email) {
        this.front_user_email = front_user_email;
    }

    public String getFront_user_role() {
        return front_user_role;
    }

    public void setFront_user_role(String front_user_role) {
        this.front_user_role = front_user_role;
    }
}
