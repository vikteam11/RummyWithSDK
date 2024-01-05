package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerList {

    @Expose
    @SerializedName("ResponsePlayer")
    public ArrayList<ResponsePlayer> ResponsePlayer;
    @Expose
    @SerializedName("TeamBCt")
    public int TeamBCt;
    @Expose
    @SerializedName("TeamACt")
    public int TeamACt;
    @Expose
    @SerializedName("TeamBId")
    public int TeamBId;
    @Expose
    @SerializedName("TeamAId")
    public int TeamAId;
    @Expose
    @SerializedName("TeamB")
    public String TeamB;
    @Expose
    @SerializedName("TeamA")
    public String TeamA;
    @Expose
    @SerializedName("Message")
    public String Message;
    @Expose
    @SerializedName("CurrentDate")
    public String CurrentDate;
    @Expose
    @SerializedName("TokenExpire")
    public boolean TokenExpire;
    @Expose
    @SerializedName("Status")
    public boolean Status;

    public String Header;

    @SerializedName("Scrolling")
    public boolean Scrolling;
    @SerializedName("ScrollingMesage")
    public String ScrollingMesage;

    @SerializedName("PredictionIcon")
    public String PredictionIcon;

    @SerializedName("PredictionUrl")
    public String PredictionUrl;

    public class ResponsePlayer implements Serializable {

        //custom keys used to generate dynamic UI.
        public boolean isFirst;
        public boolean isLast;

        public int viewType;


        @Expose
        @SerializedName("SportPlayerRole")
        public String SportPlayerRole;

        @Expose
        @SerializedName("Image")
        public String Image;
        @Expose
        @SerializedName("PlayerRole")
        public String PlayerRole;
        @Expose
        @SerializedName("IsSelected")
        public boolean IsSelected;
        @Expose
        @SerializedName("IsXi")
        public boolean IsXi;

        public boolean IsNotXi;
        @Expose
        @SerializedName("IsInjured")
        public boolean IsInjured;
        @Expose
        @SerializedName("IsNewAddition")
        public boolean IsNewAddition;
        @Expose
        @SerializedName("LastIsXi")
        public boolean isLastPlaying;
        @Expose
        @SerializedName("Credits")
        public double Credits;
        @Expose
        @SerializedName("PlayerPoint")
        public double PlayerPoint;
        @Expose
        @SerializedName("Role")
        public String Role;

        @Expose
        @SerializedName("P4x")
        public String P4x;

        @Expose
        @SerializedName("P3x")
        public String P3x;
        @Expose
        @SerializedName("PlayerName")
        public String PlayerName;
        @Expose
        @SerializedName("TeamShortCode")
        public String TeamShortCode;
        @Expose
        @SerializedName("TeamID")
        public int TeamID;
        @Expose
        @SerializedName("PlayerId")
        public int PlayerId;
        @Expose
        @SerializedName("SelectedPrec")
        public double SelectedPrec;
        public String PlayerCatName;
        @Expose
        @SerializedName("Points")
        public double Points;
        @Expose
        @SerializedName("ShortName")
        public String ShortName;
        @Expose
        @SerializedName("LastFPoint")
        public String LastFPoint;
        @Expose
        @SerializedName("CategoryId")
        public int CategoryId;
        @Expose
        @SerializedName("TeamName")
        public String TeamName;
        @Expose
        @SerializedName("MessageText")
        public String MessageText;
        @Expose
        @SerializedName("ColorCode")
        public String ColorCode;

        public boolean IsFade = false;
        public int sortCVC = 0;

        @Expose
        @SerializedName("CPrec")
        public double CPrec;

        @Expose
        @SerializedName("VcPrec")
        public double VcPrec;


        public String CategoryName;
        public String CategoryShortName;
        public int localCategoryId;

        public ResponsePlayer() {
        }

        public int getsortCVC() {
            if (PlayerRole.equalsIgnoreCase("c"))
                return 1;
            else if (PlayerRole.equalsIgnoreCase("vc"))
                return 2;
            else return 3;
        }

        public double getPoints() {
            return PlayerPoint == 0.0 ? Points : PlayerPoint;
        }

        public String getShortName() {
            return ShortName == null || ShortName.isEmpty() ? PlayerName : ShortName;
        }

        public int getCategoryID() {
            return CategoryId;
        }

        public String getTeamName() {
            return TeamName == null || TeamName.isEmpty() ? TeamShortCode : TeamName;
        }

    }
}
