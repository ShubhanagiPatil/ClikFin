package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("phoneNumber")
    String phoneNumber;
    @SerializedName("password")
    String password;
    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
