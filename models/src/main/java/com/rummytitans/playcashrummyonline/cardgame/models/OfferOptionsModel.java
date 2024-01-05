
package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OfferOptionsModel {


    private boolean state = true;

    @SerializedName("AddOffer")
    private Double mAddOffer;
    @SerializedName("ColorCode")
    private String mColorCode;
    @SerializedName("GetOffer")
    private Double mGetOffer;
    @SerializedName("ID")
    private Long mID;
    @SerializedName("IsActive")
    private Boolean mIsActive;
    @SerializedName("IsFirst")
    private Boolean mIsFirst;
    @SerializedName("ValidFrom")
    private String mValidFrom;
    @SerializedName("ValidTo")
    private String mValidTo;

    public Double getAddOffer() {
        return mAddOffer;
    }

    public void setAddOffer(Double addOffer) {
        mAddOffer = addOffer;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getColorCode() {
        return mColorCode;
    }

    public void setColorCode(String colorCode) {
        mColorCode = colorCode;
    }

    public Double getGetOffer() {
        return mGetOffer;
    }

    public void setGetOffer(Double getOffer) {
        mGetOffer = getOffer;
    }

    public Long getID() {
        return mID;
    }

    public void setID(Long iD) {
        mID = iD;
    }

    public Boolean getIsActive() {
        return mIsActive;
    }

    public void setIsActive(Boolean isActive) {
        mIsActive = isActive;
    }

    public Boolean getIsFirst() {
        return mIsFirst;
    }

    public void setIsFirst(Boolean isFirst) {
        mIsFirst = isFirst;
    }

    public String getValidFrom() {
        return mValidFrom;
    }

    public void setValidFrom(String validFrom) {
        mValidFrom = validFrom;
    }

    public String getValidTo() {
        return mValidTo;
    }

    public void setValidTo(String validTo) {
        mValidTo = validTo;
    }

}
