package com.gyanbooster.api_calling;


import com.gyanbooster.constants.URLs;
import com.gyanbooster.dao.CompanyInfoResponse.CompanyInfoResponse;
import com.gyanbooster.dao.OTPResendResponse;
import com.gyanbooster.dao.OTPValidateResponse;
import com.gyanbooster.dao.PermissionToViewResponse;
import com.gyanbooster.dao.about_us.ContactUsResponse;
import com.gyanbooster.dao.course_details.AddReviewResponse;
import com.gyanbooster.dao.forgot_password.ChangePasswordResponse;
import com.gyanbooster.dao.forgot_password.ForgotPasswordOTPResponse;
import com.gyanbooster.dao.forgot_password.ForgotPasswordResponse;
import com.gyanbooster.dao.profile_response.DashboardResponse;
import com.gyanbooster.dao.profile_response.ProfileDetailResponse;
import com.gyanbooster.dao.profile_response.YourCourseResponse;
import com.gyanbooster.dao.about_us.AboutUsResponse;
import com.gyanbooster.dao.checkout.ApplyCouponResponse;
import com.gyanbooster.dao.checkout.PaymentResponse;
import com.gyanbooster.dao.course_category.PopularVideoResponse;
import com.gyanbooster.dao.course_details.CourseDetailsResponse;
import com.gyanbooster.dao.course_listing.CourseListingResponse;
import com.gyanbooster.dao.login_response.LoginResponse;
import com.gyanbooster.dao.RegisterResponse;
import com.gyanbooster.dao.course_category.CourseCategoryResponse;
import com.gyanbooster.dao.select_courses.SelectCourseResponse;
import com.gyanbooster.dao.video.AddQueryResponse;
import com.gyanbooster.dao.video.VideoResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Dell on 1/31/2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST(URLs.LOGIN_URL)
    Call<LoginResponse> login(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(URLs.REGISTER)
    Call<RegisterResponse> register(@FieldMap Map<String, Object> body);

    @POST(URLs.SELECT_COURSE)
    Call<SelectCourseResponse> selectCourse();

    @POST(URLs.POPULAR_VIDEOS)
    Call<PopularVideoResponse> popularVideo();

    @FormUrlEncoded
    @POST(URLs.COURSE_DETAILS)
    Call<CourseDetailsResponse> courseDetails(@Field("course_id") String body);

    @POST(URLs.COURSE_CATEGORY)
    Call<CourseCategoryResponse> courseCategory(@Body RequestBody body);

    @FormUrlEncoded
    @POST(URLs.COURSE_LISTING)
    Call<CourseListingResponse> courseListing(@Field("sub_course_id") String body);

    @FormUrlEncoded
    @POST(URLs.VIDEO)
    Call<VideoResponse> video(@Field("topic_id") String body);

    @FormUrlEncoded
    @POST(URLs.OTP_VERIFICATION)
    Call<OTPValidateResponse> otpVerification(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(URLs.RESEND_OTP)
    Call<OTPResendResponse> resendOTP(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(URLs.PAYMENT_API)
    Call<PaymentResponse> paymentAPi(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(URLs.APPLY_COUPON)
    Call<ApplyCouponResponse> applyCoupontAPi(@FieldMap Map<String, Object> body);

  /*  @FormUrlEncoded
    @POST(URLs.REPLY_BY_QUERY)
    Call<QueryResponse> queryAPI(@Field("query_id") String body);*/

    @FormUrlEncoded
    @POST(URLs.PERMISSION_TO_VIEW)
    Call<PermissionToViewResponse> permissionToView(@FieldMap Map<String, Object> body);

    @POST(URLs.ABOUT_PAGE)
    Call<AboutUsResponse> aboutUs();

    @POST(URLs.COMPANY_INFO)
    Call<CompanyInfoResponse> companyInfo();

    @FormUrlEncoded
    @POST(URLs.YOUR_COURSE)
    Call<YourCourseResponse> yourCourse(@Field("user_id") String body);

    @FormUrlEncoded
    @POST(URLs.DASHBOARD)
    Call<DashboardResponse> dashboard(@Field("user_id") String body);

    @FormUrlEncoded
    @POST(URLs.PROFILE_DETAILS)
    Call<ProfileDetailResponse> profileDetailsAPI(@FieldMap Map<String, Object> body);

    @Multipart
    @POST(URLs.PROFILE_DETAILS)
    Call<ProfileDetailResponse> profileDetailsAPI(@Part("user_id") RequestBody userId,
                                                  @Part("fname") RequestBody fName,
                                                  @Part("lname") RequestBody lName,
                                                  @Part("email") RequestBody email,
                                                  @Part("mob") RequestBody mob,
                                                  @Part("password") RequestBody password,
                                                  @Part MultipartBody.Part imageFile,
                                                  @Part("old_image") RequestBody oldImage
    );


    @FormUrlEncoded
    @POST(URLs.CONTACT_US)
    Call<ContactUsResponse> contactUs(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(URLs.ADD_REVIEW)
    Call<AddReviewResponse> addReview(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(URLs.ADD_QUERY)
    Call<AddQueryResponse> addQuery(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(URLs.FORGOT_PASSWORD)
    Call<ForgotPasswordResponse> forgotPassword(@Field("email") String body);


    @FormUrlEncoded
    @POST(URLs.CHANGE_PASSWORD)
    Call<ChangePasswordResponse> changePassword(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(URLs.FORGOT_OTP_VERIFICATION)
    Call<ForgotPasswordOTPResponse> forgotOTPVerification(@FieldMap Map<String, Object> body);

}
