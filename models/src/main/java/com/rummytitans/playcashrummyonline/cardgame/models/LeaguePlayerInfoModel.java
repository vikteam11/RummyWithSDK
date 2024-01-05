package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

public class LeaguePlayerInfoModel {
    @SerializedName("Points")
    public double Points;
    @SerializedName("FxPerc")
    public double player4x;
    @SerializedName("TxPerc")
    public double player3x;
    @SerializedName("ViceCaptainPrec")
    public double ViceCaptainPrec;
    @SerializedName("CaptainPrec")
    public double CaptainPrec;
    @SerializedName("SelectionPrec")
    public double SelectionPrec;
    @SerializedName("PlayerName")
    public String PlayerName;
    @SerializedName("PlayerId")
    public int PlayerId;
    @SerializedName("TeamName")
    public String TeamName;
    @SerializedName("Image")
    public String Image;
    @SerializedName("IsSelected")
    public Boolean IsSelected;
    @SerializedName("CategoryID")
    public int CategoryID;
    public String CategoryName;
    public boolean isMatchLiveFantasy;
}
