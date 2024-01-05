
package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ScrachCardListModel implements Serializable {

        @SerializedName("Amount")
        public Double mAmount;
        @SerializedName("DateAdded")
        public String mDateAdded;
        @SerializedName("FlipMeaage")
        public String mFlipMeaage;
        @SerializedName("Id")
        public Long mId;
        @SerializedName("IsRedem")
        public Boolean mIsRedem;
        @SerializedName("Message")
        public String mMessage;
        @SerializedName("TypeMessage")
        public String mTypeMessage;

        //use for card flip
        public Boolean mIsFrontOpen=true;
}
