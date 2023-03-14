package planet.com.helokings.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Pojo.userProfile.ResponseProfile;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.databinding.ActivityWalletBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {
    ActivityWalletBinding binding;

    UserSharePreferancess userSharePreferancess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fullScreen();
        userSharePreferancess=new UserSharePreferancess(this);


        binding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.layPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalletActivity.this, PaytmWalletActivity.class);
                startActivity(intent);
            }
        });
        getUserProfile();
    }

    private void fullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = ContextCompat.getDrawable(this, R.drawable.login);
        getWindow().setBackgroundDrawable(background);
    }

    private void getUserProfile() {
        Call<ResponseProfile> mCall = RetrofitClient.getInstance().getAllApiResponse().getUserProfile(
                Comman.getInstance().getUser_id());
        mCall.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {

                ResponseProfile module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {


                        binding.coins.setText(module.getData().getWallet()+"");
                        Toast.makeText(getApplicationContext(),module.getData().getWallet(),Toast.LENGTH_LONG).show();

                    } else{

                        Toast.makeText(getApplicationContext(),module.getMsg(),Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}