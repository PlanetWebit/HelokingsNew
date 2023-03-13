package planet.com.helokings.Pojo.MyRooms;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseRoomdata{

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