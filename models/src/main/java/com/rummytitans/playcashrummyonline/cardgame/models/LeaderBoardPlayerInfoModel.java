package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

public class LeaderBoardPlayerInfoModel {

    @SerializedName("TotalPoints")
    public double TotalPoints;
    @SerializedName("AvtaarID")
    public int AvtaarID;
    @SerializedName("MatchName")
    public String MatchName;
    @SerializedName("TeamID")
    public long TeamID;
    @SerializedName("Points")
    public double Points;
}
