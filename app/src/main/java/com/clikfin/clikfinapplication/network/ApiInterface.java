package com.clikfin.clikfinapplication.network;

import com.clikfin.clikfinapplication.externalRequests.Request.BankDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.EmployeeDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.FCMTokenRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.Login;
import com.clikfin.clikfinapplication.externalRequests.Request.OTPSendRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.OTPVerifyRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.PersonalDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.ReferAndEarn;
import com.clikfin.clikfinapplication.externalRequests.Request.ReferenceDetails;
import com.clikfin.clikfinapplication.externalRequests.Request.RegisterUser;
import com.clikfin.clikfinapplication.externalRequests.Response.ApplyLoanResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.AuthenticatedUser;
import com.clikfin.clikfinapplication.externalRequests.Response.BankDetailsResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocName;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocumentResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {
    @GET("/health")
    Call<String> healthCheck();

    @Headers("Content-type: application/json")
    @POST("/user/register")
    Call<AuthenticatedUser> registerUser(@Body RegisterUser registerUser);

    @Headers("Content-type: application/json")
    @POST("/user/login")
    Call<AuthenticatedUser> login(@Body Login login);

    @GET("/info/salaryMode")
    Call<String[]> getSalaryMode(@Header("x-clikfin-auth") String authToken);

    @GET("/info/addressProof")
    Call<String[]> getAddressProof(@Header("x-clikfin-auth") String authToken);

    @Headers("Content-type: application/json")
    @POST("/application/v2/personalInfo")
    Call<ApplyLoanResponse> postPersonalDetails(@Header("x-clikfin-auth") String authToken, @Body PersonalDetails personalDetails);

    @Headers("Content-type: application/json")
    @POST
    Call<ApplyLoanResponse> postEmployeeDetails(@Url String url, @Header("x-clikfin-auth") String authToken, @Body EmployeeDetails employeeDetails);

    @GET("/info/tenure")
    Call<String[]> getTenure(@Header("x-clikfin-auth") String authToken);

    @GET
    Call<ApplyLoanResponse> getLoanApplicationStatus(@Header("x-clikfin-auth") String authToken, @Url String url);


    @Headers("Content-type: application/json")
    @POST
    Call<ApplyLoanResponse> postReferencesDetails(@Url String url, @Header("x-clikfin-auth") String authToken, @Body ReferenceDetails referenceDetails);

    @Headers("Content-type: application/json")
    @POST("/application/referAndEarnInfo")
    Call<String> postReferAndEarnDetails(@Header("x-clikfin-auth") String authToken, @Body ReferAndEarn referAndEarn);

    @Headers("Content-type: application/json")
    @POST
    Call<BankDetailsResponse> postBankDetails(@Url String url, @Header("x-clikfin-auth") String authToken, @Body BankDetails bankDetails);


    @Multipart
    @PUT
    Call<UploadDocumentResponse> putDocumentUpload(@Part MultipartBody.Part body, @Url String url, @Header("x-clikfin-auth") String authToken, @Query("documentName") String documentName, @Query("password") String password);

    @GET("/info/bank")
    Call<String[]> getBank(@Header("x-clikfin-auth") String authToken);

    @GET("/info/state")
    Call<String[]> getState(@Header("x-clikfin-auth") String authToken);

    @GET
    Call<UploadDocName> getUploadDocument(@Url String url, @Header("x-clikfin-auth") String authToken);

    @POST
    Call<Void> postAllDocumentUploadStatus(@Url String url, @Header("x-clikfin-auth") String authToken);

    @GET("/info/loanPurpose")
    Call<String[]> getLoanPurpose(@Header("x-clikfin-auth") String authToken);

    @GET("/info/organizationType")
    Call<String[]> getOrganizationType(@Header("x-clikfin-auth") String authToken);

    @GET("/info/educationalQualification")
    Call<String[]> getEducationalQualification(@Header("x-clikfin-auth") String authToken);

    @POST("/user/login")
    Call<String> postFCMToken(@Body FCMTokenRequest fcmTokenRequest);

    @Headers("Content-type: application/json")
    @POST("/otp/send")
    Call<String> postLoginWithMobileNumber(@Body OTPSendRequest otpSendRequest);

    @Headers("Content-type: application/json")
    @POST("/otp/verify")
    Call<String> postVerifyOtp(@Body OTPVerifyRequest otpVerifyRequest);

   /* @POST("/user/login")
    Call<String> postResendOtp(@Query("mobileNumber") String mobileNumber);*/
}
