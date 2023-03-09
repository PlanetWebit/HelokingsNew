package planet.com.helokings.Model;

import com.google.gson.annotations.SerializedName;

public class OtpModule {

	@SerializedName("msg")
	private String msg;

	@SerializedName("role")
	private String role;

	@SerializedName("wallet")
	private String wallet;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("session_id")
	private String sessionId;

	@SerializedName("profile_complete")
	private String profileComplete;

	@SerializedName("token")
	private String token;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("phone")
	private String phone;

	@SerializedName("nick_name")
	private String nickName;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	@SerializedName("username")
	private String username;

	@SerializedName("refer_code")
	private String referCode;

	public String getMsg(){
		return msg;
	}

	public String getRole(){
		return role;
	}

	public String getWallet(){
		return wallet;
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

	public String getUserId(){
		return userId;
	}

	public String getPhone(){
		return phone;
	}

	public String getNickName(){
		return nickName;
	}


	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}

	public String getStatus(){
		return status;
	}

	public String getUsername(){
		return username;
	}

	public String getReferCode(){
		return referCode;
	}
}