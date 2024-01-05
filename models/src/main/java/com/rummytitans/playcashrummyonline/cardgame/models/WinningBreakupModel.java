package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WinningBreakupModel {

    @SerializedName("Response")
    public ArrayList<Response> Response;
    @SerializedName("CurrentDate")
    public String CurrentDate;
    @SerializedName("Message")
    public String Message;
    @SerializedName("TokenExpire")
    public boolean TokenExpire;
    @SerializedName("Status")
    public boolean Status;
    @SerializedName("Terms")
    public String Terms;

    public static class Response {
        @SerializedName("IsPer")
        public boolean IsPer;
        @SerializedName("RankAmmount")
        public String RankAmmount;
        @SerializedName("extraMsg")
        public String extraMsg;
        @SerializedName("Image")
        public String imgUrl;
        @SerializedName("Title")
        public String Title;
        @SerializedName("WinningAndPoints")
        public List<TeamMessageModel> teamMessageModel;
    }

    public static class TeamMessageModel{
        @SerializedName("WinningMessage")
        public String WinningMessage;
        @SerializedName("TotalPoints")
        public String TotalPoints;
    }
}
