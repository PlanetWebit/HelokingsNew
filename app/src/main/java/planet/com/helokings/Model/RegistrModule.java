package planet.com.helokings.Model;

import com.google.gson.annotations.SerializedName;

public class RegistrModule {

	@SerializedName("msg")
	private String msg;

	@SerializedName("image")
	private String image;

	@SerializedName("wallet")
	private String wallet;

	@SerializedName("gender")
	private String gender;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("bio")
	private String bio;

	@SerializedName("profile_complete")
	private String profileComplete;

	@SerializedName("phone")
	private String phone;

	@SerializedName("dob")
	private String dob;

	@SerializedName("nick_name")
	private String nickName;

	@SerializedName("id")
	private String id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	@SerializedName("refer_code")
	private String referCode;

	@SerializedName("status")
	private String status;

	public String getMsg(){
		return msg;
	}

	public String getImage(){
		return image;
	}

	public String getWallet(){
		return wallet;
	}

	public String getGender(){
		return gender;
	}

	public String getLastName(){
		return lastName;
	}

	public String getBio(){
		return bio;
	}

	public String getProfileComplete(){
		return profileComplete;
	}

	public String getPhone(){
		return phone;
	}

	public String getDob(){
		return dob;
	}

	public String getNickName(){
		return nickName;
	}

	public String getId(){
		return id;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}

	public String getUsername(){
		return username;
	}

	public String getReferCode(){
		return referCode;
	}

	public String getStatus(){
		return status;
	}
}