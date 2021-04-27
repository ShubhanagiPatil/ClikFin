package com.clikfin.clikfinapplication.externalRequests.Response;

import com.google.gson.annotations.SerializedName;

public class AuthenticatedUser {
    @SerializedName("userId")
    String userId;
    @SerializedName("token")
    String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
