package planet.com.helokings.Pojo.HomeData;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("country")
	private String country;

	@SerializedName("room_tag")
	private String roomTag;

	@SerializedName("bio")
	private String bio;


	@SerializedName("room_type")
	private String room_type;

	@SerializedName("password")
	private String password;

	@SerializedName("profile_pic_small")
	private String profilePicSmall;

	@SerializedName("id")
	private String id;

	@SerializedName("state_id")
	private String stateId;

	@SerializedName("lat")
	private String lat;

	@SerializedName("image")
	private String image;

	@SerializedName("created")
	private String created;

	@SerializedName("ip")
	private String ip;

	@SerializedName("reset_wallet_datetime")
	private String resetWalletDatetime;

	@SerializedName("active")
	private String active;

	@SerializedName("room_desc")
	private String roomDesc;

	@SerializedName("room_coin")
	private String roomCoin;

	@SerializedName("version")
	private String version;

	@SerializedName("room_name")
	private String roomName;

	@SerializedName("social_id")
	private String socialId;

	@SerializedName("phone")
	private String phone;

	@SerializedName("fb_id")
	private String fbId;

	@SerializedName("following_count")
	private int followingCount;

	@SerializedName("dob")
	private String dob;

	@SerializedName("nick_name")
	private String nickName;

	@SerializedName("PushNotification")
	private PushNotification pushNotification;

	@SerializedName("verification_code")
	private String verificationCode;

	@SerializedName("device")
	private String device;

	@SerializedName("country_id")
	private String countryId;

	@SerializedName("city_id")
	private String cityId;

	@SerializedName("room_id")
	private String roomId;

	@SerializedName("role")
	private String role;

	@SerializedName("gender")
	private String gender;

	@SerializedName("city")
	private String city;

	@SerializedName("long")
	private String jsonMemberLong;

	@SerializedName("room_image")
	private String roomImage;

	@SerializedName("review_status")
	private String reviewStatus;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("paypal")
	private String paypal;

	@SerializedName("email")
	private String email;

	@SerializedName("website")
	private String website;

	@SerializedName("wallet")
	private String wallet;

	@SerializedName("social")
	private String social;

	@SerializedName("profile_pic")
	private String profilePic;

	@SerializedName("verified")
	private String verified;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("session_id")
	private String sessionId;

	@SerializedName("profile_complete")
	private String profileComplete;

	@SerializedName("token")
	private String token;

	@SerializedName("likes_count")
	private int likesCount;

	@SerializedName("device_token")
	private String deviceToken;

	@SerializedName("followers_count")
	private int followersCount;

	@SerializedName("PrivacySetting")
	private PrivacySetting privacySetting;

	@SerializedName("video_count")
	private int videoCount;

	@SerializedName("online")
	private String online;

	@SerializedName("auth_token")
	private String authToken;

	@SerializedName("username")
	private String username;

	@SerializedName("refer_code")
	private String referCode;

	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

	public String getCountry(){
		return country;
	}

	public String getRoomTag(){
		return roomTag;
	}

	public String getBio(){
		return bio;
	}

	public String getPassword(){
		return password;
	}

	public String getProfilePicSmall(){
		return profilePicSmall;
	}

	public String getId(){
		return id;
	}

	public String getStateId(){
		return stateId;
	}

	public String getLat(){
		return lat;
	}

	public String getImage(){
		return image;
	}

	public String getCreated(){
		return created;
	}

	public String getIp(){
		return ip;
	}

	public String getResetWalletDatetime(){
		return resetWalletDatetime;
	}

	public String getActive(){
		return active;
	}

	public String getRoomDesc(){
		return roomDesc;
	}

	public String getRoomCoin(){
		return roomCoin;
	}

	public String getVersion(){
		return version;
	}

	public String getRoomName(){
		return roomName;
	}

	public String getSocialId(){
		return socialId;
	}

	public String getPhone(){
		return phone;
	}

	public String getFbId(){
		return fbId;
	}

	public int getFollowingCount(){
		return followingCount;
	}

	public String getDob(){
		return dob;
	}

	public String getNickName(){
		return nickName;
	}

	public PushNotification getPushNotification(){
		return pushNotification;
	}

	public String getVerificationCode(){
		return verificationCode;
	}

	public String getDevice(){
		return device;
	}

	public String getCountryId(){
		return countryId;
	}

	public String getCityId(){
		return cityId;
	}

	public String getRoomId(){
		return roomId;
	}

	public String getRole(){
		return role;
	}

	public String getGender(){
		return gender;
	}

	public String getCity(){
		return city;
	}

	public String getJsonMemberLong(){
		return jsonMemberLong;
	}

	public String getRoomImage(){
		return roomImage;
	}

	public String getReviewStatus(){
		return reviewStatus;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getPaypal(){
		return paypal;
	}

	public String getEmail(){
		return email;
	}

	public String getWebsite(){
		return website;
	}

	public String getWallet(){
		return wallet;
	}

	public String getSocial(){
		return social;
	}

	public String getProfilePic(){
		return profilePic;
	}

	public String getVerified(){
		return verified;
	}

	public String getLastName(){
		return lastName;
	}

	public String getSessionId(){
		return sessionId;
	}

	public String getProfileComplete(){
		return profileComplete;
	}

	public String getToken(){
		return token;
	}

	public int getLikesCount(){
		return likesCount;
	}

	public String getDeviceToken(){
		return deviceToken;
	}

	public int getFollowersCount(){
		return followersCount;
	}

	public PrivacySetting getPrivacySetting(){
		return privacySetting;
	}

	public int getVideoCount(){
		return videoCount;
	}

	public String getOnline(){
		return online;
	}

	public String getAuthToken(){
		return authToken;
	}

	public String getUsername(){
		return username;
	}

	public String getReferCode(){
		return referCode;
	}
}