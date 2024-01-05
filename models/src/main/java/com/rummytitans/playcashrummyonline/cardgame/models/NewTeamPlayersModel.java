
package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class NewTeamPlayersModel {

    @SerializedName("GroundImage")
    public String GroundImage;
    @SerializedName("ImageShow")
    public Boolean ImageShow;
    @SerializedName("playerList")
    public List<PlayerList.ResponsePlayer> PlayerList;
    @SerializedName("AllPlayers")
    public List<PlayerList.ResponsePlayer> AllPlayers;
    @SerializedName("PreviewRowCount")
    public int PreviewRowCount;
    @SerializedName("Team1Id")
    public int Team1Id;
    @SerializedName("Team2Id")
    public int Team2Id;

}
