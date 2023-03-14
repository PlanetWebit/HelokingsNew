package planet.com.helokings.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import planet.com.helokings.Fragment.MeFragment;
import planet.com.helokings.Login.LoginActivity;
import planet.com.helokings.Pojo.UpdateUserModule;
import planet.com.helokings.Pojo.userProfile.ResponseProfile;
import planet.com.helokings.R;
import planet.com.helokings.RetrofitAPI.RetrofitClient;
import planet.com.helokings.SharedPref.Comman;
import planet.com.helokings.SharedPref.UserSharePreferancess;
import planet.com.helokings.databinding.ActivityEditProfileBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    UserSharePreferancess userSharePreferancess;

    ProgressDialog progressDialog;

    private File finalFile;
    String userName = "",  uimage = "", userGender = "";
    String gender = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar();
        progressDialog = new ProgressDialog(EditProfileActivity.this, R.style.MyDialogStyle);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        userSharePreferancess=new UserSharePreferancess(this);

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    updateProfile();
                }


            }
        });

        binding.genderdgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.malebtmn:
                        if (binding.malebtmn.getText().toString().equalsIgnoreCase("Male")) {
                            gender = "Male";
                        }
                        break;
                    case R.id.femalebtmn:
                        if (binding.femalebtmn.getText().toString().equalsIgnoreCase("Female")) {
                            gender = "Female";

                        }
                        break;
                    case R.id.otherbtmn:
                        if (binding.otherbtmn.getText().toString().equalsIgnoreCase("Other")) {
                            gender = "Other";

                        }
                        break;
                }
            }
        });

        binding.pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        getUserProfile();

    }

    private void toolbar() {
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

    void selectImage(){
        ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    private boolean validation() {
        if (binding.name.getText().toString().length() == 0) {
            binding.name.setError("Please fill your name");
            binding.name.requestFocus();
            return false;
        } else if (binding.name.getText().toString().length() < 5) {
            binding.name.setError("Fill 5 to 8 words");
            binding.name.requestFocus();
            return false;
        } else if (gender.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

    private void updateProfile() {
        progressDialog.show();

        MultipartBody.Part image1 = null;
        if (binding.profile != null) {
            try {
                if (finalFile.exists()) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), finalFile);
                    image1 = MultipartBody.Part.createFormData("file", finalFile.getName(), requestBody);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
            image1 = MultipartBody.Part.createFormData("file", "", attachmentEmpty);
        }
        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), Comman.getInstance().getUser_id());
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), binding.name.getText().toString());
        RequestBody gndr = RequestBody.create(MediaType.parse("multipart/form-data"), gender);
        RequestBody about = RequestBody.create(MediaType.parse("multipart/form-data"), binding.bio.getText().toString());

        Call<UpdateUserModule> moduleCall = RetrofitClient.getInstance().getAllApiResponse().updateUserProfile(user_id, name,about, gndr, image1);
        moduleCall.enqueue(new Callback<UpdateUserModule>() {
            @Override
            public void onResponse(Call<UpdateUserModule> call, Response<UpdateUserModule> response) {
                progressDialog.dismiss();
                UpdateUserModule module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {

                        getUserProfile();
                        if(MeFragment.refresh!=null){
                            MeFragment.refresh.onRefresh();
                        }
                        if(SettingActivity.refresh!=null){
                            SettingActivity.refresh.onRefresh();
                        }
                    } else {
                        Toast.makeText(EditProfileActivity.this, "" + module.getMsg(), Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<UpdateUserModule> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();


            }
        });

    }
    @SuppressLint("MissingSuperCall")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            binding.profile.setImageURI(selectedImage);
            finalFile = new File(selectedImage.getPath());

        }

    }



    private void getUserProfile() {
        progressDialog.show();
        Call<ResponseProfile> mCall = RetrofitClient.getInstance().getAllApiResponse().getUserProfile(
                Comman.getInstance().getUser_id());
        mCall.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                progressDialog.dismiss();
                ResponseProfile module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("true")) {
                        binding.name.setText(module.getData().getNickName());
                        userName = module.getData().getNickName();
                        binding.bio.setText(module.getData().getBio());
                        userGender = module.getData().getGender();
                        if(module.getData().getImage()!=null) {
                            if (module.getData().getImage().equalsIgnoreCase("")) {
                                binding.profile.setImageResource(R.drawable.profile);
                            } else {
                                Picasso.get().load(module.getData().getImage()).placeholder(R.drawable.user).into(binding.profile);
                                uimage = module.getData().getImage();
                            }
                        }

                        if(module.getData().getGender().equalsIgnoreCase("male")){
                            binding.malebtmn.setChecked(true);
                        }else if(module.getData().getGender().equalsIgnoreCase("female")){
                            binding.femalebtmn.setChecked(true);
                        }else{
                            binding.otherbtmn.setChecked(true);
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
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),module.getMsg(),Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}