package planet.com.helokings.RetrofitAPI;

import planet.com.helokings.Model.LoginModel;
import planet.com.helokings.Model.OTPModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyInterFace {
    @FormUrlEncoded
    @POST("Api_latest/login_signup_otp/")
    Call<LoginModel> logindata(
            @Field("phone") String phone,
            @Field("device_token") String device_token,
            @Field("device") String device

    );

    @FormUrlEncoded
    @POST("Api_latest/verify_mobile/")
    Call<OTPModel> OtpData(
            @Field("phone") String Phone,
            @Field("verification_code") String verification_code
    );

    

}
