package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationListModel implements Serializable {

    @SerializedName("Image")
    public String Image;
    @SerializedName("Type2")
    public String Type2;
    @SerializedName("Type1")
    public int Type1;
    @SerializedName("TnCUrl")
    public String TnCUrl;
    @SerializedName("StartDate")
    public String StartDate;
    @SerializedName("Description")
    public String Description;
    @SerializedName("Category")
    public String Category;

    @SerializedName("Id")
    public String id;
    @SerializedName("Title")
    public String Title;
    @SerializedName("DeepLink")
    public String DeepLink;
    @SerializedName("Icon")
    public String Icon;
    @SerializedName("AddedDate")
    public String AddedDate;
    @SerializedName("AddedDateTs")
    public long AddedDateTs;
    public boolean isNewNotification =true;
}
