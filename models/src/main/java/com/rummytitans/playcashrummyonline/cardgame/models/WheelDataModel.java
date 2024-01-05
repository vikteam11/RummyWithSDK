package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class WheelDataModel implements Serializable {

    @SerializedName("ShareMessage")
    public String shareMessage;

    @SerializedName("Message")
    public String message;

    @SerializedName("NumberAmount")
    public String NumberAmount;

    @SerializedName("Amount")
    public double amount;

    @SerializedName("IsUsed")
    public boolean isUsed;

    @SerializedName("IsFaceBook")
    public boolean IsFaceBook;

    @SerializedName("Istwitter")
    public boolean Istwitter;

    @SerializedName("PopUpImgae")
    public String PopUpImgae;

    @SerializedName("Wheeltext")
    public String Wheeltext;

    @SerializedName("ImageURL")
    public String imageURL;

    @SerializedName("ID")
    public int iD;
}