package com.clikfin.clikfinapplication.externalRequests.Response;

import com.google.gson.annotations.SerializedName;

public class DocumentObj {
    @SerializedName("uploaded")
    boolean uploaded;
    @SerializedName("mandatory")
    boolean mandatory;

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
}

