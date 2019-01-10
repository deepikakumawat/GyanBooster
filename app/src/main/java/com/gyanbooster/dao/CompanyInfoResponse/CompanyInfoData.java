package com.gyanbooster.dao.CompanyInfoResponse;

import java.io.Serializable;

public class CompanyInfoData implements Serializable {

    private String company_id;
    private String company_logo;
    private String company_address;
    private String company_phone1;
    private String company_phone2;
    private String company_email;
    private String company_gst_no;

    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCompany_phone1() {
        return company_phone1;
    }

    public void setCompany_phone1(String company_phone1) {
        this.company_phone1 = company_phone1;
    }

    public String getCompany_phone2() {
        return company_phone2;
    }

    public void setCompany_phone2(String company_phone2) {
        this.company_phone2 = company_phone2;
    }

    public String getCompany_gst_no() {
        return company_gst_no;
    }

    public void setCompany_gst_no(String company_gst_no) {
        this.company_gst_no = company_gst_no;
    }
}
