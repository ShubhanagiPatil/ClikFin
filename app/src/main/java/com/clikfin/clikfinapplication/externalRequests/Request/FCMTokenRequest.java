package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class FCMTokenRequest {
    @SerializedName("fcmToken")
    String fcmToken;
    @SerializedName("userId")
    String userId;

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
