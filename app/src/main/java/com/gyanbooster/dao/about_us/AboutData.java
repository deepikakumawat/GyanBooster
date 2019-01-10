package com.gyanbooster.dao.about_us;

import java.io.Serializable;

public class AboutData implements Serializable {

    private String about_id;
    private String about_file;
    private String about_desc;


    public String getAbout_id() {
        return about_id;
    }

    public void setAbout_id(String about_id) {
        this.about_id = about_id;
    }

    public String getAbout_file() {
        return about_file;
    }

    public void setAbout_file(String about_file) {
        this.about_file = about_file;
    }

    public String getAbout_desc() {
        return about_desc;
    }

    public void setAbout_desc(String about_desc) {
        this.about_desc = about_desc;
    }
}
