package planet.com.helokings.RetrofitAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://139.84.168.15/";
    private static RetrofitClient retrofitclient;
    private static Retrofit retrofit;

    public RetrofitClient() {

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static RetrofitClient getInstance() {
        if (retrofitclient == null) {
            retrofitclient = new RetrofitClient();
        }
        return retrofitclient;
    }

    public MyInterFace myInterFaceData() {
        return retrofit.create(MyInterFace.class);
    }

}
