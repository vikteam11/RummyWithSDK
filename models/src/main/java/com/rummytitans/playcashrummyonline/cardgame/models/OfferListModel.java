
package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class OfferListModel {

    @SerializedName("CurrentDate")
    private String mCurrentDate;
    @SerializedName("IsAutoScrollHeader")
    private Boolean mIsAutoScrollHeader;
    @SerializedName("Maxteam")
    private Long mMaxteam;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("NotificationKey")
    private String mNotificationKey;
    @SerializedName("PollKey")
    private String mPollKey;
    @SerializedName("Response")
    private List<PollResponseModel> mOffersModel;
    @SerializedName("Status")
    private Boolean mStatus;
    @SerializedName("TokenExpire")
    private Boolean mTokenExpire;

    public String getCurrentDate() {
        return mCurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        mCurrentDate = currentDate;
    }

    public Boolean getIsAutoScrollHeader() {
        return mIsAutoScrollHeader;
    }

    public void setIsAutoScrollHeader(Boolean isAutoScrollHeader) {
        mIsAutoScrollHeader = isAutoScrollHeader;
    }

    public Long getMaxteam() {
        return mMaxteam;
    }

    public void setMaxteam(Long maxteam) {
        mMaxteam = maxteam;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getNotificationKey() {
        return mNotificationKey;
    }

    public void setNotificationKey(String notificationKey) {
        mNotificationKey = notificationKey;
    }

    public String getPollKey() {
        return mPollKey;
    }

    public void setPollKey(String pollKey) {
        mPollKey = pollKey;
    }

    public List<PollResponseModel> getResponse() {
        return mOffersModel;
    }

    public void setResponse(List<PollResponseModel> offersModel) {
        mOffersModel = offersModel;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

    public Boolean getTokenExpire() {
        return mTokenExpire;
    }

    public void setTokenExpire(Boolean tokenExpire) {
        mTokenExpire = tokenExpire;
    }

}
