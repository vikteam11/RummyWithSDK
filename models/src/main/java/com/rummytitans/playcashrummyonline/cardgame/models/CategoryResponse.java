package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryResponse {


    @Expose
    @SerializedName("Response")
    public List<Response> Response;
    @Expose
    @SerializedName("JoinCount")
    public int JoinCount;
    @Expose
    @SerializedName("TeamCount")
    public int TeamCount;
    @Expose
    @SerializedName("Message")
    public String Message;
    @Expose
    @SerializedName("TokenExpire")
    public boolean TokenExpire;
    @Expose
    @SerializedName("Status")
    public boolean Status;
    @Expose
    @SerializedName("CurrentDate")
    public String CurrentDate;

    @SerializedName("Information")
    public Information Information;
    @SerializedName("Maxteam")
    public int Maxteam;
    @Expose
    @SerializedName("DefaultCatID")
    public int DefaultCatID;
    @Expose
    @SerializedName("IsTimesUp")
    public boolean IsTimesUp;



    public class Information implements Serializable {
        @SerializedName("InApp")
        public boolean InApp;
        @SerializedName("ImageUrl")
        public String ImageUrl;
        @SerializedName("Url")
        public String Url;
        @SerializedName("secInApp")
        public boolean secInApp;
        @SerializedName("secImageUrl")
        public String secImageUrl;
        @SerializedName("secUrl")
        public String secUrl;
    }


    public static class Response implements Serializable {
        @Expose
        @SerializedName("LeaugeData")
        public List<LeagueData> LeaugeData;
        @Expose
        @SerializedName("LeaugeCount")
        public int LeaugeCount;
        @Expose
        @SerializedName("Desc")
        public String Desc;
        @Expose
        @SerializedName("Title")
        public String Title;
        @Expose
        @SerializedName("Image")
        public String Image;
        @Expose
        @SerializedName("Id")
        public int Id;

        @Expose
        @SerializedName("IsInfo")
        public int IsInfo;

        @Expose
        @SerializedName("IsInfoUrl")
        public String IsInfoUrl;
        @Expose
        @SerializedName("ShortName")
        public String ShortName;

    }

}
