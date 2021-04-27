package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class EmployeeDetails {
    @SerializedName("companyName")
    String companyName;
    @SerializedName("employmentType")
    String employmentType;
    @SerializedName("salaryMode")
    String salaryMode;
    @SerializedName("salary")
    double salary;
    @SerializedName("designation")
    String designation;
    @SerializedName("currentExperience")
    String currentExperience;
    @SerializedName("overallExperience")
    String overallExperience;
    @SerializedName("purpose")
    String purpose;
    @SerializedName("organizationType")
    String organizationType;

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCurrentExperience() {
        return currentExperience;
    }

    public void setCurrentExperience(String currentExperience) {
        this.currentExperience = currentExperience;
    }

    public String getOverallExperience() {
        return overallExperience;
    }

    public void setOverallExperience(String overallExperience) {
        this.overallExperience = overallExperience;
    }

    @SerializedName("officialEmail")
    String officialEmail;
    @SerializedName("officialAddressLine1")
    String officialAddressLine1;
    @SerializedName("officialAddressLine2")
    String officialAddressLine2;
    @SerializedName("officialAddressLandmark")
    String officialAddressLandmark;
    @SerializedName("officialAddressState")
    String officialAddressState;
    @SerializedName("officialAddressCity")
    String officialAddressCity;
    @SerializedName("officialAddressPinCode")
    String officialAddressPinCode;
    @SerializedName("officePhoneNumber")
    String officePhoneNumber;
    @SerializedName("previousLoanAmount")
    double previousLoanAmount;
    @SerializedName("monthlyEMI")
    double monthlyEMI;
    @SerializedName("financierName")
    String financierName;
    @SerializedName("amount")
    double amount;
    @SerializedName("tenure")
    String tenure;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getSalaryMode() {
        return salaryMode;
    }

    public void setSalaryMode(String salaryMode) {
        this.salaryMode = salaryMode;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    public String getOfficialAddressLine1() {
        return officialAddressLine1;
    }

    public void setOfficialAddressLine1(String officialAddressLine1) {
        this.officialAddressLine1 = officialAddressLine1;
    }

    public String getOfficialAddressLine2() {
        return officialAddressLine2;
    }

    public void setOfficialAddressLine2(String officialAddressLine2) {
        this.officialAddressLine2 = officialAddressLine2;
    }

    public String getOfficialAddressLandmark() {
        return officialAddressLandmark;
    }

    public void setOfficialAddressLandmark(String officialAddressLandmark) {
        this.officialAddressLandmark = officialAddressLandmark;
    }

    public String getOfficialAddressState() {
        return officialAddressState;
    }

    public void setOfficialAddressState(String officialAddressState) {
        this.officialAddressState = officialAddressState;
    }

    public String getOfficialAddressCity() {
        return officialAddressCity;
    }

    public void setOfficialAddressCity(String officialAddressCity) {
        this.officialAddressCity = officialAddressCity;
    }

    public String getOfficialAddressPinCode() {
        return officialAddressPinCode;
    }

    public void setOfficialAddressPinCode(String officialAddressPinCode) {
        this.officialAddressPinCode = officialAddressPinCode;
    }

    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public void setOfficePhoneNumber(String officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }


    public String getFinancierName() {
        return financierName;
    }

    public void setFinancierName(String financierName) {
        this.financierName = financierName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getPreviousLoanAmount() {
        return previousLoanAmount;
    }

    public void setPreviousLoanAmount(double previousLoanAmount) {
        this.previousLoanAmount = previousLoanAmount;
    }

    public double getMonthlyEMI() {
        return monthlyEMI;
    }

    public void setMonthlyEMI(double monthlyEMI) {
        this.monthlyEMI = monthlyEMI;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }


}
