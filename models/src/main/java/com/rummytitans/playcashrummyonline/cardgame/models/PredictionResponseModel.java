package com.rummytitans.playcashrummyonline.cardgame.models;

import java.io.Serializable;
import java.util.List;

public class PredictionResponseModel implements Serializable {
    public int MatchId;
    public String Report;
    public String Title;
    public String Location;
    public String Capacity;
    public String Image;
    public String RedirectionUrl;
    public List<TopPlayers> TopPlayers;
    public MatchDetailModel Test;
    public MatchDetailModel ODI;
    public MatchDetailModel T20;

    public static class TopPlayers implements Serializable {
        public String Name;
        public String Role;
        public double SelectionPer;
    }

    public static class MatchDetailModel implements Serializable {
        public int TotalMatch = 0;
        public int WonBatting = 0;
        public int WonBowling = 0;
        public int Avg1InningScore = 0;
        public int Avg2InningScore = 0;
        public int Avg3InningScore = 0;
        public int Avg4InningScore = 0;
    }
}