package planet.com.helokings.VideoStreamingActivity.userlists.RoomMemberList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseRoomMember{

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("status")
	private String status;

	public String getMsg(){
		return msg;
	}

	public List<DataItem> getData(){
		return data;
	}

	public String getStatus(){
		return status;
	}
}