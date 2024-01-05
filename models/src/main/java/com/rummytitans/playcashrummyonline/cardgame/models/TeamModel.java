package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TeamModel implements Serializable {
    @Expose
    @SerializedName("IsJoin")
    public boolean IsJoin;
    @Expose
    @SerializedName("ViceCaptainImage")
    public String ViceCaptainImage;
    @Expose
    @SerializedName("CaptainImage")
    public String CaptainImage;
    @Expose
    @SerializedName("ViceCaptainName")
    public String ViceCaptainName;
    @Expose
    @SerializedName("CaptainName")
    public String CaptainName;
    @Expose
    @SerializedName("TeamNo")
    public String TeamNo;
    @Expose
    @SerializedName("TeamID")
    public long TeamID;

    public boolean isSelected;
    @Expose
    @SerializedName("Team1Count")
    public int Team1Count;

    @Expose
    @SerializedName("Team2Count")
    public int Team2Count;


    @Expose
    @SerializedName("Team3Count")
    public int Team3Count;

    @SerializedName("Categories")
    public List<Category> mCategories;
    @SerializedName("TeamTotalPoints")
    public double TeamTotalPoints;
    @SerializedName("NonXIPlayers")
    public String NonXIPlayers;

    @SerializedName("SharingUrl")
    public String SharingUrl;
    @SerializedName("SharingMessage")
    public String SharingMessage;

    @SerializedName("Player4Image")
    public String Player4Image;

    @SerializedName("Player4Name")
    public String Player4Name;

    @SerializedName("Player3Image")
    public String Player3Image;

    @SerializedName("Player3Name")
    public String Player3Name;

    @SerializedName("Player2Image")
    public String Player2Image;

    @SerializedName("Player2Name")
    public String Player2Name;

    @SerializedName("Player1_5Image")
    public String Player1_5Image;

    @SerializedName("Player1_5Name")
    public String Player1_5Name;


    public class Category implements Serializable {

        @SerializedName("Cc")
        public Long mCc;

        @SerializedName("Cn")
        public String mCn;
    }

}
