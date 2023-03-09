package planet.com.helokings.RetrofitAPI;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import planet.com.helokings.Model.LoginModel;
import planet.com.helokings.Model.OTPModel;
import planet.com.helokings.Model.OtpModule;
import planet.com.helokings.Model.RegistrModule;
import planet.com.helokings.Model.StoreFrameModel;
import planet.com.helokings.Pojo.Fonts.ResponseFonts;
import planet.com.helokings.Pojo.HomeData.ResponseHomedata;
import planet.com.helokings.Pojo.MyRooms.ResponseRoomdata;
import planet.com.helokings.Pojo.RoomData.ResponseRoom;
import planet.com.helokings.Pojo.RoomDetails.ResponseRoomDetails;
import planet.com.helokings.Pojo.userProfile.ResponseProfile;
 import planet.com.helokings.VideoStreamingActivity.audioseatmodule.pojos.ResponseOtherData;
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
//    @Multipart
//    @POST("Api_latest/update_userdata")
//    Call<RegistrModule> userUpdate(@Part("auth_token") RequestBody auth_token,
//                                   @Part("nick_name") RequestBody nick_name,
//                                   @Part("bio") RequestBody bio,
//                                   @Part("gender") RequestBody gender,
//                                   @Part MultipartBody.Part profile_pic
//    );
//





    @FormUrlEncoded
    @POST("Api_latest/update_userdata")
    Call<RegistrModule> userUpdate(
            @Field("auth_token") String  auth_token,
            @Field("nick_name") String  nick_name,
            @Field("gender") String  gender,
            @Field("bio") String  bio
    );





    @FormUrlEncoded
    @POST("Api_latest/other_userdata")
    Call<ResponseOtherData> getOtherUserData(
            @Field("auth_token") String user_id,
            @Field("other_user_id") String other_user_id,
            @Field("room_id") String room_id
    );


    @FormUrlEncoded
    @POST("Api_latest/verify_mobile")
    Call<OtpModule> userOtp(@Field("phone") String user_id,
                            @Field("verification_code") String otp);









    @FormUrlEncoded
    @POST("Api_latest/userdata")
    Call<ResponseProfile> getUserProfile(@Field("auth_token") String user_id);




    @FormUrlEncoded
    @POST("Api_latest/home_page")
    Call<ResponseHomedata> getHomedata(@Field("auth_token") String auth_token);


    @FormUrlEncoded
    @POST("Api_latest/streaming_list")
    Call<ResponseHomedata> getStreamData(@Field("auth_token") String auth_token, @Field("type") String type);


    @Multipart
    @POST("Api_latest/room_create")
    Call<ResponseRoom> crRoom(@Part("auth_token") RequestBody auth_token,
                              @Part("room_name") RequestBody room_name,
                              @Part("room_tag") RequestBody room_tag,
                              @Part("room_desc") RequestBody room_desc,
                              @Part MultipartBody.Part profile_pic,
                              @Part("review_status") RequestBody review_status,
                              @Part("coin") RequestBody coin);

    @FormUrlEncoded
    @POST("Api_latest/host_room_list")
    Call<ResponseRoomdata> roomList(@Field("auth_token") String user_id);

    @FormUrlEncoded
    @POST("Api_latest/room_details")
    Call<ResponseRoomDetails> roomDetails(@Field("auth_token") String user_id, @Field("room_id") String room_id);


    @FormUrlEncoded
    @POST("Api_latest/room_details")
    Call<ResponseRoomDetails> roomDetailsVideo(@Field("auth_token") String user_id, @Field("room_id") String room_id, @Field("type") String room_type);



    @FormUrlEncoded
    @POST("Api_latest/frame_api")
    Call<StoreFrameModel> getFrames(
            @Field("auth_token") String user_id,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("Api_latest/frame_api")
    Call<ResponseFonts> getFonts(
            @Field("auth_token") String user_id,
            @Field("type") String type
    );


    @FormUrlEncoded
    @POST("Api_latest/frame_api")
    Call<StoreFrameModel> getWallpapers(
            @Field("auth_token") String user_id,
            @Field("type") String type
    );



    @Multipart
    @POST("Api_latest/room_create")
    Call<ResponseRoom> crRoompartyvideo(@Part("auth_token") RequestBody auth_token,
                                        @Part("room_name") RequestBody room_name,
                                        @Part("room_tag") RequestBody room_tag,
                                        @Part("room_desc") RequestBody room_desc,
                                        @Part MultipartBody.Part profile_pic,
                                        @Part("review_status") RequestBody review_status,
                                        @Part("coin") RequestBody coin,
                                        @Part("type") RequestBody type);


 }
