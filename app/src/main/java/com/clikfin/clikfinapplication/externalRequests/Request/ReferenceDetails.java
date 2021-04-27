package com.clikfin.clikfinapplication.externalRequests.Request;

import com.google.gson.annotations.SerializedName;

public class ReferenceDetails {
    @SerializedName("reference1Name")
    String ref1Name;
    @SerializedName("reference2Name")
    String ref2Name;
    @SerializedName("reference1Number")
    String ref1ContactNo;
    @SerializedName("reference2Number")
    String ref2ContactNo;
    @SerializedName("reference1Relation")
    String ref1RelationShip;
    @SerializedName("reference2Relation")
    String ref2RelationShip;
    @SerializedName("reference1City")
    String ref1CityName;
    @SerializedName("reference2City")
    String ref2CityName;
String references;

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getRef1Name() {
        return ref1Name;
    }

    public void setRef1Name(String ref1Name) {
        this.ref1Name = ref1Name;
    }

    public String getRef2Name() {
        return ref2Name;
    }

    public void setRef2Name(String ref2Name) {
        this.ref2Name = ref2Name;
    }

    public String getRef1ContactNo() {
        return ref1ContactNo;
    }

    public void setRef1ContactNo(String ref1ContactNo) {
        this.ref1ContactNo = ref1ContactNo;
    }

    public String getRef2ContactNo() {
        return ref2ContactNo;
    }

    public void setRef2ContactNo(String ref2ContactNo) {
        this.ref2ContactNo = ref2ContactNo;
    }

    public String getRef1RelationShip() {
        return ref1RelationShip;
    }

    public void setRef1RelationShip(String ref1RelationShip) {
        this.ref1RelationShip = ref1RelationShip;
    }

    public String getRef2RelationShip() {
        return ref2RelationShip;
    }

    public void setRef2RelationShip(String ref2RelationShip) {
        this.ref2RelationShip = ref2RelationShip;
    }

    public String getRef1CityName() {
        return ref1CityName;
    }

    public void setRef1CityName(String ref1CityName) {
        this.ref1CityName = ref1CityName;
    }

    public String getRef2CityName() {
        return ref2CityName;
    }

    public void setRef2CityName(String ref2CityName) {
        this.ref2CityName = ref2CityName;
    }
}
