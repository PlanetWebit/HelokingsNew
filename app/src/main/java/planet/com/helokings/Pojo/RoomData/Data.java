package planet.com.helokings.Pojo.RoomData;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("room_id")
	private String roomId;

	@SerializedName("room_name")
	private String roomName;

	@SerializedName("image")
	private String image;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("online_status")
	private int onlineStatus;

	@SerializedName("room_tag")
	private String roomTag;

	@SerializedName("review_status")
	private String reviewStatus;

	@SerializedName("room_desc")
	private String roomDesc;

	@SerializedName("coin")
	private String coin;

	@SerializedName("status")
	private int status;

	public String getRoomId(){
		return roomId;
	}

	public String getRoomName(){
		return roomName;
	}

	public String getImage(){
		return image;
	}

	public String getUserId(){
		return userId;
	}

	public int getOnlineStatus(){
		return onlineStatus;
	}

	public String getRoomTag(){
		return roomTag;
	}

	public String getReviewStatus(){
		return reviewStatus;
	}

	public String getRoomDesc(){
		return roomDesc;
	}

	public String getCoin(){
		return coin;
	}

	public int getStatus(){
		return status;
	}
}