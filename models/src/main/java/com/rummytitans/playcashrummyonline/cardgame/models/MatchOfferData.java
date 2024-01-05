
package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MatchOfferData {

    @SerializedName("Code")
    private String mCode;
    @SerializedName("Conditions")
    private String mConditions;
    @SerializedName("ContentCategoryImage")
    private String mContentCategoryImage;
    @SerializedName("Description")
    private String mDescription;
    @SerializedName("ActualFee")
    public String ActualFee;
    @SerializedName("TeamFlag1")
    private String mTeamFlag1;
    @SerializedName("TeamFlag2")
    private String mTeamFlag2;
    @SerializedName("Title")
    private String mTitle;
    @SerializedName("Fee")
    private float mFee;


    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getConditions() {
        return mConditions;
    }

    public void setConditions(String conditions) {
        mConditions = conditions;
    }

    public String getContentCategoryImage() {
        return mContentCategoryImage;
    }

    public void setContentCategoryImage(String contentCategoryImage) {
        mContentCategoryImage = contentCategoryImage;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public float getFee() {
        return mFee;
    }

    public void setFee(float fee) {
        mFee = fee;
    }

    public String getTeamFlag1() {
        return mTeamFlag1;
    }

    public void setTeamFlag1(String teamFlag1) {
        mTeamFlag1 = teamFlag1;
    }

    public String getTeamFlag2() {
        return mTeamFlag2;
    }

    public void setTeamFlag2(String teamFlag2) {
        mTeamFlag2 = teamFlag2;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
