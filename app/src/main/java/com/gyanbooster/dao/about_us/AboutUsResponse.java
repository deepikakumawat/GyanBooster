package com.gyanbooster.dao.about_us;



import com.gyanbooster.dao.course_category.PopularVideoData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dell on 1/31/2018.
 */

public class AboutUsResponse implements Serializable {



    private AboutData about;
    private String status;
    private String code;

    public AboutData getAbout() {
        return about;
    }

    public void setAbout(AboutData about) {
        this.about = about;
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
