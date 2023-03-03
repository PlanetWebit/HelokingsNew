package planet.com.helokings.Pojo.HomeData;

import com.google.gson.annotations.SerializedName;

public class BannerItem{

	@SerializedName("image")
	private String image;

	@SerializedName("id")
	private String id;

	@SerializedName("url")
	private String url;

	public String getImage(){
		return image;
	}

	public String getId(){
		return id;
	}

	public String getUrl(){
		return url;
	}
}