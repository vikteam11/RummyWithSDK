package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatResponse {

    @SerializedName("index")
    public int index = 0;

    @SerializedName("messages")
    public ArrayList<MessageModel> messages;

    public static class MessageModel {

        @SerializedName("message")
        public String message;
        @SerializedName("leagueId")
        public long leagueId;
        @SerializedName("userId")
        public long userId;
        @SerializedName("teamName")
        public String teamName;
        @SerializedName("messageDate")
        public String messageDate;
        @SerializedName("messageTime")
        public String messageTime;
        @SerializedName("timeSpan")
        public long timeSpan;
    }

    public  static class MessageCountModel{
        @SerializedName("messageCount")
        public int messageCount;
    }
}