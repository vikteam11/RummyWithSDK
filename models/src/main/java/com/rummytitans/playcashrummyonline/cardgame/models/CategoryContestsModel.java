package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryContestsModel implements Serializable {

    @SerializedName("Response")
    public ArrayList<LeagueData> Response;
    @SerializedName("JoinCount")
    public int JoinCount;
    @SerializedName("TeamCount")
    public int TeamCount;
    @SerializedName("Message")
    public String Message;
    @SerializedName("TokenExpire")
    public boolean TokenExpire;
    @SerializedName("Status")
    public boolean Status;
    @SerializedName("CurrentDate")
    public String CurrentDate;
    @SerializedName("MessageQuiz")
    public String MessageQuiz;
    @Expose
    @SerializedName("IsTimesUp")
    public boolean IsTimesUp;

    @SerializedName("Information")
    public CategoryResponse.Information Information;

}
