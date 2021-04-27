package com.clikfin.clikfinapplication.externalRequests.Response;

import com.google.gson.annotations.SerializedName;

public class UploadDocName {
    @SerializedName("CURRENT_ADDRESS_PROOF")
    String CURRENT_ADDRESS_PROOF;

    public String getCURRENT_ADDRESS_PROOF() {
        return CURRENT_ADDRESS_PROOF;
    }

    public void setCURRENT_ADDRESS_PROOF(String CURRENT_ADDRESS_PROOF) {
        this.CURRENT_ADDRESS_PROOF = CURRENT_ADDRESS_PROOF;
    }
}
