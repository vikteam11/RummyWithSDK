
package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RedeemScratchCardModel {

    @SerializedName("CurrentDate")
    public Object mCurrentDate;
    @SerializedName("IsAutoScrollHeader")
    public Boolean mIsAutoScrollHeader;
    @SerializedName("JusPay")
    public Boolean mJusPay;
    @SerializedName("Message")
    public String mMessage;
    @SerializedName("NotificationKey")
    public Object mNotificationKey;
    @SerializedName("PollKey")
    public Object mPollKey;
    @SerializedName("Response")
    public String mResponse;
    @SerializedName("Status")
    public Boolean mStatus;
    @SerializedName("TokenExpire")
    public Boolean mTokenExpire;

}
