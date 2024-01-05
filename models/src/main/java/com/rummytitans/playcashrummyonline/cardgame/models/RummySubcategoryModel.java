package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RummySubcategoryModel {

    @Expose
    @SerializedName("SubCategoryId")
    public String SubCategoryId;

    @Expose
    @SerializedName("Selected")
    public boolean Selected;

    @SerializedName("CardVariant")
    public int variant = 13;

}
