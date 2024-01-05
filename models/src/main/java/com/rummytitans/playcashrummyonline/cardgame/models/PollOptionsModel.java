package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

public class PollOptionsModel {
    @SerializedName("TotalVotes")
    public String TotalVotes;
    @SerializedName("Colour")
    public String Colour;
    @SerializedName("Name")
    public String Name;
    @SerializedName("Id")
    public int Id;
    @SerializedName("IsCasted")
    public boolean isSelected = false;
}
