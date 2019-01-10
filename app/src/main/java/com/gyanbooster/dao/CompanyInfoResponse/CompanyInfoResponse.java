package com.gyanbooster.dao.CompanyInfoResponse;



import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dell on 1/31/2018.
 */

public class CompanyInfoResponse implements Serializable {



    private CompanyInfoData company_details;
    private String status;
    private String code;

    public CompanyInfoData getCompany_details() {
        return company_details;
    }

    public void setCompany_details(CompanyInfoData company_details) {
        this.company_details = company_details;
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
