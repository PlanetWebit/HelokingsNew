package planet.com.helokings.RetrofitAPI;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import planet.com.helokings.Model.ImageUplodeModel;
import planet.com.helokings.Model.MentionModel;
import planet.com.helokings.Model.TagModel;
import planet.com.helokings.Model.VideoUploadModel;
import planet.com.helokings.Pojo.HomeData.ResponseHomedata;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface NewMyInterface {
    @FormUrlEncoded
    @POST("Api_latest/home_page")
    Call<ResponseHomedata> getHomedata(@Field("auth_token") String auth_token);

    @Multipart
    @POST("Api_latest/Image_uploading")
    Call<ImageUplodeModel> uploadimagedata(
            @Part MultipartBody.Part image,
            @Part("auth_token") RequestBody token,
            @Part("description") RequestBody description,
            @Part("users_json") RequestBody users_json,
            @Part("hashtags_json") RequestBody hashtags_json
    );

    @Multipart
    @POST("Api_latest/Video_uploading")
    Call<VideoUploadModel> uploadvideodata(
            @Part MultipartBody.Part video,
            @Part("auth_token") RequestBody token,
            @Part("description") RequestBody description,
            @Part("users_json") RequestBody users_json,
            @Part("hashtags_json") RequestBody hashtags_json);


    @FormUrlEncoded
    @POST("Api_latest/search")
    Call<TagModel> getTagData(
            @Field("auth_token") String auth_token,
            @Field("type") String type,
            @Field("starting_point") String starting_point);

    @FormUrlEncoded
    @POST("Api_latest/search")
    Call<MentionModel> Mentiondata(
            @Field("auth_token") String auth_token,
            @Field("type") String type,
            @Field("keyword") String keyword,
            @Field("starting_point") String starting_point);
}
