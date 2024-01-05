package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SeriesRankModel implements Serializable {

    @SerializedName("leaderBoardSeries")
    public ArrayList<LeaderBoardSeries> leaderBoardSeries;
    @SerializedName("leaderBoardData")
    public LeaderBoardData leaderBoardData;

    public static class LeaderBoardSeries implements Serializable {
        @SerializedName("Description")
        public String Description;
        @SerializedName("Rank")
        public int Rank;
        @SerializedName("Title")
        public String Title;
        @SerializedName("Id")
        public int Id;
    }

    public static class LeaderBoardData implements Serializable {
        @SerializedName("Series")
        public int Series;
        @SerializedName("TotalMatch")
        public int TotalMatch;
        @SerializedName("TotalLeague")
        public int TotalLeague;
        @SerializedName("LeagueWon")
        public int LeagueWon;
    }
}
