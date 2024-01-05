package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ContestInfoModel implements Serializable {


    @Expose
    @SerializedName("Response")
    public Response Response;
    @Expose
    @SerializedName("Message")
    public String Message;
    @Expose
    @SerializedName("TokenExpire")
    public boolean TokenExpire;
    @Expose
    @SerializedName("IsTimesUp")
    public boolean IsTimesUp;
    @Expose
    @SerializedName("Status")
    public boolean Status;
    @Expose
    @SerializedName("CurrentDate")
    public String CurrentDate;

    public static class Response  implements Serializable{
        @Expose
        @SerializedName("LeaderBoardDown")
        public List<LeaderBoardDown> LeaderBoardDown;
        @Expose
        @SerializedName("LeagueDetail")
        public LeagueData LeagueDetail;
        @SerializedName("scoreBoard")
        public ScoreBoard scoreBoard;
        @SerializedName("Information")
        public Information Information;
    }

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
    }

    public class ScoreBoard  implements Serializable{
        @SerializedName("IsScoreShow")
        public boolean IsScoreShow;
        @SerializedName("Istest")
        public boolean IsTest;
        @SerializedName("TeamAName")
        public String TeamAName;
        @SerializedName("TeamBName")
        public String TeamBName;
        @SerializedName("TeamAScore")
        public String TeamAScore;
        @SerializedName("TeamBScore")
        public String TeamBScore;
        @SerializedName("TeamCName")
        public String TeamCName;
        @SerializedName("TeamCScore")
        public String TeamCScore;

    }

    public class LeaderBoardDown implements Serializable {
        @Expose
        @SerializedName("UserCount")
        public int UserCount;
        @Expose
        @SerializedName("Ismyteam")
        public boolean Ismyteam;
        @Expose
        @SerializedName("WiningAmount")
        public String WiningAmount;
        @Expose
        @SerializedName("Point")
        public double Point;
        @Expose
        @SerializedName("AvtaarId")
        public int AvtaarId;
        @Expose
        @SerializedName("TeamRank")
        public long TeamRank;
        @Expose
        @SerializedName("JoinedMatchId")
        public long JoinedMatchId;
        @Expose
        @SerializedName("TeamName")
        public String TeamName;
        @Expose
        @SerializedName("CompleteTime")
        public String CompleteTime;
        @Expose
        @SerializedName("WinnerMessage")
        public String WiningMessage;
        public int avatarId;
        @Expose
        @SerializedName("TName")
        public String TName;
    }


}
