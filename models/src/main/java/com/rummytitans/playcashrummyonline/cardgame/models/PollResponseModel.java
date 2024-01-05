package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PollResponseModel {

    @SerializedName("ListOption")
    public ArrayList<PollOptionsModel> ListOption;
    @SerializedName("Type1Value")
    public String Type1Value;
    @SerializedName("Type1")
    public String Type1;
    @SerializedName("PoleType")
    public int PoleType;
    @SerializedName("Title")
    public String Title;
    @SerializedName("Image")
    public String Image;
    @SerializedName("ID")
    public int ID;
    @SerializedName("Scratch")
    public Scratch Scratch;
    @SerializedName("MatchOfferData")
    private MatchOfferData MatchOfferData;
    @SerializedName("Offer")
    private Boolean Offer;
    @SerializedName("OfferOptions")
    private ArrayList<OfferOptionsModel> OfferOptions;
    @SerializedName("PopupType")
    private Long PopupType;
    @SerializedName("SubTitle")
    private Object SubTitle;
    @SerializedName("Description")
    private String Description;
    @SerializedName("Label")
    public String Label;


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Object getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(Object subTitle) {
        SubTitle = subTitle;
    }

    public Long getPopupType() {
        return PopupType;
    }

    public void setPopupType(Long popupType) {
        PopupType = popupType;
    }

    public ArrayList<OfferOptionsModel> getOfferOptions() {
        return OfferOptions;
    }

    public void setOfferOptions(ArrayList<OfferOptionsModel> offerOptions) {
        OfferOptions = offerOptions;
    }

    public Boolean getOffer() {
        return Offer;
    }

    public void setOffer(Boolean offer) {
        Offer = offer;
    }

    public com.rummytitans.playcashrummyonline.cardgame.models.MatchOfferData getMatchOfferData() {
        return MatchOfferData;
    }

    public void setMatchOfferData(com.rummytitans.playcashrummyonline.cardgame.models.MatchOfferData matchOfferData) {
        MatchOfferData = matchOfferData;
    }

    public static class Scratch {
        @SerializedName("Message")
        public String Message;
        @SerializedName("TText")
        public String TText;
        @SerializedName("SText")
        public String SText;
        @SerializedName("FText")
        public String FText;
        @SerializedName("ShareMessage")
        public String ShareMessage;
        @SerializedName("ShareImage")
        public String ShareImage;
        public boolean ButtonShow;


    }


}
