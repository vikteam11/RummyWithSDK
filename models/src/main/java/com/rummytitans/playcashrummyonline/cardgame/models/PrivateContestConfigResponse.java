package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

public class PrivateContestConfigResponse {
    @SerializedName("MamberMin")
    public int MamberMin;
    @SerializedName("MamberMax")
    public int MamberMax;
    @SerializedName("AmountMin")
    public int AmountMin;
    @SerializedName("AmountMax")
    public int AmountMax;
    @SerializedName("Message")
    public String Message;
    @SerializedName("ConfirmContests")
    public String ConfirmContests;
}
