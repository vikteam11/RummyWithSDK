package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PlayerPointBreakupModel implements Serializable {

    @SerializedName("Response")
    public List<Response> Response;
    @SerializedName("CurrentDate")
    public String CurrentDate;
    @SerializedName("IsAutoScrollHeader")
    public boolean IsAutoScrollHeader;
    @SerializedName("Message")
    public String Message;
    @SerializedName("TokenExpire")
    public boolean TokenExpire;
    @SerializedName("Status")
    public boolean Status;
    public boolean disableSwipe;

    public static class Response implements Serializable {
        @SerializedName("MatchData")
        public List<MatchData> MatchData;
        @SerializedName("PlayerImage")
        public String PlayerImage;
        @SerializedName("Role")
        public int Role;
        @SerializedName("TotalPoint")
        public float TotalPoint;
        @SerializedName("Credit")
        public double Credit;
        @SerializedName("PlayerName")
        public String PlayerName;
        public boolean isSelected = false;
        public String PlayerCategoryName;
        public String SportRole;
        @SerializedName("SelectedBy")
        public double SelectedBy = 0;
        public String TeamName;
    }

    public static class MatchData implements Serializable{
        @SerializedName("Point")
        public float Point;
        @SerializedName("Real")
        public String Real;
        @SerializedName("Action")
        public String Action;

    }
}
