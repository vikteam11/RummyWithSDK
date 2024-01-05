package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MatchInnings{
    @SerializedName("T_ID")
    public String t_ID;
    @SerializedName("Inning_Order")
    public String inning_Order;
    @SerializedName("T_Name")
    public String t_Name;
    @SerializedName("T_Sname")
    public String t_Sname;
    @SerializedName("Scr")
    public String scr;
    @SerializedName("Fscr")
    public String fscr;
    @SerializedName("Ovr")
    public String ovr;
    @SerializedName("Runs")
    public String runs;
    @SerializedName("Wkt")
    public String wkt;
    @SerializedName("Runrate")
    public String runrate;
    @SerializedName("Extra_Runs")
    public ExtraRuns extra_Runs;
    @SerializedName("Batsmen")
    public List<BattingsItem> BattingsItem;
    @SerializedName("Bowlers")
    public List<BowlingsItem> BowlingsItem;
    @SerializedName("FOWS")
    public List<FallofwicketsItem> mFallofwickets;
    @SerializedName("Did_Not_Bat")
    public List<DidNotBat> did_Not_Bat;
    @SerializedName("APIHitTime")
    public int APIHitTime;

    static public class ExtraRuns{
        @SerializedName("Byes")
        public int byes;
        @SerializedName("Legbyes")
        public int legbyes;
        @SerializedName("Wides")
        public int wides;
        @SerializedName("Noballs")
        public int noballs;
        @SerializedName("Penalty")
        public int penalty;
        @SerializedName("Total")
        public int total;
    }

    static public class BattingsItem{
        @SerializedName("Name")
        public String name;
        @SerializedName("Pid")
        public String pid;
        @SerializedName("Role")
        public String role;
        @SerializedName("Runs")
        public String runs;
        @SerializedName("Balls_Faced")
        public String balls_Faced;
        @SerializedName("Fours")
        public String fours;
        @SerializedName("Sixes")
        public String sixes;
        @SerializedName("How_Out")
        public String how_Out;
        @SerializedName("Strike_Rate")
        public String strike_Rate;
        @SerializedName("Strike")
        public boolean strike;
        @SerializedName("Non_Strike")
        public boolean non_Strike;
    }

    static public class BowlingsItem{
        @SerializedName("Name")
        public String name;
        @SerializedName("Pid")
        public String pid;
        @SerializedName("Overs")
        public double overs;
        @SerializedName("Maidens")
        public String maidens;
        @SerializedName("Runs")
        public String runs;
        @SerializedName("Wickets")
        public String wickets;
        @SerializedName("Noballs")
        public String noballs;
        @SerializedName("Wides")
        public String wides;
        @SerializedName("Econ")
        public double econ;
        @SerializedName("Strike")
        public boolean strike;
        @SerializedName("Non_Strike")
        public boolean non_Strike;
    }

    static public class FallofwicketsItem{
        @SerializedName("Name")
        public String name;
        @SerializedName("Scr")
        public String scr;
        @SerializedName("Ovr")
        public double ovr;
        @SerializedName("Number")
        public String number;
    }

    static public class DidNotBat{
        @SerializedName("Name")
        public String name;
    }
}



