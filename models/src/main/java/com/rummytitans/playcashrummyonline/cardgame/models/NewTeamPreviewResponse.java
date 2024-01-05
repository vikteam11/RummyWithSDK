package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewTeamPreviewResponse implements Serializable {

    @SerializedName("ImageShow")
    public boolean ImageShow;
    @SerializedName("Team2Id")
    public long Team2Id;
    @SerializedName("PreviewRowCount")
    public int PreviewRowCount;
    @SerializedName("GroundImage")
    public String GroundImage;
    @SerializedName("Team1Id")
    public long Team1Id;
    @SerializedName("SharingUrl")
    public String SharingUrl;
    @SerializedName("SharingMessage")
    public String SharingMessage;

    @SerializedName("Wk")
    public List<PlayerList.ResponsePlayer> Wk;

    @SerializedName("Bat")
    public List<PlayerList.ResponsePlayer> Bat;

    @SerializedName("All")
    public List<PlayerList.ResponsePlayer> All;

    @SerializedName("Bol")
    public List<PlayerList.ResponsePlayer> Bol;

    @SerializedName("Ext")
    public List<PlayerList.ResponsePlayer> Ext;
  
    @SerializedName("ExtNew")
    public List<PlayerList.ResponsePlayer> ExtNew;

    @SerializedName("ExtOne")
    public List<PlayerList.ResponsePlayer> ExtOne;

    @SerializedName("ExtTwo")
    public List<PlayerList.ResponsePlayer> ExtTwo;

}
