package planet.com.helokings.RetrofitAPI;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import planet.com.helokings.Model.LoginModel;
import planet.com.helokings.Model.OTPModel;
import planet.com.helokings.Model.RegistrModule;
import planet.com.helokings.Pojo.userProfile.ResponseProfile;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MyInterFace {
    @FormUrlEncoded
    @POST("Api_latest/login_signup_otp")
    Call<LoginModel> logindata(@Field("phone") String mobile_no,
                                @Field("device_token") String device_token,
                                @Field("device") String device
    );

    @FormUrlEncoded
    @POST("Api_latest/verify_mobile")
    Call<OTPModel> OtpData(@Field("phone") String user_id,
                            @Field("verification_code") String otp);

    @Multipart
    @POST("Api_latest/update_userdata")
    Call<RegistrModule> userUpdate(@Part("auth_token") RequestBody auth_token,
                                   @Part("nick_name") RequestBody nick_name,
                                   @Part("bio") RequestBody bio,
                                   @Part("gender") RequestBody gender,
                                   @Part MultipartBody.Part profile_pic
    );

    @FormUrlEncoded
    @POST("Api_latest/userdata")
    Call<ResponseProfile> getUserProfile(@Field("auth_token") String user_id);
}
