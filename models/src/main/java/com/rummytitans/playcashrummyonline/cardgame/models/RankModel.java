package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RankModel {
    @SerializedName("seriesDetail")
    public ArrayList<SeriesDetail> seriesDetail;
    @SerializedName("seriesSingle")
    public SeriesSingle seriesSingle;

    public static class SeriesDetail {
        @SerializedName("UserId")
        public int UserId;
        @SerializedName("AvtaarID")
        public int AvtaarID;
        @SerializedName("TeamName")
        public String TeamName;
        @SerializedName("Rank")
        public int Rank;
        @SerializedName("Points")
        public double Points;
        //defined addition to check whether details is logged in user's
        public boolean isMyTeam;
        public int avatarId;
    }

    public static class SeriesSingle {
        @SerializedName("ButtonURL")
        public String ButtonURL;
        @SerializedName("ButtonTitle")
        public String ButtonTitle;
        @SerializedName("Description")
        public String Description;
        @SerializedName("SeriesTitle")
        public String SeriesTitle;
        @SerializedName("SeriesID")
        public int SeriesID;
    }

}
