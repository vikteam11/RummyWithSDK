package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocaleModel implements Serializable {
    @SerializedName("TitleEnglish")
    public String englishTitle;
    @SerializedName("Title")
    public String languageTitle;
    @SerializedName("LanguageCode")
    public String languageCode;
    @SerializedName("Message")
    public String Message;
    @SerializedName("IsIssue")
    public boolean IsIssue;
    public boolean isSelected;
}
