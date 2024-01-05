package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CreateTeamSettingsModel implements Serializable {

    @SerializedName("Categories")
    public List<Categories> Categories;
    @SerializedName("MaxPlayerOneTeamError")
    public String MaxPlayerOneTeamError;
    @SerializedName("MaxPlayerErrorMsg")
    public String MaxPlayerErrorMsg;
    @SerializedName("SportsID")
    public int SportsID;
    @SerializedName("TotalCredit")
    public double TotalCredit;
    @SerializedName("MinFromOneTeam")
    public int MinFromOneTeam;
    @SerializedName("MaxPlayer")
    public int MaxPlayer;
    @SerializedName("PreviewRowCount")
    public int PreviewRowCount;
    @SerializedName("GroundImage")
    public String GroundImage;

    public int customsportsId=0;


    public static class Categories implements Serializable {
        @SerializedName("MaxSelectError")
        public String MaxSelectError;
        @SerializedName("MinSelectError")
        public String MinSelectError;
        @SerializedName("ImageURL")
        public String ImageURL;
        @SerializedName("MinPlayerCount")
        public int MinPlayerCount;
        @SerializedName("MaxPlayerCount")
        public int MaxPlayerCount;
        @SerializedName("Description")
        public String Description;
        @SerializedName("ShortName")
        public String ShortName;
        @SerializedName("Name")
        public String Name;
        @SerializedName("Role")
        public String Role;
        @SerializedName("ID")
        public int ID;
    }
}
