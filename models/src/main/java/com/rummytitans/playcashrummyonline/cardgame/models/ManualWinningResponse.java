package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ManualWinningResponse implements Serializable {

    @SerializedName("privateRank")
    public ArrayList<PrivateRank> privateRank;
    @SerializedName("WinnerRank")
    public String WinnerRank;
    @SerializedName("Id")
    public int Id;

    public static class PrivateRank {
        @SerializedName("Amount")
        public int Amount;
        @SerializedName("Member")
        public int Member;
        @SerializedName("Rank")
        public String Rank;
    }
}
