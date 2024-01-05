package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JoinedTeamModel {


    @SerializedName("Categories")
    public List<Categories> Categories;
    @SerializedName("TeamTotalPoints")
    public double TeamTotalPoints;
    @SerializedName("Team3Count")
    public int Team3Count;
    @SerializedName("Team2Count")
    public int Team2Count;
    @SerializedName("Team1Count")
    public int Team1Count;
    @SerializedName("IsJoin")
    public boolean IsJoin;
    @SerializedName("ViceCaptainImage")
    public String ViceCaptainImage;
    @SerializedName("CaptainImage")
    public String CaptainImage;
    @SerializedName("ViceCaptainName")
    public String ViceCaptainName;
    @SerializedName("CaptainName")
    public String CaptainName;
    @SerializedName("TeamNo")
    public String TeamNo;
    @SerializedName("TeamID")
    public Long TeamID;

    public static class Categories {
        @SerializedName("Cc")
        public int Cc;
        @SerializedName("Cn")
        public String Cn;
    }
}
