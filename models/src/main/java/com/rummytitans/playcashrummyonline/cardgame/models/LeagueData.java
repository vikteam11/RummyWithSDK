package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class LeagueData implements Serializable {

    @Expose
    @SerializedName("IsPercentageWinners")
    public String IsPercentageWinners;
    @Expose
    @SerializedName("IsPercentage")
    public boolean IsPercentage;
    @Expose
    @SerializedName("IsChat")
    public boolean IsChat;
    @Expose
    @SerializedName("LeaugeCount")
    public int LeaugeCount;
    @Expose
    @SerializedName("MatchID")
    public String MatchID;
    @Expose
    @SerializedName("WiningAmount")
    public double WiningAmount;
    @Expose
    @SerializedName("Title")
    public String Title;
    @Expose
    @SerializedName("NoofWinners")
    public String NoofWinners;
    @Expose
    @SerializedName("Winner")
    public String Winner;
    @Expose
    @SerializedName("NoofMembers")
    public int NoofMembers;
    @Expose
    @SerializedName("IsMultiple")
    public boolean IsMultiple;


    @Expose
    @SerializedName("BonusPercentage")
    public int BonusPercentage;

    @Expose
    @SerializedName("IsConfirm")
    public boolean IsConfirm;

    @Expose
    @SerializedName("IsGuaranted")
    public boolean IsGuaranted;
    @Expose
    @SerializedName("Fees")
    public int Fees;
    @Expose
    @SerializedName("SecoundFee")
    public int SecoundFee;
    @Expose
    @SerializedName("LeaugeID")
    public int LeaugeID;
    @SerializedName("LeaugeCode")
    public String LeaugeCode;
    @SerializedName("UserCount")
    public int UserCount;
    @SerializedName("CategoryId")
    public int CategoryId;
    public String CategoryTitle;
    @SerializedName("IsjoinAllow")
    public boolean IsjoinAllow;
    @SerializedName("IsjoinMessage")
    public String IsjoinMessage;
    @Expose
    @SerializedName("LeagueMsg")
    public String LeagueMsg;
    @SerializedName("PassAvailable")
    public boolean PassAvailable;

    @SerializedName("IsPrivate")
    public boolean IsPrivate;

    @SerializedName("IsCancelled")
    public boolean IsCancelled;

    @Expose
    @SerializedName("LeagueInfo")
    public ArrayList<LeagueInfo> LeagueInfo;
    @Expose
    @SerializedName("AllowJoinCount")
    public int AllowJoinCount;

    public boolean allreadyJoined = false;

    @Expose
    @SerializedName("ChatUrl")
    public String ChatUrl;
    @Expose
    @SerializedName("MessageCount")
    public  int MessageCount;

    public static class LeagueInfo implements Serializable {
        @Expose
        @SerializedName("ImageUrl")
        public String ImageUrl;
        @Expose
        @SerializedName("Title")
        public String Title;
        @Expose
        @SerializedName("Tooltip")
        public String Tooltip;
        @Expose
        @SerializedName("Type")
        public String Type;
    }
}
