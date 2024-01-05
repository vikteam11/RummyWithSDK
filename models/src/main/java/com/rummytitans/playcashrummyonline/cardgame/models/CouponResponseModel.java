package com.rummytitans.playcashrummyonline.cardgame.models;

import java.util.List;
import java.io.Serializable;
import com.google.gson.annotations.SerializedName;

public class CouponResponseModel{

	@SerializedName("Buy")
	public List<CouponModel> buy;

	@SerializedName("Used")
	public List<CouponModel> used;

	@SerializedName("Purchased")
	public List<CouponModel> purchased;

	public int  setCurrentTab=0;

	public static class CouponModel implements Serializable {

		@SerializedName("Message")
		public String Message=null;

		@SerializedName("Validity")
		public int validity;

		@SerializedName("discoutPer")
		public String discoutPer;

		@SerializedName("Discount")
		public double discount;

		@SerializedName("TotalCoupans")
		public int totalCoupans;

		@SerializedName("TotalRedeem")
		public int totalRedeem;

		@SerializedName("IsActive")
		public boolean isActive;

		@SerializedName("Amount")
		public double amount;

		@SerializedName("ID")
		public int iD;

		@SerializedName("TotalAmount")
		public double totalAmount;

		@SerializedName("DiscountAmount")
		public double discountAmount;

		@SerializedName("URL")
		public String uRL;

		@SerializedName("ExpiryDate")
		public String ExpiryDate;

	}

}