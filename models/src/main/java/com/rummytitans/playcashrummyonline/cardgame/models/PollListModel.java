package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PollListModel {


    @SerializedName("ListOption")
    public List<ListOption> ListOption;
    @SerializedName("Type1Value")
    public String Type1Value;
    @SerializedName("Type1")
    public String Type1;
    @SerializedName("PoleType")
    public int PoleType;
    @SerializedName("Title")
    public String Title;
    @SerializedName("Image")
    public String Image;
    @SerializedName("ID")
    public int ID;
    public String Answer;
    public boolean answerSubmitted;

    public static class ListOption {
        @SerializedName("IsCasted")
        public boolean IsCasted;
        @SerializedName("TotalVotes")
        public String TotalVotes;
        @SerializedName("Colour")
        public String Colour;
        @SerializedName("Name")
        public String Name;
        @SerializedName("Id")
        public int Id;
    }
}
