package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JuspayResponse implements Serializable {


    @SerializedName("amount")
    public int amount;
    @SerializedName("amount_refunded")
    public int amount_refunded;
    @SerializedName("currency")
    public String currency;
    @SerializedName("customer_email")
    public String customer_email;
    @SerializedName("customer_id")
    public String customer_id;
    @SerializedName("customer_phone")
    public String customer_phone;
    @SerializedName("date_created")
    public String date_created;
    @SerializedName("id")
    public String id;
    @SerializedName("juspay")
    public Juspay juspay;
    @SerializedName("merchant_id")
    public String merchant_id;
    @SerializedName("order_id")
    public String order_id;
    @SerializedName("payment_links")
    public Payment_links payment_links;
    @SerializedName("product_id")
    public String product_id;
    @SerializedName("refunded")
    public boolean refunded;
    @SerializedName("return_url")
    public String return_url;
    @SerializedName("status")
    public String status;
    @SerializedName("status_id")
    public int status_id;
    @SerializedName("udf1")
    public String udf1;
    @SerializedName("udf10")
    public String udf10;
    @SerializedName("udf2")
    public String udf2;
    @SerializedName("udf3")
    public String udf3;
    @SerializedName("udf4")
    public String udf4;
    @SerializedName("udf5")
    public String udf5;
    @SerializedName("udf6")
    public String udf6;
    @SerializedName("udf7")
    public String udf7;
    @SerializedName("udf8")
    public String udf8;
    @SerializedName("udf9")
    public String udf9;

    public static class Juspay implements Serializable {
        @SerializedName("client_auth_token")
        public String client_auth_token;
        @SerializedName("client_auth_token_expiry")
        public String client_auth_token_expiry;
    }

    public static class Payment_links implements Serializable {
        @SerializedName("iframe")
        public String iframe;
        @SerializedName("mobile")
        public String mobile;
        @SerializedName("web")
        public String web;
    }
}
