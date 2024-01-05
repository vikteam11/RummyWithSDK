package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlayerInfoModel {

    @Expose
    @SerializedName("MatchData")
    public List<MatchData> MatchData;
    @Expose
    @SerializedName("PlayerImage")
    public String PlayerImage;
    @Expose
    @SerializedName("SelectBy")
    public int SelectBy;
    @Expose
    @SerializedName("Role")
    public int Role;
    @Expose
    @SerializedName("TotalPoint")
    public double TotalPoint;
    @Expose
    @SerializedName("Credit")
    public double Credit;
    @Expose
    @SerializedName("PlayerTeam")
    public String PlayerTeam;
    @Expose
    @SerializedName("BattingStyle")
    public String BattingStyle;
    @Expose
    @SerializedName("BirthDetail")
    public String BirthDetail;
    @Expose
    @SerializedName("PlayerName")
    public String PlayerName;
    public String PlayerCategoryName;

    public class MatchData {
        @Expose
        @SerializedName("SelectBy")
        public int SelectBy;
        @Expose
        @SerializedName("Point")
        public double Point;
        @Expose
        @SerializedName("MatchDate")
        public String MatchDate;
        @Expose
        @SerializedName("MatchName")
        public String MatchName;
        @Expose
        @SerializedName("MatchCredit")
        public double MatchCredit;


    }

}
