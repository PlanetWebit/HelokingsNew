package planet.com.helokings.Pojo.HomeData;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseHomedata{

	@SerializedName("msg")
	private String msg;

	@SerializedName("follower_live_count")
	private int followerLiveCount;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("banner")
	private List<BannerItem> banner;

	@SerializedName("status")
	private String status;

	public String getMsg(){
		return msg;
	}

	public int getFollowerLiveCount(){
		return followerLiveCount;
	}

	public List<DataItem> getData(){
		return data;
	}

	public List<BannerItem> getBanner(){
		return banner;
	}

	public String getStatus(){
		return status;
	}
}