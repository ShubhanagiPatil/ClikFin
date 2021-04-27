package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class BankDetails {
    @SerializedName("accountHolderName")
    String accountHolderName;
    @SerializedName("accountNumber")
    String accountNumber;
    String reEnterAccountNumber;
    @SerializedName("ifscCode")
    String IFSCCode;
    @SerializedName("bankName")
    String bankName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getReEnterAccountNumber() {
        return reEnterAccountNumber;
    }

    public void setReEnterAccountNumber(String reEnterAccountNumber) {
        this.reEnterAccountNumber = reEnterAccountNumber;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public void setIFSCCode(String IFSCCode) {
        this.IFSCCode = IFSCCode;
    }
}
