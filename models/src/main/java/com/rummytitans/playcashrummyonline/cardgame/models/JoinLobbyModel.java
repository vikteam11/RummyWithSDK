package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JoinLobbyModel {

    @SerializedName("gameplayUrl")
    public String gameplayUrl;

    @SerializedName("matchFound")
    public boolean matchFound;

}
