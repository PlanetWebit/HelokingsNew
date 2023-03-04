package planet.com.helokings.Pojo.Frames;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("image")
	private String image;

	@SerializedName("amount")
	private String amount;

	@SerializedName("id")
	private String id;

	@SerializedName("total_days")
	private String totalDays;

	public String getImage(){
		return image;
	}

	public String getAmount(){
		return amount;
	}

	public String getId(){
		return id;
	}

	public String getTotalDays(){
		return totalDays;
	}
}