package planet.com.audioseatmodule.RetrofitAPI;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import planet.com.audioseatmodule.Seatmodel.Seatpojos.ResponseOtherData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MyInterFace {



    @FormUrlEncoded
    @POST("Api_latest/other_userdata")
    Call<ResponseOtherData> getOtherUserData(
            @Field("auth_token") String user_id,
            @Field("other_user_id") String other_user_id,
            @Field("room_id") String room_id
    );








 }
