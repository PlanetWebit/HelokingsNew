package planet.com.helokings.Pojo.RoomData;

import com.google.gson.annotations.SerializedName;

public class ResponseRoom{

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private Data data;

	@SerializedName("status")
	private String status;

	public String getMsg(){
		return msg;
	}

	public Data getData(){
		return data;
	}

	public String getStatus(){
		return status;
	}
}