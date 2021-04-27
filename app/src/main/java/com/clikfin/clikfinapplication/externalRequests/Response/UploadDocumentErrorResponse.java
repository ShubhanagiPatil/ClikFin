package com.clikfin.clikfinapplication.externalRequests.Response;

import com.google.gson.annotations.SerializedName;

public class UploadDocumentErrorResponse {
    @SerializedName("error")
    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
