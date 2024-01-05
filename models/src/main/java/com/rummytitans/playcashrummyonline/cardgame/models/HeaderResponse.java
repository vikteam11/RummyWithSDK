package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HeaderResponse implements Serializable {
    @Expose
    @SerializedName("Meta")
    public String deeplink;
    @Expose
    @SerializedName("Type")
    public String Type;
    @Expose
    @SerializedName("Image")
    public String Image;
}
