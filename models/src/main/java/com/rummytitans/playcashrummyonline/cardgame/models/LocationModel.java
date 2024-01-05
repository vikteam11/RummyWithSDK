package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocationModel{
	public String StateName;
	public Integer Minutes;
	@SerializedName("CatGameList")
	public ArrayList<GamesResponseModel> games;
}
