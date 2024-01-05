package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamPreviewModel {

    @Expose
    @SerializedName("Ext")
    public List<PlayerList.ResponsePlayer> Ext;
    @Expose
    @SerializedName("Bol")
    public List<PlayerList.ResponsePlayer> Bol;
    @Expose
    @SerializedName("All")
    public List<PlayerList.ResponsePlayer> All;
    @Expose
    @SerializedName("Bat")
    public List<PlayerList.ResponsePlayer> Bat;
    @Expose
    @SerializedName("Wk")
    public List<PlayerList.ResponsePlayer> Wk;
    @Expose
    @SerializedName("ImageShow")
    public boolean ImageShow;
    @Expose
    @SerializedName("Team2Id")
    public int Team2Id;
    @Expose
    @SerializedName("Team1Id")
    public int Team1Id;
    @Expose
    @SerializedName("GroundImage")
    public String GroundImage;

    @Expose
    @SerializedName("AllPlayers")
    public List<PlayerList.ResponsePlayer> AllPlayers;

    public static class PlayerPreview {
        @Expose
        @SerializedName("Image")
        public String Image;
        @Expose
        @SerializedName("CategoryID")
        public int CategoryID;
        @Expose
        @SerializedName("TeamName")
        public String TeamName;
        @Expose
        @SerializedName("TeamID")
        public int TeamID;
        @Expose
        @SerializedName("Points")
        public double Points;
        @Expose
        @SerializedName("PlayerRole")
        public String PlayerRole;
        @Expose
        @SerializedName("ShortName")
        public String ShortName;
    }
}
