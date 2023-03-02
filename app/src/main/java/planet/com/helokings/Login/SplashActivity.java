package planet.com.helokings.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import planet.com.helokings.Pojo.userProfile.ResponseProfile;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 2000;
    private UserSharePreferancess userSharePreferancess;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
//

        userSharePreferancess = new UserSharePreferancess(context);
        String user_id = userSharePreferancess.getStringValue("user_id");
        String id = userSharePreferancess.getStringValue("id");
        String name = userSharePreferancess.getStringValue("name");
        String username = userSharePreferancess.getStringValue("username");
        Comman.getInstance().setUser_id(user_id);
        Comman.getInstance().setId(id);
        Comman.getInstance().setName(name);
        Comman.getInstance().setUsername(username);;
        String token = userSharePreferancess.getStringValue("token");
        Comman.getInstance().setToken(token);
        new Handler().postDelayed(() -> {
            if (!Comman.getInstance().getUser_id().equalsIgnoreCase("")) {
                getUserProfile();
            } else {
                Intent intent = new Intent(SplashActivity.this, LoginTypeActivity.class);
                startActivity(intent);
                finish();
            }

        }, SPLASH_SCREEN_TIME_OUT);

    }

    private void getUserProfile() {
        Call<ResponseProfile> mCall = RetrofitClient.getInstance().myInterFaceData().getUserProfile(Comman.getInstance().getUser_id());
        mCall.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                ResponseProfile module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {
                        Comman.getInstance().setImage(module.getData().getImage());
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else{
                        if(module.getMsg().equalsIgnoreCase("unauthorized login")){
                            userSharePreferancess.setStringValue("user_id", "");
                            userSharePreferancess.setStringValue("id", "");
                            userSharePreferancess.setStringValue("username", "");
                            Comman.getInstance().setUser_id("");
                            Comman.getInstance().setId("");
                            Comman.getInstance().setUsername("");
                            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("status","1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {

            }
        });
    }

}