package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class ReferAndEarn {
    @SerializedName("name")
    String name;
    @SerializedName("contactNo")
    String contactNo;
    @SerializedName("city")
    String city;
    @SerializedName("referredByName")
    String referredByName;
    @SerializedName("referredByContactNo")
    String referredByContactNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReferredByName() {
        return referredByName;
    }

    public void setReferredByName(String referredByName) {
        this.referredByName = referredByName;
    }

    public String getReferredByContactNo() {
        return referredByContactNo;
    }

    public void setReferredByContactNo(String referredByContactNo) {
        this.referredByContactNo = referredByContactNo;
    }
}
