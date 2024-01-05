package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompareTeamModel {

    @SerializedName("TeamCampareTop")
    public TeamCampareTop TeamCampareTop;

    @SerializedName("CaptionVc")
    public List<CompareTeamMainModel> CaptionVc;

    @SerializedName("MatchPlayer")
    public List<CompareTeamMainModel> MatchPlayer;

    @SerializedName("DiffPlayer")
    public List<CompareTeamMainModel> DiffPlayer;

    @SerializedName("MessageTop")
    public String MessageTop;

    @SerializedName("MessageCvc")
    public String MessageCvc;

    @SerializedName("MessageDiff")
    public String MessageDiff;

    public static class TeamCampareTop {
        @SerializedName("TeamA2")
        public TopTeam TeamA2;
        @SerializedName("TeamA1")
        public TopTeam TeamA1;
    }

    public static class TopTeam {
        @SerializedName("Point")
        public double Point;
        @SerializedName("Rank")
        public int Rank;
        @SerializedName("TeamName")
        public String TeamName;
        @SerializedName("AvtarId")
        public int AvtarId;
    }

    public static class CompareTeamMainModel {
        @SerializedName("TeamA1")
        public CompareTeamPlayerModel TeamA1;
        @SerializedName("TeamA2")
        public CompareTeamPlayerModel TeamA2;
    }

    public static class CompareTeamPlayerModel {
        @SerializedName("Point")
        public Double Point;
        @SerializedName("CateGory")
        public int CateGory;
        @SerializedName("CategoryRole")
        public String CategoryRole;
        @SerializedName("Role")
        public String Role;
        @SerializedName("TeamName")
        public String TeamName;
        @SerializedName("Name")
        public String Name;
        @SerializedName("Image")
        public String Image;
    }

}
