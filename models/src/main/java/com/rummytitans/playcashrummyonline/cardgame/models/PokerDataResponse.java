package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class PokerDataResponse {

    @SerializedName("er")
    public String er;
    @SerializedName("d")
    public D d;
    @SerializedName("st")
    public int st;

    public static class D {
        @SerializedName("kv")
        public JsonObject kv;
        @SerializedName("p52_guid")
        public String p52_guid;
        @SerializedName("token")
        public String token;
    }
}
