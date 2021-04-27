package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class OTPSendRequest {
    @SerializedName("phoneNumber")
    String phoneNumber;
    @SerializedName("isRetry")
    boolean isRetry;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isRetry() {
        return isRetry;
    }

    public void setRetry(boolean retry) {
        isRetry = retry;
    }
}
