package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FavroiteTeamListModel {

    @SerializedName("FavoriteTeamList")
    public ArrayList<FavoriteTeamList> FavoriteTeamList;
    @SerializedName("SelectedTeamId")
    public Long SelectedTeamId;
    @SerializedName("TourId")
    public int TourId;
    @SerializedName("Desc")
    public String Desc;
    @SerializedName("Title")
    public String Title;
    @SerializedName("Note")
    public String Note;

    public static class FavoriteTeamList {
        @SerializedName("Image")
        public String Image;
        @SerializedName("Name")
        public String Name ;
        @SerializedName("Id")
        public Long Id;
        public boolean isSelected;
    }
}
