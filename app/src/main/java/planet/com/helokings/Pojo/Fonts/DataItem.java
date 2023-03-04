package planet.com.helokings.Pojo.Fonts;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("image")
	private String image;

	@SerializedName("amount")
	private String amount;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private String type;

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

	public String getTitle(){
		return title;
	}

	public String getType(){
		return type;
	}

	public String getTotalDays(){
		return totalDays;
	}
}