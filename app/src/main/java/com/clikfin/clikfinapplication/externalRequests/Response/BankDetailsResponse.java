package com.clikfin.clikfinapplication.externalRequests.Response;

import com.google.gson.annotations.SerializedName;

public class BankDetailsResponse {
    @SerializedName("applicationId")
    String applicationId;
    @SerializedName("status")
    String status;
    @SerializedName("documentStatus")
    UploadDocumentResponse  documentStatus;

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

    public UploadDocumentResponse getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(UploadDocumentResponse documentStatus) {
        this.documentStatus = documentStatus;
    }
}
