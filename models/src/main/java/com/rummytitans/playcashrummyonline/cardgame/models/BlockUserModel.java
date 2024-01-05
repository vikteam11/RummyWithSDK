package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class BlockUserModel implements Serializable {
    @SerializedName("Header")
    public String Header;

    @SerializedName("HeaderDetail")
    public String HeaderDetail;

    @SerializedName("Title")
    public String Title;

    @SerializedName("SupportText")
    public String SupportText;

    @SerializedName("HelpUrl")
    public String HelpUrl;

    @SerializedName("Reasons")
    public ArrayList<String> Reasons;
}
