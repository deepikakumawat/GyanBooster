package com.gyanbooster.dao.checkout;


import java.io.Serializable;

/**
 * Created by Dell on 1/31/2018.
 */

public class PayData implements Serializable {


    public static final String PAYDATA = "PAYDATA";
    private String payment_id;
    private String p_txn_id;
    private String p_student_id;
    private String p_course_id;
    private String p_ammount;
    private String p_status;
    private String p_add_date;
    private String p_last_date;
    private String course_price;
    private String course_name;
    private String p_gst_num;
    private String p_wallet;
    private String p_coupon;
    private String front_user_fname;
    private String front_user_email;

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getP_txn_id() {
        return p_txn_id;
    }

    public void setP_txn_id(String p_txn_id) {
        this.p_txn_id = p_txn_id;
    }

    public String getP_student_id() {
        return p_student_id;
    }

    public void setP_student_id(String p_student_id) {
        this.p_student_id = p_student_id;
    }

    public String getP_course_id() {
        return p_course_id;
    }

    public void setP_course_id(String p_course_id) {
        this.p_course_id = p_course_id;
    }

    public String getP_ammount() {
        return p_ammount;
    }

    public void setP_ammount(String p_ammount) {
        this.p_ammount = p_ammount;
    }

    public String getP_status() {
        return p_status;
    }

    public void setP_status(String p_status) {
        this.p_status = p_status;
    }

    public String getP_add_date() {
        return p_add_date;
    }

    public void setP_add_date(String p_add_date) {
        this.p_add_date = p_add_date;
    }

    public String getP_last_date() {
        return p_last_date;
    }

    public void setP_last_date(String p_last_date) {
        this.p_last_date = p_last_date;
    }

    public String getCourse_price() {
        return course_price;
    }

    public void setCourse_price(String course_price) {
        this.course_price = course_price;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getP_gst_num() {
        return p_gst_num;
    }

    public void setP_gst_num(String p_gst_num) {
        this.p_gst_num = p_gst_num;
    }

    public String getP_wallet() {
        return p_wallet;
    }

    public void setP_wallet(String p_wallet) {
        this.p_wallet = p_wallet;
    }

    public String getP_coupon() {
        return p_coupon;
    }

    public void setP_coupon(String p_coupon) {
        this.p_coupon = p_coupon;
    }

    public String getFront_user_fname() {
        return front_user_fname;
    }

    public void setFront_user_fname(String front_user_fname) {
        this.front_user_fname = front_user_fname;
    }

    public String getFront_user_email() {
        return front_user_email;
    }

    public void setFront_user_email(String front_user_email) {
        this.front_user_email = front_user_email;
    }
}
