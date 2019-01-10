package com.gyanbooster.dao.video;

import java.io.Serializable;

public class Queries implements Serializable{

    private String user_image;
    private String front_user_fname;
    private String date;
    private String qus;
    private String query_id;
    private String file;


    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getFront_user_fname() {
        return front_user_fname;
    }

    public void setFront_user_fname(String front_user_fname) {
        this.front_user_fname = front_user_fname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQus() {
        return qus;
    }

    public void setQus(String qus) {
        this.qus = qus;
    }

    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
