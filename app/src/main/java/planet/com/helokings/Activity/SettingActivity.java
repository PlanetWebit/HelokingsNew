package planet.com.helokings.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Pojo.userProfile.ResponseProfile;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.Service.Refresh;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.databinding.ActivitySettingBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity implements Refresh{
    ActivitySettingBinding binding;
    public static Refresh refresh;

    UserSharePreferancess userSharePreferancess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        refresh=this;
        userSharePreferancess=new UserSharePreferancess(this);
        ToolBar();


        binding.ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, EditProfileActivity.class);
                startActivity(intent);

            }
        });
        binding.tvAccountBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AccountBindingActivity.class);
                startActivity(intent);
            }
        });
        binding.joinAgenecy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, JoinAgency.class);
                startActivity(intent);
            }
        });

        getUserProfile();
    }

    private void ToolBar() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolBar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
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

                        binding.name.setText(module.getData().getNickName());

                        if(module.getData().getImage()!=null) {
                            if (module.getData().getImage().equalsIgnoreCase("")) {
                                binding.profile.setImageResource(R.drawable.profile);
                            } else {
                                Glide.with(getApplicationContext()).load(module.getData().getImage()).placeholder(R.drawable.profile).into(binding.profile);
                            }
                        }

                        if (module.getData().getFrame_image().equalsIgnoreCase("")) {
                            binding.videoFrame.setVisibility(View.GONE);
                        } else {
                            Glide.with(getApplicationContext()).load(module.getData().getFrame_image()).into(binding.videoFrame);
                            binding.videoFrame.setVisibility(View.VISIBLE);
                        }

                    } else{
                        if(module.getMsg().equalsIgnoreCase("unauthorized login")){
                            userSharePreferancess.setStringValue("user_id", "");
                            userSharePreferancess.setStringValue("id", "");
                            Comman.getInstance().setUser_id("");
                            Comman.getInstance().setId("");
                            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("status","1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        Toast.makeText(getApplicationContext(),module.getMsg(),Toast.LENGTH_LONG).show();
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

    @Override
    public void onRefresh() {
        getUserProfile();
    }
}