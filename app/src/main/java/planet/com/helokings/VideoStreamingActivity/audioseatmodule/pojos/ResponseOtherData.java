package planet.com.helokings.VideoStreamingActivity.audioseatmodule.pojos;

import com.google.gson.annotations.SerializedName;

public class ResponseOtherData{

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private Data data;

	@SerializedName("PrivacySetting")
	private PrivacySetting privacySetting;

	@SerializedName("PushNotification")
	private PushNotification pushNotification;

	@SerializedName("status")
	private String status;

	public String getMsg(){
		return msg;
	}

	public Data getData(){
		return data;
	}

	public PrivacySetting getPrivacySetting(){
		return privacySetting;
	}

	public PushNotification getPushNotification(){
		return pushNotification;
	}

	public String getStatus(){
		return status;
	}
}