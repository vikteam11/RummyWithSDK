package com.rummytitans.playcashrummyonline.cardgame.models;

import com.google.gson.annotations.SerializedName;

public class PinpointNotificationModel{

	@SerializedName("MessageConfiguration")
	public MessageConfiguration messageConfiguration;

	public static class MessageConfiguration{

		@SerializedName("GCMMessage")
		public GCMMessage gCMMessage;

		public static class GCMMessage{

			@SerializedName("Title")
			public String title;

			@SerializedName("SilentPush")
			public boolean silentPush;

			@SerializedName("Body")
			public String body;
			@SerializedName("deeplink")
			public String deeplink;
			@SerializedName("imageUrl")
			public String imageUrl;
		}
	}
}