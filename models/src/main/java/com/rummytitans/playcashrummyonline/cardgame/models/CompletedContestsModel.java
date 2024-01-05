package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompletedContestsModel implements Serializable {

    @SerializedName("liveMyLeauge")
    public ArrayList<LiveMyLeauge> liveMyLeauge;
    @SerializedName("liveMyScore")
    public LiveMyScore liveMyScore;
    @SerializedName("Information")
    public Information Information;


    public class Information implements Serializable {
        @SerializedName("InApp")
        public boolean InApp;
        @SerializedName("ImageUrl")
        public String ImageUrl;
        @SerializedName("Url")
        public String Url;
        @SerializedName("secInApp")
        public boolean secInApp;
        @SerializedName("secImageUrl")
        public String secImageUrl;
        @SerializedName("secUrl")
        public String secUrl;
        @SerializedName("IsBriefScoreCard")
        public Boolean IsBriefScoreCard;
        @SerializedName("IsDetailScoreCard")
        public Boolean IsDetailScoreCard;
    }

    public static class LiveMyLeauge implements Serializable {
        @SerializedName("LiveMyLeaugeTeam")
        public List<ContestInfoModel.LeaderBoardDown> LiveMyLeaugeTeam;
        @SerializedName("WinningAmount")
        public String WinningAmount;
        @SerializedName("Winner")
        public String Winner;
        @SerializedName("Fee")
        public int Fee;
        @SerializedName("Title")
        public String Title;
        @SerializedName("LeaugeId")
        public int LeaugeId;
        @SerializedName("NoofMembers")
        public String NoofMembers;
        @SerializedName("NoofWinners")
        public String NoofWinners;
        @SerializedName("IsPrivate")
        public boolean IsPrivate;
        @SerializedName("IsCancelled")
        public boolean IsCancelled;
        public boolean isPractice() {
            try {
                return Fee == 0 && Integer.parseInt(Winner) == 0;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public static class LiveMyScore implements Serializable {
        @SerializedName("TeamBScore")
        public String TeamBScore;
        @SerializedName("TeamAScore")
        public String TeamAScore;
        @SerializedName("TeamBName")
        public String TeamBName;
        @SerializedName("TeamAName")
        public String TeamAName;
        @SerializedName("TeamCName")
        public String TeamCName;
        @SerializedName("TeamCScore")
        public String TeamCScore;
        @SerializedName("Istest")
        public boolean Istest;
        @SerializedName("IsScoreShow")
        public boolean IsScoreShow;
        @SerializedName("LastUpdatedScore")
        public String LastUpdatedScore;
    }
}
