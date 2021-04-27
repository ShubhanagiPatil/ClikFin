package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class PersonalDetails {
    @SerializedName("firstName")
    String firstName;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("middleName")
    String middleName;
    @SerializedName("motherName")
    String motherName;
    @SerializedName("spouseName")
    String spouseName;
    @SerializedName("fatherName")
    String fatherName;
    @SerializedName("educationalQualification")
    String educationalQualification;

    public String getEducationalQualification() {
        return educationalQualification;
    }

    public void setEducationalQualification(String educationalQualification) {
        this.educationalQualification = educationalQualification;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    @SerializedName("phoneNumber")
    String phoneNumber;
    @SerializedName("email")
    String email;
    @SerializedName("aadhaarNumber")
    String aadhaarNumber;
    @SerializedName("panNumber")
    String panNumber;
    @SerializedName("maritalStatus")
    String maritalStatus;
    @SerializedName("gender")
    String gender;

    public String getPermanentAddressStayingFor() {
        return permanentAddressStayingFor;
    }

    public void setPermanentAddressStayingFor(String permanentAddressStayingFor) {
        this.permanentAddressStayingFor = permanentAddressStayingFor;
    }

    public String getPermanentAddressType() {
        return permanentAddressType;
    }

    public void setPermanentAddressType(String permanentAddressType) {
        this.permanentAddressType = permanentAddressType;
    }

    @SerializedName("permanentAddressStayingFor")
    String permanentAddressStayingFor;
    @SerializedName("permanentAddressType")
    String permanentAddressType;
    @SerializedName("dateOfBirth")
    String dateOfBirth;
    @SerializedName("currentAddressLine1")
    String currentAddressLine1;
    @SerializedName("currentAddressLine2")
    String currentAddressLine2;
    @SerializedName("currentAddressLandmark")
    String currentAddressLandmark;
    @SerializedName("currentAddressState")
    String currentAddressState;
    @SerializedName("currentAddressCity")
    String currentAddressCity;
    @SerializedName("currentAddressPinCode")
    String currentAddressPinCode;
    @SerializedName("permanentAddressLine1")
    String permanentAddressLine1;
    @SerializedName("permanentAddressLine2")
    String permanentAddressLine2;
    @SerializedName("permanentAddressLandmark")
    String permanentAddressLandmark;
    @SerializedName("permanentAddressState")
    String permanentAddressState;
    @SerializedName("permanentAddressCity")
    String permanentAddressCity;
    @SerializedName("permanentAddressPinCode")
    String permanentAddressPinCode;
    @SerializedName("addressProof")
    String addressProof;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCurrentAddressLine1() {
        return currentAddressLine1;
    }

    public void setCurrentAddressLine1(String currentAddressLine1) {
        this.currentAddressLine1 = currentAddressLine1;
    }

    public String getCurrentAddressLine2() {
        return currentAddressLine2;
    }

    public void setCurrentAddressLine2(String currentAddressLine2) {
        this.currentAddressLine2 = currentAddressLine2;
    }

    public String getCurrentAddressLandmark() {
        return currentAddressLandmark;
    }

    public void setCurrentAddressLandmark(String currentAddressLandmark) {
        this.currentAddressLandmark = currentAddressLandmark;
    }

    public String getCurrentAddressState() {
        return currentAddressState;
    }

    public void setCurrentAddressState(String currentAddressState) {
        this.currentAddressState = currentAddressState;
    }

    public String getCurrentAddressCity() {
        return currentAddressCity;
    }

    public void setCurrentAddressCity(String currentAddressCity) {
        this.currentAddressCity = currentAddressCity;
    }

    public String getCurrentAddressPinCode() {
        return currentAddressPinCode;
    }

    public void setCurrentAddressPinCode(String currentAddressPinCode) {
        this.currentAddressPinCode = currentAddressPinCode;
    }

    public String getPermanentAddressLine1() {
        return permanentAddressLine1;
    }

    public void setPermanentAddressLine1(String permanentAddressLine1) {
        this.permanentAddressLine1 = permanentAddressLine1;
    }

    public String getPermanentAddressLine2() {
        return permanentAddressLine2;
    }

    public void setPermanentAddressLine2(String permanentAddressLine2) {
        this.permanentAddressLine2 = permanentAddressLine2;
    }

    public String getPermanentAddressLandmark() {
        return permanentAddressLandmark;
    }

    public void setPermanentAddressLandmark(String permanentAddressLandmark) {
        this.permanentAddressLandmark = permanentAddressLandmark;
    }

    public String getPermanentAddressState() {
        return permanentAddressState;
    }

    public void setPermanentAddressState(String permanentAddressState) {
        this.permanentAddressState = permanentAddressState;
    }

    public String getPermanentAddressCity() {
        return permanentAddressCity;
    }

    public void setPermanentAddressCity(String permanentAddressCity) {
        this.permanentAddressCity = permanentAddressCity;
    }

    public String getPermanentAddressPinCode() {
        return permanentAddressPinCode;
    }

    public void setPermanentAddressPinCode(String permanentAddressPinCode) {
        this.permanentAddressPinCode = permanentAddressPinCode;
    }

    public String getAddressProof() {
        return addressProof;
    }

    public void setAddressProof(String addressProof) {
        this.addressProof = addressProof;
    }
}
