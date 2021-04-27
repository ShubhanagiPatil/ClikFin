package com.clikfin.clikfinapplication.externalRequests.Response;

import com.google.gson.annotations.SerializedName;

public class UploadDocumentResponse {
    @SerializedName("CURRENT_ADDRESS_PROOF")
    DocumentObj CURRENT_ADDRESS_PROOF;
    @SerializedName("AADHAAR_CARD_FRONT")
    DocumentObj AADHAAR_CARD_FRONT;
    @SerializedName("AADHAAR_CARD_BACK")
    DocumentObj AADHAAR_CARD_BACK;
    @SerializedName("PAN_CARD")
    DocumentObj PAN_CARD;
    @SerializedName("BANK_STATEMENT_1")
    DocumentObj BANK_STATEMENT_1;
    @SerializedName("BANK_STATEMENT_2")
    DocumentObj BANK_STATEMENT_2;
    @SerializedName("BANK_STATEMENT_3")
    DocumentObj BANK_STATEMENT_3;
    @SerializedName("PAY_SLIP_1")
    DocumentObj PAY_SLIP_1;
    @SerializedName("PAY_SLIP_2")
    DocumentObj PAY_SLIP_2;
    @SerializedName("PAY_SLIP_3")
    DocumentObj PAY_SLIP_3;
    @SerializedName("PHOTO")
    DocumentObj PHOTO;
    @SerializedName("COMPANY_ID")
    DocumentObj COMPANY_ID;

    public DocumentObj getCURRENT_ADDRESS_PROOF() {
        return CURRENT_ADDRESS_PROOF;
    }

    public void setCURRENT_ADDRESS_PROOF(DocumentObj CURRENT_ADDRESS_PROOF) {
        this.CURRENT_ADDRESS_PROOF = CURRENT_ADDRESS_PROOF;
    }

    public DocumentObj getAADHAAR_CARD_FRONT() {
        return AADHAAR_CARD_FRONT;
    }

    public void setAADHAAR_CARD_FRONT(DocumentObj AADHAAR_CARD_FRONT) {
        this.AADHAAR_CARD_FRONT = AADHAAR_CARD_FRONT;
    }

    public DocumentObj getAADHAAR_CARD_BACK() {
        return AADHAAR_CARD_BACK;
    }

    public void setAADHAAR_CARD_BACK(DocumentObj AADHAAR_CARD_BACK) {
        this.AADHAAR_CARD_BACK = AADHAAR_CARD_BACK;
    }

    public DocumentObj getPAN_CARD() {
        return PAN_CARD;
    }

    public void setPAN_CARD(DocumentObj PAN_CARD) {
        this.PAN_CARD = PAN_CARD;
    }

    public DocumentObj getBANK_STATEMENT_1() {
        return BANK_STATEMENT_1;
    }

    public void setBANK_STATEMENT_1(DocumentObj BANK_STATEMENT_1) {
        this.BANK_STATEMENT_1 = BANK_STATEMENT_1;
    }

    public DocumentObj getBANK_STATEMENT_2() {
        return BANK_STATEMENT_2;
    }

    public void setBANK_STATEMENT_2(DocumentObj BANK_STATEMENT_2) {
        this.BANK_STATEMENT_2 = BANK_STATEMENT_2;
    }

    public DocumentObj getBANK_STATEMENT_3() {
        return BANK_STATEMENT_3;
    }

    public void setBANK_STATEMENT_3(DocumentObj BANK_STATEMENT_3) {
        this.BANK_STATEMENT_3 = BANK_STATEMENT_3;
    }

    public DocumentObj getPAY_SLIP_1() {
        return PAY_SLIP_1;
    }

    public void setPAY_SLIP_1(DocumentObj PAY_SLIP_1) {
        this.PAY_SLIP_1 = PAY_SLIP_1;
    }

    public DocumentObj getPAY_SLIP_2() {
        return PAY_SLIP_2;
    }

    public void setPAY_SLIP_2(DocumentObj PAY_SLIP_2) {
        this.PAY_SLIP_2 = PAY_SLIP_2;
    }

    public DocumentObj getPAY_SLIP_3() {
        return PAY_SLIP_3;
    }

    public void setPAY_SLIP_3(DocumentObj PAY_SLIP_3) {
        this.PAY_SLIP_3 = PAY_SLIP_3;
    }

    public DocumentObj getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(DocumentObj PHOTO) {
        this.PHOTO = PHOTO;
    }

    public DocumentObj getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(DocumentObj COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }
}
