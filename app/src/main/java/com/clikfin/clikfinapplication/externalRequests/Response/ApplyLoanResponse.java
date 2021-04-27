package com.clikfin.clikfinapplication.externalRequests.Response;

import com.google.gson.annotations.SerializedName;

public class ApplyLoanResponse {
    @SerializedName("applicationId")
    String applicationId;
    @SerializedName("status")
    String status;


    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
