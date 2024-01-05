package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MatchListResponse {

    @SerializedName("Completed")
    public List<MatchModel> Completed = new ArrayList<>();
    @SerializedName("Started")
    public List<MatchModel> Started = new ArrayList<>();
    @SerializedName("NotStarted")
    public List<MatchModel> NotStarted = new ArrayList<>();

}
