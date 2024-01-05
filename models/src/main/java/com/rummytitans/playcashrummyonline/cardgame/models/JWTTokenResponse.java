package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JWTTokenResponse {
    @SerializedName("Status")
    public boolean Status;
    @SerializedName("Data")
    public JWTData Data;


    public static class JWTData implements Serializable {
        @SerializedName("Result")
        public PokerData Result;
    }

    public static class PokerData implements Serializable {
        @SerializedName("kv")
        public JsonObject kv;
        @SerializedName("Token")
        public String Token;
    }
}
